package com.weweibuy.gateway.manager.client.router;

import com.weweibuy.gateway.manager.client.mapper.*;
import com.weweibuy.gateway.manager.client.model.po.*;
import com.weweibuy.gateway.manager.client.model.vo.FilterVo;
import com.weweibuy.gateway.manager.client.model.vo.PredicateVo;
import com.weweibuy.gateway.manager.client.model.vo.RouterVo;
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
    private GatewayRouterMapper routerMapper;

    @Autowired
    private RouterPredicateMapper predicateMapper;

    @Autowired
    private PredicateArgsMapper predicateArgsMapper;

    @Autowired
    private RouterFilterMapper routerFilterMapper;

    @Autowired
    private FilterArgsMapper filterArgsMapper;

    public List<RouterVo> getAllRouter() {
        log.info("【路由加载】>>> 从DB中捞取路由数据");
        GatewayRouterExample example = new GatewayRouterExample();

        example.createCriteria()
                .andStatusEqualTo((byte) 0);
        List<GatewayRouter> gatewayRouters = routerMapper.selectByExample(example);

        RouterPredicateExample predicateExample = new RouterPredicateExample();
        return gatewayRouters.stream()
                .map(router -> {
                    String routerId = router.getRouterId();
                    predicateExample.createCriteria()
                            .andRouterIdEqualTo(routerId)
                            .andStatusEqualTo((byte) 0);

                    // 断言
                    List<RouterPredicate> routerPredicates = predicateMapper.selectByExample(predicateExample);
                    predicateExample.clear();

                    PredicateArgsExample predicateArgsExample = new PredicateArgsExample();
                    // 获取断言
                    List<PredicateVo> predicateVoList = routerPredicates.stream()
                            .map(predicate -> {
                                predicateArgsExample.createCriteria()
                                        .andPredicateIdEqualTo(predicate.getId())
                                        .andStatusEqualTo((byte) 0);
                                // 断言参数
                                List<PredicateArgs> predicateArgs = predicateArgsMapper.selectByExample(predicateArgsExample);
                                predicateArgsExample.clear();

                                Map<String, String> predicateArgsMap = predicateArgs.stream()
                                        .collect(Collectors.toMap(PredicateArgs::getArgsName, PredicateArgs::getArgsValue));
                                return PredicateVo.convert(predicate, predicateArgsMap);
                            })
                            .collect(Collectors.toList());

                    RouterFilterExample routerFilterExample = new RouterFilterExample();
                    routerFilterExample.createCriteria()
                            .andRouterIdEqualTo(routerId)
                            .andStatusEqualTo((byte) 0);

                    // 过滤器
                    List<RouterFilter> routerFilters = routerFilterMapper.selectByExample(routerFilterExample);
                    routerFilterExample.clear();

                    FilterArgsExample filterArgsExample = new FilterArgsExample();
                    List<FilterVo> filterVoList = routerFilters.stream()
                            .map(filter -> {
                                filterArgsExample.createCriteria()
                                        .andFilterIdEqualTo(filter.getId())
                                        .andStatusEqualTo((byte) 0);
                                List<FilterArgs> filterArgs = filterArgsMapper.selectByExample(filterArgsExample);
                                filterArgsExample.clear();

                                // 过滤器参数
                                Map<String, String> filterArgsMap = filterArgs.stream()
                                        .collect(Collectors.toMap(FilterArgs::getArgsName, FilterArgs::getArgsValue));
                                return FilterVo.convert(filter, filterArgsMap);
                            })
                            .collect(Collectors.toList());

                    return RouterVo.convert(router, predicateVoList, filterVoList);
                })
                .collect(Collectors.toList());
    }


}
