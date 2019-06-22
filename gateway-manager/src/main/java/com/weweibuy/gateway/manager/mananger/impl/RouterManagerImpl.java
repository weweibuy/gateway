package com.weweibuy.gateway.manager.mananger.impl;

import com.weweibuy.gateway.manager.mananger.RouterManager;
import com.weweibuy.gateway.manager.mapper.*;
import com.weweibuy.gateway.manager.model.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/18 13:48
 **/
@Component
public class RouterManagerImpl implements RouterManager {

    @Autowired
    private GatewayRouterMapper routerMapper;

    @Autowired
    private RouterPredicateMapper predicateMapper;

    @Autowired
    private PredicateArgsMapper predicateArgsMapper;

    @Autowired
    private RouterFilterMapper filterMapper;

    @Autowired
    private FilterArgsMapper filterArgsMapper;

    @Override
    public void deleteRouter(Long id) {
        GatewayRouter router = routerMapper.selectByPrimaryKey(id);
        if (router != null) {
            // 删除断言
            deletePredicateByRouterId(router.getRouterId());
            // 删除过滤器
            deleteFiltersByRouterId(router.getRouterId());
            routerMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public void deletePredicateById(Long id) {
        // 删除参数
        PredicateArgsExample example = new PredicateArgsExample();
        example.createCriteria()
                .andPredicateIdEqualTo(id);
        predicateArgsMapper.deleteByExample(example);
        // 删除断言
        predicateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deletePredicateByRouterId(String routerId) {
        RouterPredicateExample example = new RouterPredicateExample();
        example.createCriteria()
                .andRouterIdEqualTo(routerId);
        List<RouterPredicate> predicateList = predicateMapper.selectByExample(example);

        PredicateArgsExample predicateArgsExample = new PredicateArgsExample();
        predicateList.forEach(predicate -> {
            // 删除参数
            predicateArgsExample.createCriteria()
                    .andPredicateIdEqualTo(predicate.getId());
            predicateArgsMapper.deleteByExample(predicateArgsExample);
            predicateArgsExample.clear();
        });
    }

    @Override
    public void deleteFilterById(Long id) {
        FilterArgsExample filterArgsExample = new FilterArgsExample();
        filterArgsExample.createCriteria()
                .andFilterIdEqualTo(id);
        // 删除参数
        filterArgsMapper.deleteByExample(filterArgsExample);
        // 删除过滤器
        filterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteFiltersByRouterId(String routerId) {
        RouterFilterExample example = new RouterFilterExample();
        example.createCriteria()
                .andRouterIdEqualTo(routerId);
        List<RouterFilter> routerFilters = filterMapper.selectByExample(example);

        FilterArgsExample filterArgsExample = new FilterArgsExample();
        routerFilters.forEach(filter -> {
            filterArgsExample.createCriteria()
                    .andFilterIdEqualTo(filter.getId());
            // 删除参数
            filterArgsMapper.deleteByExample(filterArgsExample);
            filterArgsExample.clear();
            // 删除过滤器
            deleteFilterById(filter.getId());
        });

    }
}
