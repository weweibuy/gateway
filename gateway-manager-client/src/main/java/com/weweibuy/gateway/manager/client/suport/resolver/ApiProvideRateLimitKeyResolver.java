package com.weweibuy.gateway.manager.client.suport.resolver;

import com.weweibuy.gateway.manager.client.utils.RouteToRequestUrlUtil;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * api 接口限流; 针对提供服务, 1台实例与10台实例限流相同
 *
 * @author durenhao
 * @date 2019/7/6 15:12
 **/
@Component
public class ApiProvideRateLimitKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return RouteToRequestUrlUtil.getLbUri(exchange)
                .map(uri -> Mono.just(uri.toString()))
                .orElseGet(Mono::empty);
    }

}
