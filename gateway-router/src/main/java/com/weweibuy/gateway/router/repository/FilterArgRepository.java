package com.weweibuy.gateway.router.repository;

import com.weweibuy.gateway.router.mapper.RouterFilterArgsMapper;
import com.weweibuy.gateway.router.model.example.RouterFilterArgsExample;
import com.weweibuy.gateway.router.model.po.RouterFilterArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author durenhao
 * @date 2021/8/12 21:28
 **/
@Repository
@RequiredArgsConstructor
public class FilterArgRepository {

    private final RouterFilterArgsMapper routerFilterArgsMapper;


    public List<RouterFilterArgs> select(String filterId) {
        return routerFilterArgsMapper.selectByExample(
                RouterFilterArgsExample.newAndCreateCriteria()
                        .andIsDeleteEqualTo(false)
                        .andFilterIdEqualTo(filterId)
                        .example());
    }

    public List<RouterFilterArgs> select(List<String> filterIdList) {
        return routerFilterArgsMapper.selectByExample(
                RouterFilterArgsExample.newAndCreateCriteria()
                        .andIsDeleteEqualTo(false)
                        .andFilterIdIn(filterIdList)
                        .example());
    }


}
