package com.weweibuy.gateway.router.service;

import com.weweibuy.gateway.core.support.OpLogProperties;
import com.weweibuy.gateway.core.support.OpLogPropertiesLocator;
import com.weweibuy.gateway.router.mapper.OpLogConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * opLog 配置加载服务
 *
 * @author durenhao
 * @date 2021/8/31 18:16
 **/
@Service
@RequiredArgsConstructor
public class OpLogLocator implements OpLogPropertiesLocator {

    private final OpLogConfigMapper opLogConfigMapper;

    @Override
    public OpLogProperties getOpLogProperties() {
        OpLogProperties opLogProperties = new OpLogProperties();
        return opLogProperties;
    }





}
