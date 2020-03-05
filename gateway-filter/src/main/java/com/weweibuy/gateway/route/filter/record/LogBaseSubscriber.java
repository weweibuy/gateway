package com.weweibuy.gateway.route.filter.record;

import com.weweibuy.gateway.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.BaseSubscriber;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author durenhao
 * @date 2020/3/5 11:40
 **/
@Slf4j
public class LogBaseSubscriber extends BaseSubscriber {


    private Subscriber actual;

    private ServerWebExchange exchange;

    public LogBaseSubscriber(Subscriber actual, ServerWebExchange exchange) {
        this.actual = actual;
        this.exchange = exchange;
    }

    @Override
    protected void hookOnComplete() {
        log(exchange);
        actual.onComplete();
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        actual.onSubscribe(this);
    }


    @Override
    protected void hookOnError(Throwable t) {
        log(exchange);
        actual.onError(t);
    }

    private void log(ServerWebExchange exchange) {
        LocalDateTime now = LocalDateTime.now();
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        log.info("{} {} {} {} {} {} {}", ip, DateUtils.toDateFormat(now), headers.get("Host"),
                request.getMethod(), request.getURI().getPath(),
                Duration.between(now, LocalDateTime.now()).toMillis(),
                exchange.getResponse().getStatusCode().value());
    }

}
