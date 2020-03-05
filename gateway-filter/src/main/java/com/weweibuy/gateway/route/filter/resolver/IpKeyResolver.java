package com.weweibuy.gateway.route.filter.resolver;

import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.utils.RequestIpUtil;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 解析ip
 *
 * @author durenhao
 * @date 2019/7/6 15:38
 **/
@Component
public class IpKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        String reallyIp = RequestIpUtil.getReallyIp(exchange);
        exchange.getAttributes().put(ExchangeAttributeConstant.REQUEST_IP_ATTR, reallyIp);
        return Mono.just(RequestIpUtil.getReallyIp(exchange));
    }
}
