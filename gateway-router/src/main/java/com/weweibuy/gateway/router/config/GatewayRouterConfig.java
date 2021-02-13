package com.weweibuy.gateway.router.config;

import com.weweibuy.gateway.router.dynamic.JdbcRouterDefinitionLocator;
import com.weweibuy.gateway.router.dynamic.JdbcRouterManger;
import org.springframework.cloud.gateway.route.CompositeRouteLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author durenhao
 * @date 2020/2/22 22:34
 **/
@Configuration
public class GatewayRouterConfig {

    @Bean
    public RouteDefinitionLocator jdbcRouteDefinitionLocator() {
        return new JdbcRouterDefinitionLocator();
    }

    @Bean
    @Primary
    public RouteLocator cachedCompositeRouteLocator(List<RouteLocator> routeLocators) {
        return new SyncLoadCachingRouteLocator(
                new CompositeRouteLocator(Flux.fromIterable(routeLocators)));
    }

    @Bean
    public JdbcRouterManger jdbcRouterManger() {
        return new JdbcRouterManger();
    }


}
