package com.weweibuy.gateway.route.filter.sentinel;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.ResourceTypeConstants;
import com.alibaba.csp.sentinel.adapter.reactor.ContextConfig;
import com.alibaba.csp.sentinel.adapter.reactor.EntryConfig;
import com.alibaba.csp.sentinel.adapter.reactor.SentinelReactorTransformer;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.weweibuy.framework.common.core.exception.Exceptions;
import com.weweibuy.framework.common.core.utils.OptionalEnhance;
import com.weweibuy.gateway.sentinel.common.SentinelGatewayConstants;
import com.weweibuy.gateway.sentinel.common.api.ApiDefinition;
import com.weweibuy.gateway.sentinel.common.api.GatewayApiDefinitionManager;
import com.weweibuy.gateway.sentinel.common.param.GatewayParamParser;
import com.weweibuy.gateway.sentinel.common.rule.GatewayDegradeRule;
import com.weweibuy.gateway.sentinel.common.rule.GatewayFlowRule;
import com.weweibuy.gateway.sentinel.common.rule.GatewayRuleManager;
import com.weweibuy.gateway.sentinel.sc.ServerWebExchangeItemParser;
import com.weweibuy.gateway.sentinel.sc.api.GatewayApiMatcherManager;
import com.weweibuy.gateway.sentinel.sc.api.matcher.WebExchangeApiMatcher;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesResultEvent;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Sentinel 过滤器
 *
 * @author durenhao
 * @date 2021/2/12 19:15
 **/
@Slf4j
@Component
public class SentinelGatewayFilterFactory extends AbstractGatewayFilterFactory<SentinelGatewayFilterFactory.Config> {

    private final GatewayParamParser<ServerWebExchange> paramParser = new GatewayParamParser<>(
            new ServerWebExchangeItemParser());

    private final Set<ApiDefinition> gatewayApiDefinitionSet = new HashSet<>();

    private final Set<GatewayFlowRule> gatewayFlowRuleSet = new HashSet<>();

    private final Set<DegradeRule> gatewayDegradeRuleSet = new HashSet<>();

    public SentinelGatewayFilterFactory() {
        super(SentinelGatewayFilterFactory.Config.class);
    }


    @EventListener
    public synchronized void onRefreshRoutesResultEvent(RefreshRoutesResultEvent event) {
        Throwable throwable = event.getThrowable();
        try {
            if (throwable == null) {
                GatewayApiDefinitionManager.loadApiDefinitions(new HashSet<>(gatewayApiDefinitionSet));
                GatewayRuleManager.loadRules(new HashSet<>(gatewayFlowRuleSet));
                DegradeRuleManager.loadRules(new ArrayList<>(gatewayDegradeRuleSet));
                log.warn("Sentinel配置刷新完成");
            }
        } finally {
            gatewayApiDefinitionSet.clear();
            gatewayFlowRuleSet.clear();
            gatewayDegradeRuleSet.clear();
        }

    }


