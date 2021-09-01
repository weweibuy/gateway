package com.weweibuy.gateway.route.filter.log;

import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.constant.RouterMetaDataConstant;
import com.weweibuy.gateway.core.utils.RequestIpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.BaseSubscriber;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

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
        LogParam logParam = buildLogParam(exchange);

        logOpLog(logParam, exchange);

        log.info("{}  {}  {}  {}  {}  {}  {}  {}  {}ms",
                logParam.getIp(),
                logParam.getHost(),
                logParam.getSystemId(),
                logParam.getHttpMethod(),
                logParam.getRouterToUri(),
                logParam.getPath(),
                logParam.getTrace(),
                logParam.getHttpStatus(),
                Duration.between(logParam.getReqTime(), LocalDateTime.now()).toMillis());
    }


    static void logOpLog(LogParam logParam, ServerWebExchange exchange) {
        Boolean output = Optional.ofNullable((Boolean) exchange.getAttribute(ExchangeAttributeConstant.OP_LOG_OUTPUT_ATTR))
                .orElse(false);

        if (!output) {
            return;
        }
        OpLogLogger.opLog(logParam, exchange);
    }

    static LogParam buildLogParam(ServerWebExchange exchange) {
        LogParam logParam = new LogParam();

        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        LocalDateTime requestTimestamp = exchange.getAttribute(ExchangeAttributeConstant.REQUEST_TIMESTAMP);
        String traceId = exchange.getAttribute(ExchangeAttributeConstant.TRACE_ID_ATTR);
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();

        HttpHeaders headers = request.getHeaders();
        String username = exchange.getAttribute(ExchangeAttributeConstant.USER_ID_ATTR);
        String systemId = Optional.ofNullable(route.getMetadata())
                .map(m -> (String) m.get(RouterMetaDataConstant.SYSTEM_ID))
                .orElse(StringUtils.EMPTY);

        logParam.setIp(RequestIpUtil.getIp(request));
        logParam.setUsername(username);
        logParam.setTrace(traceId);
        logParam.setHttpMethod(request.getMethod());
        logParam.setHttpStatus(exchange.getResponse().getStatusCode().value());
        logParam.setPath(uri.getPath());
        logParam.setParam(uri.getQuery());
        logParam.setHost(headers.getFirst(HttpHeaders.HOST));
        logParam.setSystemId(systemId);
        logParam.setRouterToUri(route.getUri().toString());
        logParam.setReqTime(requestTimestamp);

        return logParam;
    }

}
