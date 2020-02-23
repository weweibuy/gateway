package com.weweibuy.gateway.core.mode.event.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author durenhao
 * @date 2020/2/23 14:57
 **/
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandlerImpl implements WebExceptionHandler {

    private List<ViewResolver> viewResolvers;

    private List<HttpMessageWriter<?>> messageWriters;

    private ExceptionMatchHandlerComposite composite;

    public WebExceptionHandlerImpl(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                   ServerCodecConfigurer serverCodecConfigurer, ExceptionMatchHandlerComposite composite) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.messageWriters = serverCodecConfigurer.getWriters();
        this.composite = composite;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        Mono<ServerResponse> responseMono = composite.getExceptionHandler(exchange, ex).handler(exchange, ex);

        return responseMono
                .doOnNext(response -> log(response, exchange, ex))
                .flatMap(response -> writeResponse(response, exchange));


    }


    private void log(ServerResponse response, ServerWebExchange exchange, Throwable ex) {
        log.warn("发生异常: {}", ex);
    }

    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
        return response.writeTo(exchange, new ResponseContext());
    }

    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return WebExceptionHandlerImpl.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return WebExceptionHandlerImpl.this.viewResolvers;
        }

    }

}
