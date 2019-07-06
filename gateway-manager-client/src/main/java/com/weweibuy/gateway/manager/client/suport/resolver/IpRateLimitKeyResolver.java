package com.weweibuy.gateway.manager.client.suport.resolver;

import com.weweibuy.gateway.manager.client.utils.RequestIpUtil;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * IP 限流
 *
 * @author durenhao
 * @date 2019/7/6 15:38
 **/
@Component
public class IpRateLimitKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(RequestIpUtil.getReallyIp(exchange));
    }
}
