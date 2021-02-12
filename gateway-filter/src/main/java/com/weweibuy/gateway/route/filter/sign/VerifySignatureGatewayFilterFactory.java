package com.weweibuy.gateway.route.filter.sign;

import com.weweibuy.framework.common.core.model.dto.CommonCodeResponse;
import com.weweibuy.framework.common.core.utils.PredicateEnhance;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.route.filter.authorization.AppAuthenticationGatewayFilterFactory;
import com.weweibuy.gateway.route.filter.authorization.model.AppInfo;
import com.weweibuy.gateway.route.filter.config.VerifySignatureProperties;
import com.weweibuy.gateway.route.filter.constant.RedisConstant;
import com.weweibuy.gateway.route.filter.support.CachedBodyOutputMessage;
import com.weweibuy.gateway.route.filter.support.ModifyBodyReqHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.EnableBodyCachingEvent;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * 验签过滤器,
 * 依赖 {@link AppAuthenticationGatewayFilterFactory} 鉴权并设置 appSecret(验签)
 * 依赖 {@link SystemRequestParamGatewayFilterFactory} 设置系统级输入参数
 * <p>
 * 禁止 URL 传参形式: ?name=tom,jack&name=luck (将导致验签失败);
 * ?name=&age=12   将视 name对应的值为空串进行验签
 *
 * @author durenhao
 * @date 2020/2/22 19:56
 **/
@Component
@Slf4j
public class VerifySignatureGatewayFilterFactory extends AbstractGatewayFilterFactory<VerifySignatureGatewayFilterFactory.Config> {

    @Autowired
    private ApplicationContext applicationContext;

    public static final String CACHED_REQUEST_BODY_ATTR = ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR;

    private static final ParameterizedTypeReference MULTIPART_VALUE_TYPE;

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Autowired
    private VerifySignatureProperties verifySignatureProperties;

    private List<HttpMessageReader<?>> httpMessageReaders;

    static {
        MULTIPART_VALUE_TYPE = ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(
                MultiValueMap.class, String.class, Part.class).getType());
    }

    public VerifySignatureGatewayFilterFactory() {
        super(VerifySignatureGatewayFilterFactory.Config.class);
        httpMessageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    @Override
    @SuppressWarnings("unchecked")
    public GatewayFilter apply(Config config) {
        /*
         * 发送事件 缓存 请求体
         * @see org.springframework.cloud.gateway.filter.AdaptCachedBodyGlobalFilter.onApplicationEvent()
         */
        EnableBodyCachingEvent event = new EnableBodyCachingEvent(this, config.getRouterId());
        applicationContext.publishEvent(event);

        return (exchange, chain) -> {
            SystemRequestParam systemRequestParam = (SystemRequestParam) exchange.getAttributes().get(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM);
            HttpHeaders oriHeaders = exchange.getRequest().getHeaders();

            String contentType = oriHeaders.getFirst(HttpHeaders.CONTENT_TYPE);
            if (StringUtils.isNotBlank(contentType) &&
                    MediaType.MULTIPART_FORM_DATA.includes(MediaType.parseMediaType(contentType))) {
                // MULTIPART_FORM_DATA 单独校验
                return verifyMultipartFormData(systemRequestParam, exchange, chain, config);
            }

            String body = Optional.ofNullable((DataBuffer) exchange.getAttributes().get(CACHED_REQUEST_BODY_ATTR))
                    .map(DataBuffer::asByteBuffer)
                    .map(StandardCharsets.UTF_8::decode)
                    .map(Object::toString)
                    .orElse(StringUtils.EMPTY);

            return verifySignature(systemRequestParam, exchange, body, Collections.emptyMap(), config)
                    .flatMap(b ->
                            PredicateEnhance.predicateThenGet(b,
                                    () -> chain.filter(exchange),
                                    () -> ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST,
                                            CommonCodeResponse.badSignature(), exchange)));
        };
    }

    private Mono verifyMultipartFormData(SystemRequestParam systemRequestParam, ServerWebExchange exchange, GatewayFilterChain chain, Config config) {
        return ServerRequest.create(exchange, httpMessageReaders)
                .bodyToMono(MULTIPART_VALUE_TYPE)
                // 验签
                .flatMap(body -> {
                    LinkedMultiValueMap<String, Part> multiValueMap = (LinkedMultiValueMap) (body);
                    Map<String, Part> singleValueMap = multiValueMap.toSingleValueMap();
                    return verifySignature(systemRequestParam, exchange, null,
                            SignHelper.multipartSignBodyParam(singleValueMap), config)
                            .flatMap(b -> PredicateEnhance.predicateThenGet(b,
                                    () -> multipartSuccessVerify(singleValueMap, exchange, chain),
                                    () -> ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST,
                                            CommonCodeResponse.badSignature(), exchange)));
                });
    }



    /**
     * multipart 验证成功重新构建请求
     *
     * @param singleValueMap
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> multipartSuccessVerify(Map<String, Part> singleValueMap, ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders oriHeaders = request.getHeaders();
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(oriHeaders);
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        singleValueMap.forEach((k, v) -> multipartBodyBuilder.part(k, v));

        MultiValueMap<String, HttpEntity<?>> httpEntityMultiValueMap = multipartBodyBuilder.build();

        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return BodyInserters.fromPublisher(Mono.just(httpEntityMultiValueMap), MULTIPART_VALUE_TYPE)
                .insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> chain.filter(exchange.mutate()
                        .request(ModifyBodyReqHelper.decorate(
                                exchange, headers, outputMessage)).build())))
                .onErrorResume((Function<Throwable, Mono<Void>>) throwable ->
                        ModifyBodyReqHelper.release(exchange, outputMessage, throwable));
    }


    private Mono<Boolean> verifySignature(SystemRequestParam systemRequestParam, ServerWebExchange exchange,
                                          String body, Map<String, String> fileMd5Map, Config config) {

        AppInfo appInfo = (AppInfo) exchange.getAttributes().get(ExchangeAttributeConstant.APP_INFO_ATTR);

        String sign = SignHelper.sign(exchange.getRequest(), systemRequestParam, appInfo, body, fileMd5Map);

        if (StringUtils.isBlank(sign) || !sign.equals(systemRequestParam.getSignature())) {
            return Mono.just(false);
        }
        if (!config.getNonceCheck()) {
            return Mono.just(true);
        }
        return verifyNonce(systemRequestParam, appInfo);
    }

    /**
     * 重放检查
     *
     * @param systemRequestParam
     * @param appInfo
     * @return
     */
    private Mono<Boolean> verifyNonce(SystemRequestParam systemRequestParam, AppInfo appInfo) {
        String clientId = appInfo.getAppId();
        return redisTemplate.opsForValue()
                .setIfAbsent(key(clientId, systemRequestParam), systemRequestParam.getTimestamp() + "",
                        Duration.ofSeconds(verifySignatureProperties.getTimestampIntervalSecond()));
    }

    private String key(String clientId, SystemRequestParam systemRequestParam) {
        return RedisConstant.KEY_PREFIX + clientId + "_" +
                systemRequestParam.getSignature() + "_" + systemRequestParam.getNonce();
    }

    @Data
    public static class Config {

        @NotBlank(message = "必须设置 routerId ")
        private String routerId;

        /**
         * 是否防重放
         */
        private Boolean nonceCheck = false;

    }


}
