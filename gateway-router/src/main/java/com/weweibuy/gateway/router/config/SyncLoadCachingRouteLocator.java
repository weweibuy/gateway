package com.weweibuy.gateway.router.config;

import com.weweibuy.gateway.router.dynamic.JdbcRouterDefinitionLocator;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.CachingRouteLocator;
import org.springframework.cloud.gateway.route.RouteLocator;

/**
 * for com.weweibuy.gateway.route.filter.sentinel.SentinelGatewayFilterFactory
 * <p>
 * 同步刷新路由
 *
 * @author durenhao
 * @date 2021/2/13 22:29
 **/
public class SyncLoadCachingRouteLocator extends CachingRouteLocator {

    public SyncLoadCachingRouteLocator(RouteLocator delegate) {
        super(delegate);
    }

    @Override
    public synchronized void onApplicationEvent(RefreshRoutesEvent event) {
        Object source = event.getSource();
        if (source instanceof JdbcRouterDefinitionLocator) {
            super.onApplicationEvent(event);
        }
    }
}
