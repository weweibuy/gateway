package com.weweibuy.gateway.route.filter.sign;

import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.core.support.ObjectWrapper;
import com.weweibuy.gateway.route.filter.config.VerifySignatureProperties;
import com.weweibuy.gateway.route.filter.constant.RedisConstant;
import com.weweibuy.gateway.route.filter.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * @author durenhao
 * @date 2020/2/22 19:56
 **/
@Component
@Slf4j
public class VerifySignatureGatewayFilterFactory extends AbstractGatewayFilterFactory {

    private final List<HttpMessageReader<?>> messageReaders;

    private static final ParameterizedTypeReference MULTIPART_DATA_TYPE;

    private static final ParameterizedTypeReference JSON_DATA_TYPE;

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Autowired
    private VerifySignatureProperties verifySignatureProperties;


    static {
        MULTIPART_DATA_TYPE = ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(
                MultiValueMap.class, String.class, String.class).getType());
        JSON_DATA_TYPE = ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(
                Map.class, String.class, Object.class).getType());
    }

    public VerifySignatureGatewayFilterFactory() {
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }


    @Override
    @SuppressWarnings("unchecked")
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            SystemRequestParam systemRequestParam = (SystemRequestParam) exchange.getAttributes().get(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM);


            String contentType = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);

            MediaType mediaType = MediaType.valueOf(contentType);

            ParameterizedTypeReference typeReference = null;

            if (mediaType.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED)) {
                typeReference = MULTIPART_DATA_TYPE;
            } else if (mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
                typeReference = JSON_DATA_TYPE;
            } else {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.UNSUPPORTED_MEDIA_TYPE, CommonCodeJsonResponse.UnSupportedMediaType(), exchange);
            }
            ObjectWrapper<ParameterizedTypeReference> objectWrapper = new ObjectWrapper<>(typeReference);

            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove(HttpHeaders.CONTENT_LENGTH);

            ServerRequest serverRequest = new DefaultServerRequest(exchange,
                    this.messageReaders);

            return serverRequest.bodyToMono(typeReference)
                    .flatMap(o -> verifySignature(systemRequestParam, exchange, (Map) o)
                            .flatMap(b -> {
                                if (b) {
                                    CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
                                    return BodyInserters.fromPublisher(Mono.just(o), objectWrapper.getObject())
                                            .insert(outputMessage, new BodyInserterContext())
                                            .then(Mono.defer(() -> {
                                                ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                                                        exchange.getRequest()) {

                                                    @Override
                                                    public HttpHeaders getHeaders() {
                                                        long contentLength = headers.getContentLength();
                                                        HttpHeaders httpHeaders = new HttpHeaders();
                                                        httpHeaders.putAll(super.getHeaders());
                                                        if (contentLength > 0) {
                                                            httpHeaders.setContentLength(contentLength);
                                                        } else {
                                                            httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                                                        }
                                                        return httpHeaders;
                                                    }

                                                    @Override
                                                    public Flux<DataBuffer> getBody() {
                                                        return outputMessage.getBody();
                                                    }
                                                };
                                                return chain.filter(exchange.mutate().request(decorator).build());
                                            }));

                                } else {
                                    return ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST, CommonCodeJsonResponse.badRequestParam("签名错误"), exchange);
                                }
                            }));

        };
    }


    private Mono<Boolean> verifySignature(SystemRequestParam systemRequestParam, ServerWebExchange exchange, Map body) {
        SignTypeEum signType = systemRequestParam.getSignType();
        String appSecret = (String) exchange.getAttributes().get(ExchangeAttributeConstant.APP_SECRET_ATTR);
        ServerHttpRequest httpRequest = exchange.getRequest();
        MultiValueMap<String, String> queryParams = httpRequest.getQueryParams();
        String sign = null;
        switch (signType) {
            case MD5:
                sign = SignUtil.md5Sign(appSecret, queryParams, body, systemRequestParam);
                break;
            case HMAC_SHA256:
                sign = SignUtil.hmacSha256Sign(appSecret, queryParams, body, systemRequestParam);
                break;
            default:

        }

        if (StringUtils.isBlank(sign) || !sign.equals(systemRequestParam.getSignature())) {
            return Mono.just(false);
        }
        if (!verifySignatureProperties.getNonceCheck()) {
            return Mono.just(true);
        }
        return verifyNonce(systemRequestParam);
    }


    private Mono<Boolean> verifyNonce(SystemRequestParam systemRequestParam) {
        if (!verifySignatureProperties.getNonceCheck()) {
            return Mono.just(true);
        }
        String appKey = systemRequestParam.getAppKey();
        return redisTemplate.opsForValue()
                .setIfAbsent(RedisConstant.KEY_PREFIX + appKey + "_" + systemRequestParam.getNonce(), systemRequestParam.getTimestamp() + "",
                        Duration.ofSeconds(125));
    }


}
