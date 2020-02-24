package com.weweibuy.gateway.route.filter.authorization;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.fastjson.JSONObject;
import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
import com.weweibuy.gateway.core.mode.event.response.ResponseWriter;
import com.weweibuy.gateway.route.filter.constant.AuthenticationHeaderConstant;
import com.weweibuy.gateway.route.filter.constant.ExchangeAttributeConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @author durenhao
 * @date 2019/7/6 9:27
 **/
@Slf4j
@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Autowired
    private ResponseWriter responseWriter;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            String attribute = exchange.getAttribute(ExchangeAttributeConstant.SERVICE_KEY);
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String appKey = headers.getFirst(AuthenticationHeaderConstant.APP_KEY_HEADER);
            if (StringUtils.isBlank(appKey)) {
                return responseWriter.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeJsonResponse.unauthorized(), exchange);
            }

            Context enter = ContextUtil.enter(route.getId(), exchange.getRequest().getRemoteAddress().getHostString());
            return chain.filter(exchange);
        };
    }

    private DataBuffer getBodyBuffer(ServerHttpResponse response, Object o) {
        return response.bufferFactory().wrap(JSONObject.toJSONBytes(o));
    }

}
