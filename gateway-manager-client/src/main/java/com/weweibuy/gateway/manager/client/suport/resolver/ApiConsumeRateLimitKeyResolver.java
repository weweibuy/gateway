package com.weweibuy.gateway.manager.client.suport.resolver;

import com.weweibuy.gateway.manager.client.utils.RouteToRequestUrlUtil;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * api 接口限流; 针对于服务调用方; 如同一个api不同的地址, 针对暴露Api
 *
 * @author durenhao
 * @date 2019/7/6 15:23
 **/
@Component
public class ApiConsumeRateLimitKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return RouteToRequestUrlUtil.getLbUri(exchange)
                .map(uri -> Mono.just(uri.toString()))
                .orElseGet(Mono::empty);
    }
}
