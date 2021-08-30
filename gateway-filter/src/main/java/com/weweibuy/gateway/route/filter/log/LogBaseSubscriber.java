package com.weweibuy.gateway.route.filter.log;

import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.utils.RequestIpUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.BaseSubscriber;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 日志输出 订阅
 *
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
    protected void hookOnNext(Object value) {
        actual.onNext(value);
    }

    @Override
    protected void hookOnComplete() {
        try {
            recordLog(exchange);
        } finally {
            actual.onComplete();
        }
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        actual.onSubscribe(this);
    }


    @Override
    protected void hookOnError(Throwable t) {
        try {
            recordLog(exchange);
        } finally {
            actual.onError(t);
        }
    }


    static void recordLog(ServerWebExchange exchange) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        LocalDateTime requestTimestamp = exchange.getAttribute(ExchangeAttributeConstant.REQUEST_TIMESTAMP);
        String traceId = exchange.getAttribute(ExchangeAttributeConstant.TRACE_ID_ATTR);
        LocalDateTime now = LocalDateTime.now();
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        log.info("{} {} {} {} {} {} {} {}ms",
                RequestIpUtil.getIp(request),
                headers.get(HttpHeaders.HOST),
                request.getMethod(),
                route.getUri(),
                request.getURI().getPath(),
                traceId,
                exchange.getResponse().getStatusCode().value(),
                Duration.between(requestTimestamp, now).toMillis());
    }

}
