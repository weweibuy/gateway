package com.weweibuy.gateway.router.config;

import com.weweibuy.gateway.router.dynamic.JdbcRouterDefinitionLocator;
import com.weweibuy.gateway.router.dynamic.JdbcRouterManger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author durenhao
 * @date 2020/2/22 22:34
 **/
@Configuration
@MapperScan(basePackages = "com.weweibuy.gateway.route.mapper")
public class GatewayRouterConfig {

    @Bean
    public RouteDefinitionLocator jdbcRouteDefinitionLocator() {
        return new JdbcRouterDefinitionLocator();
    }

    @Bean
    public JdbcRouterManger jdbcRouterManger() {
        return new JdbcRouterManger();
    }



}
