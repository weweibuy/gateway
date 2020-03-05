package com.weweibuy.gateway.route.filter.record;

import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @author durenhao
 * @date 2020/3/4 22:23
 **/
@Slf4j
@Component
public class AccessLogFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(ExchangeAttributeConstant.REQUEST_TIMESTAMP, LocalDateTime.now());
        return chain.filter(exchange).transform(p ->
                actual ->
                        p.subscribe(new LogBaseSubscriber(actual, exchange)));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }


}
