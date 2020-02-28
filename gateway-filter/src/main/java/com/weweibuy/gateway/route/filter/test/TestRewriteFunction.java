package com.weweibuy.gateway.route.filter.test;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author durenhao
 * @date 2020/2/28 11:06
 **/
@Slf4j
@Component
public class TestRewriteFunction implements RewriteFunction<Object, Object> {

    @Override
    public Publisher<Object> apply(ServerWebExchange serverWebExchange, Object o) {
        log.info("请求参数: {}", o);
        return Mono.just(o);
    }
}
