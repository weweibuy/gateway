package com.weweibuy.gateway.router.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置
 *
 * @author durenhao
 * @date 2020/11/26 22:35
 **/
@Configuration
public class CacheConfig {

    /**
     *
     * @return
     */
    @Bean
    public CacheManager caffeineCacheManager() {
        return buildCacheManager(100, 2000, 1, TimeUnit.DAYS);
    }


    private CaffeineCacheManager buildCacheManager(Integer initSize, Integer maxSize, long duration, TimeUnit unit) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine caffeine = Caffeine.newBuilder()
                .initialCapacity(initSize)
                .maximumSize(maxSize)
                .expireAfterWrite(duration, unit);
        cacheManager.setAllowNullValues(true);
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }



}
