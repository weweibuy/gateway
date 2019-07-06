package com.weweibuy.gateway.manager.client.config;

import com.weweibuy.gateway.manager.client.controller.RefreshRouterController;
import com.weweibuy.gateway.manager.client.router.JdbcRouteDefinitionLocator;
import com.weweibuy.gateway.manager.client.router.JdbcRouterManger;
import com.weweibuy.gateway.manager.client.suport.resolver.LbRateLimitKeyResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * @author durenhao
 * @date 2019/5/22 22:50
 **/
@Configuration
@MapperScan(basePackages = "com.weweibuy.gateway.manager.client.mapper")
@ComponentScans(value = @ComponentScan(basePackageClasses = {LbRateLimitKeyResolver.class}))
public class GatewayClientConfiguration {

    @Bean
    public RouteDefinitionLocator jdbcRouteDefinitionLocator() {
        return new JdbcRouteDefinitionLocator();
    }

    @Bean
    public JdbcRouterManger jdbcRouterManger() {
        return new JdbcRouterManger();
    }

    @Bean
    public RefreshRouterController refreshRouterController() {
        return new RefreshRouterController();
    }


}
