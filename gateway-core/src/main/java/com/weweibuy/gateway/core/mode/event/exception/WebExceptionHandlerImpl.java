package com.weweibuy.gateway.core.mode.event.exception;

import com.weweibuy.gateway.core.mode.event.http.ReactorHttpHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author durenhao
 * @date 2020/2/23 14:57
 **/
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandlerImpl implements WebExceptionHandler {


    private ExceptionMatchHandlerComposite composite;

    public WebExceptionHandlerImpl(ExceptionMatchHandlerComposite composite) {
        this.composite = composite;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        return composite.getExceptionHandler(exchange, ex)
                .handler(exchange, ex)
                .doOnNext(response -> log(response, exchange, ex))
                .flatMap(response -> ReactorHttpHelper.write(response, exchange));
    }


    private void log(ServerResponse response, ServerWebExchange exchange, Throwable ex) {
        log.warn("发生异常: {}", ex);
    }


}
