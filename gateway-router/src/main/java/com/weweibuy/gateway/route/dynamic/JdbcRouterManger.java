package com.weweibuy.gateway.route.dynamic;

import com.weweibuy.gateway.route.mapper.*;
import com.weweibuy.gateway.route.model.example.*;
import com.weweibuy.gateway.route.model.po.*;
import com.weweibuy.gateway.route.model.vo.FilterVo;
import com.weweibuy.gateway.route.model.vo.PredicateVo;
import com.weweibuy.gateway.route.model.vo.RouterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2019/5/20 22:41
 **/
@Slf4j
public class JdbcRouterManger {

    @Autowired
    private GatewayRouterMapper routerMapper;

    @Autowired
    private RouterPredicateMapper predicateMapper;

    @Autowired
    private RouterPredicateArgsMapper predicateArgsMapper;

    @Autowired
    private RouterFilterMapper routerFilterMapper;

    @Autowired
    private RouterFilterArgsMapper filterArgsMapper;

    public List<RouterVo> getAllRouter() {
        log.info("【路由加载】>>> 从DB中捞取路由数据");
        GatewayRouterExample example = new GatewayRouterExample();

        example.createCriteria()
                .andIsDeleteEqualTo(false);
        List<GatewayRouter> gatewayRouters = routerMapper.selectByExample(example);

        RouterPredicateExample predicateExample = new RouterPredicateExample();
        return gatewayRouters.stream()
                .map(router -> {
                    String routerId = router.getRouterId();
                    predicateExample.createCriteria()
                            .andRouterIdEqualTo(routerId)
                            .andIsDeleteEqualTo(false);

                    // 断言
                    List<RouterPredicate> routerPredicates = predicateMapper.selectByExample(predicateExample);
                    predicateExample.clear();

                    RouterPredicateArgsExample predicateArgsExample = new RouterPredicateArgsExample();
                    // 获取断言
                    List<PredicateVo> predicateVoList = routerPredicates.stream()
                            .map(predicate -> {
                                predicateArgsExample.createCriteria()
                                        .andPredicateIdEqualTo(predicate.getPredicateId())
                                        .andIsDeleteEqualTo(false);

                                // 断言参数
                                List<RouterPredicateArgs> predicateArgs = predicateArgsMapper.selectByExample(predicateArgsExample);
                                predicateArgsExample.clear();

                                Map<String, String> predicateArgsMap = predicateArgs.stream()
                                        .collect(Collectors.toMap(RouterPredicateArgs::getArgsName, RouterPredicateArgs::getArgsValue));
                                return PredicateVo.convert(predicate, predicateArgsMap);
                            })
                            .collect(Collectors.toList());

                    RouterFilterExample routerFilterExample = new RouterFilterExample();
                    routerFilterExample.createCriteria()
                            .andRouterIdEqualTo(routerId)
                            .andIsDeleteEqualTo(false);

                    // 过滤器
                    List<RouterFilter> routerFilters = routerFilterMapper.selectByExample(routerFilterExample);
                    routerFilterExample.clear();

                    RouterFilterArgsExample filterArgsExample = new RouterFilterArgsExample();
                    List<FilterVo> filterVoList = routerFilters.stream()
                            .map(filter -> {
                                filterArgsExample.createCriteria()
                                        .andFilterIdEqualTo(filter.getFilterId())
                                        .andIsDeleteEqualTo(false);

                                List<RouterFilterArgs> filterArgs = filterArgsMapper.selectByExample(filterArgsExample);
                                filterArgsExample.clear();

                                // 过滤器参数
                                Map<String, String> filterArgsMap = filterArgs.stream()
                                        .collect(Collectors.toMap(RouterFilterArgs::getArgsName, RouterFilterArgs::getArgsValue));
                                return FilterVo.convert(filter, filterArgsMap);
                            })
                            .collect(Collectors.toList());

                    return RouterVo.convert(router, predicateVoList, filterVoList);
                })
                .collect(Collectors.toList());
    }


}
