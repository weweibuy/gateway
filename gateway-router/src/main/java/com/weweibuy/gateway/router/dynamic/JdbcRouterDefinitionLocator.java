package com.weweibuy.gateway.router.dynamic;

import com.weweibuy.gateway.core.mode.event.CustomRefreshRoutesEvent;
import com.weweibuy.gateway.router.model.vo.FilterVo;
import com.weweibuy.gateway.router.model.vo.PredicateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.cache.CacheFlux;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2019/5/20 22:28
 **/
@Slf4j
public class JdbcRouterDefinitionLocator implements RouteDefinitionLocator, ApplicationListener<CustomRefreshRoutesEvent> {

    @Autowired
    private JdbcRouterManger jdbcRouterManger;

    private final Flux<RouteDefinition> routeDefinition;

    private final Map<String, List> cache = new HashMap<>();

    public JdbcRouterDefinitionLocator() {
        routeDefinition = CacheFlux.lookup(cache, "routeDefinitions", RouteDefinition.class)
                .onCacheMissResume(() -> loadFormDB()
                        .sort(AnnotationAwareOrderComparator.INSTANCE));
    }

    /**
     * 从DB 中加载数据
     *
     * @return
     */
    private Flux<RouteDefinition> loadFormDB() {
        return Flux.fromStream(jdbcRouterManger.getAllRouter().stream()
                .map(routerVo -> {
                    String routerId = routerVo.getRouterId();
                    RouteDefinition routeDefinition = new RouteDefinition();
                    List<PredicateVo> predicates = routerVo.getPredicates();
                    // 断言
                    List<PredicateDefinition> predicateDefinitions = predicates.stream()
                            .sorted(Comparator.comparing(PredicateVo::getPredicatePriority))
                            .map(predicate -> {
                                PredicateDefinition predicateDefinition = new PredicateDefinition();
                                predicateDefinition.setName(predicate.getPredicateName());
                                predicateDefinition.setArgs(predicate.getPredicateArgs());
                                return predicateDefinition;
                            })
                            .collect(Collectors.toList());

                    List<FilterVo> filters = routerVo.getFilters();

                    // 过滤器
                    List<FilterDefinition> filterDefinitions = filters.stream()
                            // 根据优先级排序, 排序越小越靠前
                            .sorted(Comparator.comparing(FilterVo::getFilterPriority))
                            .map(filter -> {
                                FilterDefinition filterDefinition = new FilterDefinition();
                                filterDefinition.setName(filter.getFilterName());
                                filterDefinition.setArgs(filter.getFilterArgs());
                                return filterDefinition;
                            })
                            .collect(Collectors.toList());

                    routeDefinition.setPredicates(predicateDefinitions);
                    routeDefinition.setFilters(filterDefinitions);
                    routeDefinition.setId(routerId);
                    routeDefinition.setOrder(routerVo.getRouterPriority());
                    routeDefinition.setUri(UriComponentsBuilder.fromUriString(routerVo.getRouterUri()).build().toUri());
                    return routeDefinition;
                }));
    }


    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return this.routeDefinition;
    }


    /**
     * 防止 eureka 的心跳事件不断刷新路由
     * TODO 有没有更好的办法??
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(CustomRefreshRoutesEvent event) {
        log.info("【路由刷新】>>> 接收到自定义路由刷新事件");
        this.cache.clear();
    }
}
