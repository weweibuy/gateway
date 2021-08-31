package com.weweibuy.gateway.route.filter.log;

import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.support.OpLogProperties;
import com.weweibuy.gateway.core.support.OpLogPropertiesLocator;
import com.weweibuy.gateway.route.filter.support.CachedBodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.event.EnableBodyCachingEvent;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.AbstractServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 缓存响应 body 过滤器
 *
 * @author durenhao
 * @date 2021/8/31 14:46
 **/
@Component
@RequiredArgsConstructor
public class OpLogCachedBodyStrFilter implements GlobalFilter, Ordered {

    private final ApplicationContext applicationContext;

    private final Set<String> cacheRequestBodyRouter = new CopyOnWriteArraySet<>();

    private final OpLogPropertiesLocator opLogPropertiesLocator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        if (!(serverHttpResponse instanceof AbstractServerHttpResponse)) {
            return chain.filter(exchange);
        }

        AbstractServerHttpResponse response = (AbstractServerHttpResponse) serverHttpResponse;
        if (needCacheBody(exchange, chain)) {
            sendCacheRequestBodyEvent(exchange);
            opLogAttr(exchange);
            return chain.filter(exchange.mutate()
                    .response(new CachedBodyResponse(response.getNativeResponse(),
                            response.bufferFactory(), exchange))
                    .build());
        }
        return chain.filter(exchange);
    }

    /**
     * 设置输出 opLog
     *
     * @param exchange
     */
    private void opLogAttr(ServerWebExchange exchange) {
        exchange.getAttributes()
                .put(ExchangeAttributeConstant.OP_LOG_OUTPUT_ATTR, true);
    }


    // 是否需要缓存 响应 body
    private boolean needCacheBody(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String host = request.getHeaders().getFirst(HttpHeaders.HOST);
        String path = request.getURI().getPath();

        OpLogProperties opLogProperties = opLogPropertiesLocator.getOpLogProperties();
        return opLogProperties.match(host, path);
    }

    private void sendCacheRequestBodyEvent(ServerWebExchange exchange) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String routeId = route.getId();
        if (!cacheRequestBodyRouter.contains(routeId)) {
            EnableBodyCachingEvent event = new EnableBodyCachingEvent(this, routeId);
            applicationContext.publishEvent(event);
            cacheRequestBodyRouter.add(routeId);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }


}
