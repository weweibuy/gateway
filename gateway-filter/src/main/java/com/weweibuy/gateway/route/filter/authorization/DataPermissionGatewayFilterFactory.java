package com.weweibuy.gateway.route.filter.authorization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.weweibuy.framework.common.core.exception.Exceptions;
import com.weweibuy.framework.common.core.model.dto.CommonCodeResponse;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.utils.JackJsonUtils;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.core.lb.LoadBalancerHelper;
import com.weweibuy.gateway.core.support.ObjectWrapper;
import com.weweibuy.gateway.route.filter.authorization.model.DataPermissionReq;
import com.weweibuy.gateway.route.filter.authorization.model.DataPermissionResp;
import com.weweibuy.gateway.route.filter.support.CachedBodyOutputMessage;
import com.weweibuy.gateway.route.filter.support.DataPermissionHelper;
import com.weweibuy.gateway.route.filter.support.ModifyBodyReqHelper;
import io.netty.handler.codec.http.HttpMethod;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;
import static org.springframework.util.CollectionUtils.unmodifiableMultiValueMap;

/**
 * 数据 权限控制过滤器
 *
 * @author durenhao
 * @date 2021/1/31 19:28
 **/
@Component
public class DataPermissionGatewayFilterFactory extends AbstractGatewayFilterFactory<DataPermissionGatewayFilterFactory.Config> {

    private static final ParameterizedTypeReference JSON_DATA_TYPE;

    private static final ParameterizedTypeReference FORM_DATA_TYPE;


    private final List<HttpMessageReader<?>> messageReaders;

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;


    private JavaType authorizationRespType;

