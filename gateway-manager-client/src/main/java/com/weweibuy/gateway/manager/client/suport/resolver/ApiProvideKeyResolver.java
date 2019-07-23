package com.weweibuy.gateway.manager.client.suport.resolver;

import com.weweibuy.gateway.manager.client.model.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.manager.client.utils.RouteToRequestUrlUtil;
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
