package com.weweibuy.gateway.core.advice;

import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 异常匹配处理
 *
 * @author durenhao
 * @date 2020/2/23 18:34
 **/
public interface ExceptionMatchHandler {

    /**
     * 是否匹配
     *
     * @param exchange
     * @param ex
     * @return
     */
    boolean match(ServerWebExchange exchange, Throwable ex);

    /**
     * 处理异常
     *
     * @param exchange
     * @param ex
     * @return
     */
    Mono<ServerResponse> handler(ServerWebExchange exchange, Throwable ex);


}
