package com.weweibuy.gateway.router.service;

import com.weweibuy.gateway.core.support.OpLogProperties;
import com.weweibuy.gateway.core.support.OpLogPropertiesLocator;
import com.weweibuy.gateway.router.model.po.OpLogConfig;
import com.weweibuy.gateway.router.repository.OpLogConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * opLog 配置加载服务
 *
 * @author durenhao
 * @date 2021/8/31 18:16
 **/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "op_log_config_cache")
public class CachedOpLogLocator implements OpLogPropertiesLocator {

    private final OpLogConfigRepository opLogConfigRepository;

    @Override
    @Cacheable(key = "'opLogProperties'")
    public OpLogProperties getOpLogProperties() {
        OpLogProperties opLogProperties = new OpLogProperties();
        List<OpLogConfig> configList = opLogConfigRepository.select();
        Map<Boolean, List<OpLogConfig>> patternPathMap = configList.stream()
                .collect(Collectors.groupingBy(c -> c.getReqPath().indexOf('*') != -1));
        Optional.ofNullable(patternPathMap.get(true))
                .map(l -> l.stream().collect(Collectors.groupingBy(OpLogConfig::getReqHost,
                        Collectors.mapping(OpLogConfig::getReqPath, Collectors.toList()))))
                .ifPresent(opLogProperties::setHostPatternPathListMap);

        Optional.ofNullable(patternPathMap.get(false))
                .map(l -> l.stream().collect(Collectors.groupingBy(OpLogConfig::getReqHost,
                        Collectors.mapping(OpLogConfig::getReqPath, Collectors.toSet()))))
                .ifPresent(opLogProperties::setHostExactPathSetMap);

        return opLogProperties;
    }


}
