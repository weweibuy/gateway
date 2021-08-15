package com.weweibuy.gateway.router.repository;

import com.weweibuy.gateway.router.mapper.RouterPredicateMapper;
import com.weweibuy.gateway.router.model.example.RouterPredicateExample;
import com.weweibuy.gateway.router.model.po.RouterPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author durenhao
 * @date 2021/8/12 21:26
 **/
@Repository
@RequiredArgsConstructor
public class PredicateRepository {

    private final RouterPredicateMapper routerPredicateMapper;


    public List<RouterPredicate> select(String routerId) {
        return routerPredicateMapper.selectByExample(
                RouterPredicateExample.newAndCreateCriteria()
                        .andIsDeleteEqualTo(false)
                        .andRouterIdEqualTo(routerId)
                        .example());
    }


}
