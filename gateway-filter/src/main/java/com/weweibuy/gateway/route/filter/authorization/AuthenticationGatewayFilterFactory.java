package com.weweibuy.gateway.route.filter.authorization;

import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
import com.weweibuy.gateway.core.mode.event.response.ResponseWriter;
import com.weweibuy.gateway.route.filter.authorization.model.AuthorizationRe;
import com.weweibuy.gateway.route.filter.constant.AuthenticationHeaderConstant;
import com.weweibuy.gateway.route.filter.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.route.filter.http.HttpHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author durenhao
 * @date 2019/7/6 9:27
 **/
@Slf4j
@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory {

    private static final String AUTH_HOST = "";

    @Autowired
    private ResponseWriter responseWriter;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String appKey = headers.getFirst(AuthenticationHeaderConstant.APP_KEY_HEADER);
            String service = exchange.getAttribute(ExchangeAttributeConstant.SERVICE_KEY);

            if (StringUtils.isAnyBlank(appKey, service) || appKey.length() <= 6) {
                return responseWriter.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeJsonResponse.unauthorized(), exchange);
            }
            String path = exchange.getRequest().getURI().getPath();
            AuthorizationRe authorizationRe = new AuthorizationRe(appKey, service, exchange.getRequest());

            return HttpHelper.getForJson(AUTH_HOST,
                    null, CommonCodeJsonResponse.class)
                    .flatMap(res -> hashAuthentication(res, chain, exchange));
        };
    }


    private Mono<Void> hashAuthentication(ResponseEntity<CommonCodeJsonResponse> responseEntity,
                                          GatewayFilterChain chain, ServerWebExchange exchange) {
        int value = responseEntity.getStatusCode().value();
        if (value == 200) {
            // 设置app 信息
            exchange.getAttributes().put(ExchangeAttributeConstant.APP_KEY_SECRET, "xxx");
            return chain.filter(exchange);
        }
        return responseWriter.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeJsonResponse.unauthorized(), exchange);
    }

}
