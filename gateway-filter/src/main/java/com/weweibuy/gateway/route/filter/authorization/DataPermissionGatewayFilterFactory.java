package com.weweibuy.gateway.route.filter.authorization;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.core.lb.LoadBalancerHelper;
import com.weweibuy.gateway.route.filter.authorization.model.DataPermissionReq;
import io.netty.handler.codec.http.HttpMethod;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 数据 权限控制过滤器
 *
 * @author durenhao
 * @date 2021/1/31 19:28
 **/
@Component
public class DataPermissionGatewayFilterFactory extends AbstractGatewayFilterFactory<DataPermissionGatewayFilterFactory.Config> {

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    private JavaType authorizationRespType;

    public DataPermissionGatewayFilterFactory(ObjectMapper objectMapper) {
        super(Config.class);
        authorizationRespType = objectMapper.getTypeFactory()
                .constructParametricType(CommonDataResponse.class, DataPermissionReq.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String username = (String) exchange.getAttributes().get(ExchangeAttributeConstant.USER_ID_ATTR);
            URI authUri = loadBalancerHelper.strToUri(config.getAuthUrl());

            String service = exchange.getAttribute(ExchangeAttributeConstant.SERVICE_KEY);
            ServerHttpRequest request = exchange.getRequest();
            org.springframework.http.HttpMethod method = request.getMethod();

            URI requestUri = (URI) exchange.getAttributes().get(GATEWAY_REQUEST_URL_ATTR);

            DataPermissionReq dataPermissionReq = new DataPermissionReq(service, requestUri.getPath(), method, username);

            return loadBalancerHelper.choose(authUri)
                    .flatMap(uri -> ReactorHttpHelper.<CommonDataResponse<DataPermissionReq>>executeForJson(HttpMethod.POST,
                            uri.toString() + authUri.getPath(), dataPermissionReq, authorizationRespType)
                            .flatMap(resp -> modifyPermissionField(resp, chain, exchange)));

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
    protected Mono<Void> modifyPermissionField(ResponseEntity<CommonDataResponse<DataPermissionReq>> responseEntity,
                                               GatewayFilterChain chain, ServerWebExchange exchange) {
        return Mono.empty();
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
