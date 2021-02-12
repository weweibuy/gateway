package com.weweibuy.gateway.route.filter.sentinel;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.ResourceTypeConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.param.GatewayParamParser;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.sc.ServerWebExchangeItemParser;
import com.alibaba.csp.sentinel.adapter.gateway.sc.api.GatewayApiMatcherManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.api.matcher.WebExchangeApiMatcher;
import com.alibaba.csp.sentinel.adapter.reactor.ContextConfig;
import com.alibaba.csp.sentinel.adapter.reactor.EntryConfig;
import com.alibaba.csp.sentinel.adapter.reactor.SentinelReactorTransformer;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Sentinel 过滤器
 *
 * @author durenhao
 * @date 2021/2/12 19:15
 **/
@Component
public class SentinelGatewayFilterFactory extends AbstractGatewayFilterFactory<SentinelGatewayFilterFactory.Config> {

    private final GatewayParamParser<ServerWebExchange> paramParser = new GatewayParamParser<>(
            new ServerWebExchangeItemParser());

    public SentinelGatewayFilterFactory() {
        super(SentinelGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {


            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

            Mono<Void> asyncResult = chain.filter(exchange);
            String routeId = route.getId();
            Object[] params = paramParser.parseParameterFor(routeId, exchange,
                    r -> r.getResourceMode() == SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);

            asyncResult = asyncResult.transform(
                    new SentinelReactorTransformer<>(new EntryConfig(routeId, ResourceTypeConstants.COMMON_API_GATEWAY,
                            EntryType.IN, 1, params, new ContextConfig(contextName(routeId)))));

            Set<String> matchingApis = pickMatchingApiDefinitions(exchange);
            for (String apiName : matchingApis) {
                Object[] parameterParams = paramParser.parseParameterFor(apiName, exchange,
                        r -> r.getResourceMode() == SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME);
                asyncResult = asyncResult.transform(
                        new SentinelReactorTransformer<>(new EntryConfig(apiName, ResourceTypeConstants.COMMON_API_GATEWAY,
                                EntryType.IN, 1, parameterParams))
                );
            }

            return asyncResult;

        };
    }

    private String contextName(String route) {
        return SentinelGatewayConstants.GATEWAY_CONTEXT_ROUTE_PREFIX + route;
    }

    private Set<String> pickMatchingApiDefinitions(ServerWebExchange exchange) {
        return GatewayApiMatcherManager.getApiMatcherMap().values()
                .stream()
                .filter(m -> m.test(exchange))
                .map(WebExchangeApiMatcher::getApiName)
                .collect(Collectors.toSet());
    }


    @Data
    public static class Config {

        /**
         * API 定义分组  配置形式:  apiDefinitionList[0].apiName
         */
        private Set<ApiDefinitionConfig> apiDefinitionList;

        /**
         * 流控配置
         */
        private List<GatewayFlowRule> gatewayFlowRuleList;

        /**
         * 熔断配置
         */
        private List<DegradeRule> degradeRuleList;

        /**
         * 黑白名单
         */
        private List<AuthorityRule> authorityRuleList;
    }

    @Data
    public static class ApiDefinitionConfig {

        private String apiName;

        private List<String> path;


    }
}