    static {
        JSON_DATA_TYPE = ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(
                Map.class, String.class, Object.class).getType());
        ResolvableType resolvableType = ResolvableType.forClassWithGenerics(
                MultiValueMap.class, String.class, String.class);
        FORM_DATA_TYPE = ParameterizedTypeReference.forType(resolvableType.getType());
    }

    public DataPermissionGatewayFilterFactory() {
        super(Config.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
        authorizationRespType = JackJsonUtils.javaType(new TypeReference<CommonDataResponse<List<DataPermissionResp>>>() {
        });
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String username = (String) exchange.getAttributes().get(ExchangeAttributeConstant.USER_ID_ATTR);
            URI authUri = loadBalancerHelper.strToUri(config.getAuthUrl());

            Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);

            String service = Optional.ofNullable(route.getMetadata())
                    .map(m -> (String) m.get(route.getId()))
                    .orElseThrow(() -> Exceptions.system("路由id,无法找到系统id"));


            ServerHttpRequest request = exchange.getRequest();
            org.springframework.http.HttpMethod method = request.getMethod();

            URI requestUri = (URI) exchange.getAttributes().get(GATEWAY_REQUEST_URL_ATTR);

            DataPermissionReq dataPermissionReq = new DataPermissionReq(service, requestUri.getPath(), method, username);

            return loadBalancerHelper.choose(authUri)
                    .flatMap(uri -> ReactorHttpHelper.<CommonDataResponse<List<DataPermissionResp>>>executeForJson(HttpMethod.POST,
                            uri.toString() + authUri.getPath(), dataPermissionReq, authorizationRespType)
                            .flatMap(resp -> handlerPermissionResp(resp, chain, exchange)));

        };
    }

    /**
     * 修改字段
     *
     * @param responseEntity
     * @param chain
     * @param exchange
     * @return
     */
    protected Mono<Void> handlerPermissionResp(ResponseEntity<CommonDataResponse<List<DataPermissionResp>>> responseEntity,
                                               GatewayFilterChain chain, ServerWebExchange exchange) {

        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        if (status == 200 && responseEntity.getBody() != null) {
            CommonDataResponse<List<DataPermissionResp>> response = responseEntity.getBody();
            List<DataPermissionResp> dataPermissionRespList = response.getData();
            return handlerPermissionResp(dataPermissionRespList, chain, exchange);
        } else if (status >= 400 && status < 500) {
            return ReactorHttpHelper.buildAndWriteJson(responseEntity.getStatusCode(), responseEntity.getBody(), exchange);
        } else if (status >= 500) {
            return ReactorHttpHelper.buildAndWriteJson(HttpStatus.INTERNAL_SERVER_ERROR, CommonCodeResponse.unknownException(), exchange);
        }
        return ReactorHttpHelper.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeResponse.forbidden(), exchange);
    }


    protected Mono<Void> handlerPermissionResp(List<DataPermissionResp> dataPermissionRespList,
                                               GatewayFilterChain chain, ServerWebExchange exchange) {
        // 没有要修改的字段
        if (CollectionUtils.isEmpty(dataPermissionRespList)) {
            return chain.filter(exchange);
        }
        return modifyPermissionField(dataPermissionRespList, chain, exchange);
    }

    /**
     * 修改 请求字段
     *
     * @param dataPermissionRespList
     * @param chain
     * @param exchange
     * @return
     */
    protected Mono<Void> modifyPermissionField(List<DataPermissionResp> dataPermissionRespList,
                                               GatewayFilterChain chain, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        Map<Integer, List<DataPermissionResp>> typeListMap = dataPermissionRespList.stream()
                .collect(Collectors.groupingBy(DataPermissionResp::getReqParamType));
        return modifyParam(typeListMap, chain, exchange);
    }


    private Mono modifyParam(Map<Integer, List<DataPermissionResp>> typeListMap,
                             GatewayFilterChain chain, ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();

        List<DataPermissionResp> queryDataPermissionList = typeListMap.get(DataPermissionResp.QUERY_PARAM_TYPE);
        URI newUri = null;
        // 修改query参数
        if (CollectionUtils.isNotEmpty(queryDataPermissionList)) {
            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>(
                    request.getQueryParams());
            DataPermissionHelper.modifyQueryParam(queryDataPermissionList, queryParams);

            newUri = UriComponentsBuilder.fromUri(request.getURI())
                    .replaceQueryParams(unmodifiableMultiValueMap(queryParams))
                    .build().toUri();
        }

        ObjectWrapper<URI> newUriWrapper = new ObjectWrapper<>(newUri);

        List<DataPermissionResp> bodyDataPermissionList = typeListMap.get(DataPermissionResp.BODY_PARAM_TYPE);

        if (CollectionUtils.isNotEmpty(bodyDataPermissionList)) {
            HttpHeaders oriHeaders = exchange.getRequest().getHeaders();
            String contentType = oriHeaders.getFirst(HttpHeaders.CONTENT_TYPE);
            if (StringUtils.isBlank(contentType)) {
                throw new UnsupportedMediaTypeStatusException("contentType为空");
            }
            // TODO 支持更多类型
            MediaType mediaType = MediaType.parseMediaType(contentType);
            if (!MediaType.APPLICATION_JSON.includes(mediaType) &&
                    !MediaType.APPLICATION_FORM_URLENCODED.includes(mediaType)) {
                throw new UnsupportedMediaTypeStatusException(contentType);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.putAll(oriHeaders);
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            return ServerRequest.create(exchange, messageReaders)
                    .bodyToMono(mediaTypeToTypeReference(mediaType))
                    // 修改请求体  TODO 修改结构不抛异常 参考 VerifySignatureGatewayFilterFactory
                    .map(body -> DataPermissionHelper.modifyBody((Map<String, Object>) body, bodyDataPermissionList, mediaType))
                    .flatMap(body -> {
                        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
                        return BodyInserters.fromPublisher(Mono.just(body), mediaTypeToTypeReference(mediaType))
                                .insert(outputMessage, new BodyInserterContext())
                                .then(Mono.defer(() -> {
                                    ServerHttpRequestDecorator decorator = ModifyBodyReqHelper.decorate(exchange, headers, outputMessage);
                                    return buildReqIfNecessaryAndFilter(chain, exchange, decorator, newUriWrapper.getObject());
                                }))
                                .onErrorResume((Function<Throwable, Mono<Void>>) throwable ->
                                        ModifyBodyReqHelper.release(exchange, outputMessage, throwable));
                    });
        }

        // 修改url
        if (newUri != null) {
            return buildReqIfNecessaryAndFilter(chain, exchange, request, newUri);
        }
        // 不修改数据
        return chain.filter(exchange);
    }


    private ParameterizedTypeReference mediaTypeToTypeReference(MediaType mediaType) {

        if (MediaType.APPLICATION_JSON.includes(mediaType)) {
            return JSON_DATA_TYPE;
        }
        if (MediaType.APPLICATION_FORM_URLENCODED.includes(mediaType)) {
            return FORM_DATA_TYPE;
        }
        throw new UnsupportedMediaTypeStatusException(mediaType.getType() + mediaType.getSubtype());
    }

    private Mono<Void> buildReqIfNecessaryAndFilter(GatewayFilterChain chain, ServerWebExchange exchange,
                                                    ServerHttpRequest request, URI newUri) {
        if (newUri != null) {
            request = request.mutate().uri(newUri)
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        }
        return chain.filter(exchange.mutate().request(request).build());
    }


    @Data
    public static class Config {

        /**
         * 权限服务器地址
         */
        @NotBlank(message = "权限服务器地址不能为空")
        private String authUrl;

        /**
         * 匿名 路径
         */
        private Set<String> anonymousPath = new HashSet<>();

    }
}
