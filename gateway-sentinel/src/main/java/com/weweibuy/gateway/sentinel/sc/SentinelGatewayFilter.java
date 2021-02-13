/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weweibuy.gateway.sentinel.sc;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.ResourceTypeConstants;
import com.alibaba.csp.sentinel.adapter.reactor.ContextConfig;
import com.alibaba.csp.sentinel.adapter.reactor.EntryConfig;
import com.alibaba.csp.sentinel.adapter.reactor.SentinelReactorTransformer;
import com.weweibuy.gateway.sentinel.common.SentinelGatewayConstants;
import com.weweibuy.gateway.sentinel.common.param.GatewayParamParser;
import com.weweibuy.gateway.sentinel.sc.api.GatewayApiMatcherManager;
import com.weweibuy.gateway.sentinel.sc.api.matcher.WebExchangeApiMatcher;
import com.weweibuy.gateway.sentinel.sc.callback.GatewayCallbackManager;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Eric Zhao
 * @since 1.6.0
 */
public class SentinelGatewayFilter implements GatewayFilter, GlobalFilter, Ordered {

    private final int order;

    public SentinelGatewayFilter() {
        this(Ordered.HIGHEST_PRECEDENCE);
    }

    public SentinelGatewayFilter(int order) {
        this.order = order;
    }

    private final GatewayParamParser<ServerWebExchange> paramParser = new GatewayParamParser<>(
        new ServerWebExchangeItemParser());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

        Mono<Void> asyncResult = chain.filter(exchange);
        if (route != null) {
            String routeId = route.getId();
            Object[] params = paramParser.parseParameterFor(routeId, exchange,
                r -> r.getResourceMode() == SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
            String origin = Optional.ofNullable(GatewayCallbackManager.getRequestOriginParser())
                .map(f -> f.apply(exchange))
                .orElse("");
            asyncResult = asyncResult.transform(
                new SentinelReactorTransformer<>(new EntryConfig(routeId, ResourceTypeConstants.COMMON_API_GATEWAY,
                    EntryType.IN, 1, params, new ContextConfig(contextName(routeId), origin)))
            );
        }

        Set<String> matchingApis = pickMatchingApiDefinitions(exchange, route.getId());
        for (String apiName : matchingApis) {
            Object[] params = paramParser.parseParameterFor(apiName, exchange,
                r -> r.getResourceMode() == SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME);
            asyncResult = asyncResult.transform(
                new SentinelReactorTransformer<>(new EntryConfig(apiName, ResourceTypeConstants.COMMON_API_GATEWAY,
                    EntryType.IN, 1, params))
            );
        }

        return asyncResult;
    }

    private String contextName(String route) {
        return SentinelGatewayConstants.GATEWAY_CONTEXT_ROUTE_PREFIX + route;
    }

    private Set<String> pickMatchingApiDefinitions(ServerWebExchange exchange, String routerId) {
        String path = exchange.getRequest().getPath().toString();
        return GatewayApiMatcherManager.getApiMatcherMap(routerId).values()
            .stream()
            .filter(m -> m.test(path))
            .map(WebExchangeApiMatcher::getApiName)
            .collect(Collectors.toSet());
    }

    @Override
    public int getOrder() {
        return order;
    }
}
