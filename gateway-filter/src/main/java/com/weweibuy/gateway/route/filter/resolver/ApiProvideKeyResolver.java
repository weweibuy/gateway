package com.weweibuy.gateway.route.filter.resolver;

import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.route.filter.utils.RouteToRequestUrlUtil;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 解析目前服务的 lb url
 *
 * @author durenhao
 * @date 2019/7/6 15:12
 **/
@Component
public class ApiProvideKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return RouteToRequestUrlUtil.getLbUri(exchange)
                .map(uri -> Mono.just(uri.toString()))
                .map(uri -> {
                    exchange.getAttributes().put(ExchangeAttributeConstant.SERVICE_URL_ATTR, uri);
                    return uri;
                })
                .orElseGet(Mono::empty);
    }

}
