package com.weweibuy.gateway.route.filter.resolver;

import com.weweibuy.gateway.route.filter.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.route.filter.utils.RouteToRequestUrlUtil;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 解析请求地址
 *
 * @author durenhao
 * @date 2019/7/6 15:23
 **/
@Component
public class ApiConsumeKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return RouteToRequestUrlUtil.getLbUri(exchange)
                .map(uri -> Mono.just(uri.toString()))
                .map(uri -> {
                    exchange.getAttributes().put(ExchangeAttributeConstant.REQUEST_URL_ATTR, uri);
                    return uri;
                })
                .orElseGet(Mono::empty);
    }
}
