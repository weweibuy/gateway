package com.weweibuy.gateway.router.service;

import com.weweibuy.gateway.core.support.OpLogProperties;
import com.weweibuy.gateway.core.support.OpLogPropertiesLocator;
import org.springframework.stereotype.Service;

/**
 * @author durenhao
 * @date 2021/8/31 18:16
 **/
@Service
public class OpLogLocator implements OpLogPropertiesLocator {


    @Override
    public OpLogProperties getOpLogProperties() {
        OpLogProperties opLogProperties = new OpLogProperties();
        return opLogProperties;
    }
}
