package com.weweibuy.gateway.router.repository;

import com.weweibuy.gateway.router.mapper.RouterPredicateArgsMapper;
import com.weweibuy.gateway.router.model.example.RouterPredicateArgsExample;
import com.weweibuy.gateway.router.model.po.RouterPredicateArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author durenhao
 * @date 2021/8/12 21:26
 **/
@Repository
@RequiredArgsConstructor
public class PredicateArgRepository {

    private final RouterPredicateArgsMapper routerPredicateArgsMapper;


    public List<RouterPredicateArgs> select(String predicateId) {
        return routerPredicateArgsMapper.selectByExample(
                RouterPredicateArgsExample.newAndCreateCriteria()
                        .andIsDeleteEqualTo(false)
                        .andPredicateIdEqualTo(predicateId)
                        .example());
    }

    public List<RouterPredicateArgs> select(List<String> predicateIdList) {
        return routerPredicateArgsMapper.selectByExample(
                RouterPredicateArgsExample.newAndCreateCriteria()
                        .andIsDeleteEqualTo(false)
                        .andPredicateIdIn(predicateIdList)
                        .example());
    }


}
