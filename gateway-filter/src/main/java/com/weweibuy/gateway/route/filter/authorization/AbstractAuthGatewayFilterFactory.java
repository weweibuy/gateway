package com.weweibuy.gateway.route.filter.authorization;

import com.fasterxml.jackson.databind.JavaType;
import com.weweibuy.framework.common.core.model.constant.CommonConstant;
import com.weweibuy.framework.common.core.model.dto.CommonCodeResponse;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.eum.CommonErrorCodeEum;
import com.weweibuy.framework.common.core.utils.HttpRequestUtils;
import com.weweibuy.framework.common.core.utils.PredicateEnhance;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.core.lb.LoadBalancerHelper;
import io.netty.handler.codec.http.HttpMethod;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.*;

/**
 * 权限过滤器 抽象
 *
 * @author durenhao
 * @date 2020/12/27 20:29
 **/
public abstract class AbstractAuthGatewayFilterFactory<C extends AbstractAuthGatewayFilterFactory.Config, R, P>
        extends AbstractGatewayFilterFactory<C> {

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    private JavaType authorizationRespType;

    public AbstractAuthGatewayFilterFactory(Class<C> configClass, JavaType authorizationRespType) {
        super(configClass);
        this.authorizationRespType = authorizationRespType;
    }


    @Override
    public GatewayFilter apply(C config) {
        return (exchange, chain) -> {

            if (!preCheck(config, chain, exchange)) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeResponse.unauthorized(), exchange);
            }

            Set<String> anonymousPath = config.getAnonymousPath();
            String path = exchange.getRequest().getURI().getPath();

            anonymousPath = Optional.ofNullable(anonymousPath)
                    .orElse(Collections.emptySet());

            return PredicateEnhance.of(anonymousPath)
                    .withPredicate(ap -> ap.stream()
                            .anyMatch(p -> HttpRequestUtils.isEqualsOrMatchPath(p, path)))
                    .get(() -> chain.filter(exchange),
                            () -> authentication(config, chain, exchange));
        };
    }

    private Mono<Void> authentication(C config, GatewayFilterChain chain, ServerWebExchange exchange) {
        R r = authReq(config, chain, exchange);
        URI authUri = loadBalancerHelper.strToUri(config.getAuthUrl());

        // 设置 traceCode
        String traceCode = (String) exchange.getAttributes().get(ExchangeAttributeConstant.TRACE_ID_ATTR);
        Map<String, String> headerMap = Optional.ofNullable(traceCode)
                .map(t -> Collections.singletonMap(CommonConstant.LogTraceConstant.HTTP_TRACE_CODE_HEADER, t))
                .orElse(Collections.emptyMap());
        return loadBalancerHelper.choose(authUri)
                .flatMap(uri -> ReactorHttpHelper.<CommonDataResponse<P>>executeForJson(HttpMethod.POST, uri.toString() + authUri.getPath(),
                        null, headerMap, r, authorizationRespType)
                        .flatMap(res -> hashAuthentication(res, chain, exchange)));

    }

    // TODO 异常设置 源服务 id
    protected Mono<Void> hashAuthentication(ResponseEntity<CommonDataResponse<P>> responseEntity,
                                            GatewayFilterChain chain, ServerWebExchange exchange) {


        int status = responseEntity.getStatusCode().value();
        if (status == 200 && responseEntity.getBody() != null) {
            return handleReqSuccess(responseEntity.getBody(), chain, exchange);
        } else if (status >= 400 && status < 500) {
            return ReactorHttpHelper.buildAndWriteJson(responseEntity.getStatusCode(), responseEntity.getBody(), exchange);
        } else if (status >= 500) {
            return ReactorHttpHelper.buildAndWriteJson(HttpStatus.INTERNAL_SERVER_ERROR, CommonCodeResponse.unknownException(), exchange);
        }
        return ReactorHttpHelper.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeResponse.unauthorized(), exchange);
    }

    private Mono<Void> handleReqSuccess(CommonDataResponse<P> response, GatewayFilterChain chain, ServerWebExchange exchange) {
        String code = response.getCode();
        if (CommonErrorCodeEum.SUCCESS.getCode().equals(code)) {
            onAuthSuccess(response.getData(), chain, exchange);
            return chain.filter(exchange);
        }

        if (CommonErrorCodeEum.FORBIDDEN.getCode().equals(code)) {
            return ReactorHttpHelper.buildAndWriteJson(HttpStatus.FORBIDDEN, CommonCodeResponse.forbidden(), exchange);
        }

        return ReactorHttpHelper.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeResponse.unauthorized(), exchange);

    }

    /**
     * 鉴权成功
     *
     * @param response
     * @param chain
     * @param exchange
     */
    protected abstract void onAuthSuccess(P response, GatewayFilterChain chain, ServerWebExchange exchange);

    /**
     * 鉴权请求
     *
     * @param config
     * @param chain
     * @param exchange
     * @return
     */
    protected abstract R authReq(C config, GatewayFilterChain chain, ServerWebExchange exchange);

    /**
     * 鉴权前校验
     *
     * @param config
     * @param chain
     * @param exchange
     * @return true 通过 ; false 不通过
     */
    protected abstract boolean preCheck(C config, GatewayFilterChain chain, ServerWebExchange exchange);


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