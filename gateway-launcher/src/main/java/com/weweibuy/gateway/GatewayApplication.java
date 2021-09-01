package com.weweibuy.gateway;

import com.weweibuy.framework.rocketmq.annotation.EnableRocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author durenhao
 * @date 2019/5/12 22:38
 **/
@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
@EnableRocket(basePackages = {"com.weweibuy.gateway.GatewayApplication",
        "com.weweibuy.framework.common.lc.mq"})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
