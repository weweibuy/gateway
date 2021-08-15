package com.weweibuy.gateway.router.repository;

import com.weweibuy.gateway.router.mapper.RouterFilterMapper;
import com.weweibuy.gateway.router.model.example.RouterFilterExample;
import com.weweibuy.gateway.router.model.po.RouterFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author durenhao
 * @date 2021/8/12 21:28
 **/
@Repository
@RequiredArgsConstructor
public class FilterRepository {

    private final RouterFilterMapper routerFilterMapper;


    public List<RouterFilter> select(String routerId) {
        return routerFilterMapper.selectByExample(
                RouterFilterExample.newAndCreateCriteria()
                        .andIsDeleteEqualTo(false)
                        .andRouterIdEqualTo(routerId)
                        .example());
    }


}
