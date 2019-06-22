package com.weweibuy.gateway.manager;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author durenhao
 * @date 2019/5/12 22:44
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableApolloConfig
@MapperScan(basePackages = "com.weweibuy.gateway.manager.mapper")
public class GatewayManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayManagerApplication.class, args);
    }
}
