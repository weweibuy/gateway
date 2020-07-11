package com.weweibuy.gateway.route.filter.trace;

import com.weweibuy.framework.common.core.model.constant.CommonConstant;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @author durenhao
 * @date 2020/7/11 13:02
 **/
@Component
public class AddTraceHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String traceCode = exchange.getAttribute(ExchangeAttributeConstant.TRACE_ID_ATTR);
            String userCode = exchange.getAttribute(ExchangeAttributeConstant.USER_ID_ATTR);

            ServerHttpRequest.Builder builder = exchange.getRequest().mutate()
                    .header(CommonConstant.LogTraceConstant.HTTP_TRACE_CODE_HEADER, traceCode);
            if (StringUtils.isNotBlank(userCode)) {
                builder.header(CommonConstant.LogTraceConstant.HTTP_USER_CODE_HEADER, userCode);
            }
            return chain.filter(exchange.mutate().request(builder.build()).build());
        };
    }
}
