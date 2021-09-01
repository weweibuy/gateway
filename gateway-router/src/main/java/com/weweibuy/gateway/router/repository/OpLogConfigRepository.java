package com.weweibuy.gateway.router.repository;

import com.weweibuy.gateway.router.mapper.OpLogConfigMapper;
import com.weweibuy.gateway.router.model.example.OpLogConfigExample;
import com.weweibuy.gateway.router.model.po.OpLogConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author durenhao
 * @date 2021/9/1 13:59
 **/
@Repository
@RequiredArgsConstructor
public class OpLogConfigRepository {

    private final OpLogConfigMapper opLogConfigMapper;


    public List<OpLogConfig> select() {
        return opLogConfigMapper.selectByExample(
                OpLogConfigExample.newAndCreateCriteria()
                        .andIsDeleteEqualTo(false)
                        .example());
    }


}