    @Override
    public GatewayFilter apply(Config config) {

        boolean hasApiDefinition = initApiDefinition(config);
        boolean[] hasFlowRule = initFlowRule(config.getGatewayFlowRuleList(), config.getRouterId());
        boolean[] hasDegradeRule = initDegradeRule(config.getDegradeRuleList(), config.getRouterId());

        return (exchange, chain) -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            String routeId = route.getId();
            if (!config.getRouterId().equals(routeId)) {
                log.warn("实际路由id: {}, 与配置的路由id: {}, 不一致", routeId, config.getRouterId());
            }

            Mono<Void> asyncResult = chain.filter(exchange);

            // 路由级的流控或降价规则
            if (hasFlowRule[0] || hasDegradeRule[0]) {
                Object[] params = paramParser.parseParameterFor(routeId, exchange,
                        r -> r.getResourceMode() == SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
                asyncResult = asyncResult.transform(
                        new SentinelReactorTransformer<>(new EntryConfig(routeId, ResourceTypeConstants.COMMON_API_GATEWAY,
                                EntryType.IN, 1, params, new ContextConfig(contextName(routeId)))));
            }

            // 有定义API 并且 有对应流控或降级规则
            if (hasApiDefinition && (hasFlowRule[1] || hasDegradeRule[1])) {
                Set<String> matchingApis = pickMatchingApiDefinitions(exchange, route.getId());
                for (String apiName : matchingApis) {
                    Object[] parameterParams = paramParser.parseParameterFor(apiName, exchange,
                            r -> r.getResourceMode() == SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME);
                    asyncResult = asyncResult.transform(
                            new SentinelReactorTransformer<>(new EntryConfig(apiName, ResourceTypeConstants.COMMON_API_GATEWAY,
                                    EntryType.IN, 1, parameterParams)));
                }
            }

            return asyncResult;

        };
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


    private boolean initApiDefinition(Config config) {
        Set<ApiDefinitionConfig> apiDefinitionList = config.getApiDefinitionList();
        if (CollectionUtils.isNotEmpty(apiDefinitionList)) {
            Set<ApiDefinition> definitionSet = apiDefinitionList.stream()
                    .map(a -> buildApiDefinition(a, config.getRouterId()))
                    .collect(Collectors.toSet());
            gatewayApiDefinitionSet.addAll(definitionSet);
            return true;
        }
        return false;
    }

    private boolean[] initFlowRule(Set<GatewayFlowRule> configFlowRuleList, String routerId) {
        boolean[] booleans = new boolean[2];
        if (CollectionUtils.isNotEmpty(configFlowRuleList)) {
            Map<Integer, List<GatewayFlowRule>> resourceModeGroup = configFlowRuleList.stream()
                    .peek(r -> {
                        if (Objects.equals(r.getResourceMode(), SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID)
                                && StringUtils.isBlank(r.getResource())) {
                            r.setResource(routerId);
                        }
                    })
                    .collect(Collectors.groupingBy(f -> f.getResourceMode()));
            // 根据路由限流
            List<GatewayFlowRule> routeFlowRuleList = resourceModeGroup.get(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
            // 根据 api 限流
            List<GatewayFlowRule> apiFlowRuleList = resourceModeGroup.get(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME);

            OptionalEnhance.ofNullable(routeFlowRuleList)
                    .peek(list -> list.stream()
                            .filter(r -> !routerId.equals(r.getResource()))
                            .findFirst()
                            .ifPresent(r -> {
                                throw Exceptions.business("流控规则配置,若为routeId 模式,资源名称必须与路由id一致");
                            }))
                    .ifPresentOrElse(l -> booleans[0] = true, () -> booleans[0] = false);

            OptionalEnhance.ofNullable(apiFlowRuleList)
                    .ifPresentOrElse(l -> booleans[1] = true, () -> booleans[1] = false);
            gatewayFlowRuleSet.addAll(configFlowRuleList);
            return booleans;
        }
        booleans[0] = false;
        booleans[1] = false;
        return booleans;

    }

    private boolean[] initDegradeRule(List<GatewayDegradeRule> configDegradeRuleSet, String routerId) {
        boolean[] booleans = new boolean[2];
        if (CollectionUtils.isNotEmpty(configDegradeRuleSet)) {
            Map<Integer, List<GatewayDegradeRule>> resourceModeGroup = configDegradeRuleSet.stream()
                    .peek(r -> {
                        if (Objects.equals(r.getResourceMode(), SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID)
                                && StringUtils.isBlank(r.getResource())) {
                            r.setResource(routerId);
                        }
                    })
                    .collect(Collectors.groupingBy(f -> f.getResourceMode()));

            // 根据路由限流
            List<GatewayDegradeRule> routeFlowRuleList = resourceModeGroup.get(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
            // 根据 api 限流
            List<GatewayDegradeRule> apiFlowRuleList = resourceModeGroup.get(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME);

            gatewayDegradeRuleSet.addAll((List<DegradeRule>) (Object) configDegradeRuleSet);

            OptionalEnhance.ofNullable(routeFlowRuleList)
                    .peek(list -> list.stream()
                            .filter(r -> !routerId.equals(r.getResource()))
                            .findFirst()
                            .ifPresent(r -> {
                                throw Exceptions.business("流控降级配置,若为routeId 模式,资源名称必须与路由id一致");
                            }))
                    .ifPresentOrElse(l -> booleans[0] = true, () -> booleans[0] = false);

            OptionalEnhance.ofNullable(apiFlowRuleList)
                    .ifPresentOrElse(l -> booleans[1] = true, () -> booleans[1] = false);

            return booleans;
        }
        booleans[0] = false;
        booleans[1] = false;
        return booleans;
    }


    private ApiDefinition buildApiDefinition(ApiDefinitionConfig apiDefinitionConfig, String routerId) {
        ApiDefinition apiDefinition = new ApiDefinition(apiDefinitionConfig.getApiName(), routerId);
        Set<String> pathList = apiDefinitionConfig.getPathList();
        apiDefinition.setPath(pathList);
        return apiDefinition;
    }


    @Data
    public static class Config {

        @NotBlank(message = "routerId不能为空")
        private String routerId;

        /**
         * API 定义分组  配置形式:  apiDefinitionList[0].apiName
         */
        @Valid
        private Set<ApiDefinitionConfig> apiDefinitionList;

        /**
         * 流控配置; 设置 paramItem 将转为 热点参数限流
         */
        private Set<GatewayFlowRule> gatewayFlowRuleList;

        /**
         * 熔断配置
         * https://github.com/alibaba/Sentinel/wiki/%E7%86%94%E6%96%AD%E9%99%8D%E7%BA%A7
         */
        private List<GatewayDegradeRule> degradeRuleList;

    }

    @Data
    public static class ApiDefinitionConfig {

        private String apiName;

        @NotEmpty(message = "ApiDefinitionConfig pathList属性不能为空")
        private Set<String> pathList;


    }
}
