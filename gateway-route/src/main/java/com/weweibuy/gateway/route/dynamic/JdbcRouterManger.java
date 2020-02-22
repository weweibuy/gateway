package com.weweibuy.gateway.route.dynamic;

import com.weweibuy.gateway.route.mapper.*;
import com.weweibuy.gateway.route.model.po.*;
import com.weweibuy.gateway.route.model.vo.FilterVo;
import com.weweibuy.gateway.route.model.vo.PredicateVo;
import com.weweibuy.gateway.route.model.vo.RouterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2019/5/20 22:41
 **/
@Slf4j
@Component
public class JdbcRouterManger {

    @Autowired
    private GatewayRouteMapper routerMapper;

    @Autowired
    private RoutePredicateMapper predicateMapper;

    @Autowired
    private RoutePredicateArgsMapper predicateArgsMapper;

    @Autowired
    private RouteFilterMapper routerFilterMapper;

    @Autowired
    private RouteFilterArgsMapper filterArgsMapper;

    public List<RouterVo> getAllRouter() {
        log.info("【路由加载】>>> 从DB中捞取路由数据");
        GatewayRouteExample example = new GatewayRouteExample();

        example.createCriteria()
                .andIsDeleteEqualTo(false);
        List<GatewayRoute> gatewayRouters = routerMapper.selectByExample(example);

        RoutePredicateExample predicateExample = new RoutePredicateExample();
        return gatewayRouters.stream()
                .map(router -> {
                    String routerId = router.getRouteId();
                    predicateExample.createCriteria()
                            .andRouteIdEqualTo(routerId);

                    // 断言
                    List<RoutePredicate> routerPredicates = predicateMapper.selectByExample(predicateExample);
                    predicateExample.clear();

                    RoutePredicateArgsExample predicateArgsExample = new RoutePredicateArgsExample();
                    // 获取断言
                    List<PredicateVo> predicateVoList = routerPredicates.stream()
                            .map(predicate -> {
                                predicateArgsExample.createCriteria()
                                        .andPredicateIdEqualTo(predicate.getPredicateId());
                                // 断言参数
                                List<RoutePredicateArgs> predicateArgs = predicateArgsMapper.selectByExample(predicateArgsExample);
                                predicateArgsExample.clear();

                                Map<String, String> predicateArgsMap = predicateArgs.stream()
                                        .collect(Collectors.toMap(RoutePredicateArgs::getArgsName, RoutePredicateArgs::getArgsValue));
                                return PredicateVo.convert(predicate, predicateArgsMap);
                            })
                            .collect(Collectors.toList());

                    RouteFilterExample routerFilterExample = new RouteFilterExample();
                    routerFilterExample.createCriteria()
                            .andRouteIdEqualTo(routerId);

                    // 过滤器
                    List<RouteFilter> routerFilters = routerFilterMapper.selectByExample(routerFilterExample);
                    routerFilterExample.clear();

                    RouteFilterArgsExample filterArgsExample = new RouteFilterArgsExample();
                    List<FilterVo> filterVoList = routerFilters.stream()
                            .map(filter -> {
                                filterArgsExample.createCriteria()
                                        .andFilterIdEqualTo(filter.getFilterId());
                                List<RouteFilterArgs> filterArgs = filterArgsMapper.selectByExample(filterArgsExample);
                                filterArgsExample.clear();

                                // 过滤器参数
                                Map<String, String> filterArgsMap = filterArgs.stream()
                                        .collect(Collectors.toMap(RouteFilterArgs::getArgsName, RouteFilterArgs::getArgsValue));
                                return FilterVo.convert(filter, filterArgsMap);
                            })
                            .collect(Collectors.toList());

                    return RouterVo.convert(router, predicateVoList, filterVoList);
                })
                .collect(Collectors.toList());
    }


}
