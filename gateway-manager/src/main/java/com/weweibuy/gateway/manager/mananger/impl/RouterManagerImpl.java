package com.weweibuy.gateway.manager.mananger.impl;

import com.weweibuy.gateway.manager.mananger.RouterManager;
import com.weweibuy.gateway.manager.mapper.*;
import com.weweibuy.gateway.manager.model.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
        RouterPredicate routerPredicate = predicateMapper.selectByPrimaryKey(id);
        if (Objects.isNull(routerPredicate)) {
            return;
        }
        // 删除参数
        PredicateArgsExample example = new PredicateArgsExample();
        example.createCriteria()
                .andPredicateIdEqualTo(routerPredicate.getPredicateId());
        predicateArgsMapper.deleteByExample(example);
        // 删除断言
        predicateMapper.deleteByPrimaryKey(id);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePredicateByRouterId(String routerId) {
        RouterPredicateExample example = new RouterPredicateExample();
        example.createCriteria()
                .andRouterIdEqualTo(routerId);
        List<RouterPredicate> predicateList = predicateMapper.selectByExample(example);

        PredicateArgsExample predicateArgsExample = new PredicateArgsExample();
        predicateList.forEach(predicate -> {
            // 删除参数
            predicateArgsExample.createCriteria()
                    .andPredicateIdEqualTo(predicate.getPredicateId());
            predicateArgsMapper.deleteByExample(predicateArgsExample);
            predicateArgsExample.clear();
        });
    }

    @Override
    public void deleteFilterById(Long id) {

        RouterFilter routerFilter = filterMapper.selectByPrimaryKey(id);
        if (Objects.isNull(routerFilter)) {
            return;
        }
        FilterArgsExample filterArgsExample = new FilterArgsExample();
        filterArgsExample.createCriteria()
                .andFilterIdEqualTo(routerFilter.getFilterId());
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
                    .andFilterIdEqualTo(filter.getFilterId());
            // 删除参数
            filterArgsMapper.deleteByExample(filterArgsExample);
            filterArgsExample.clear();
            // 删除过滤器
            deleteFilterById(filter.getId());
        });

    }
}
