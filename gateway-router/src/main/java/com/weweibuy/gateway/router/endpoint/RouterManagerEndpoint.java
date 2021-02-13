package com.weweibuy.gateway.router.endpoint;

import com.weweibuy.framework.common.core.model.dto.CommonCodeResponse;
import com.weweibuy.gateway.core.mode.event.CustomRefreshRoutesEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesResultEvent;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 动态路由 相关接口
 *
 * @author durenhao
 * @date 2020/2/23 11:11
 **/
@Slf4j
@RestController
@RequestMapping("/gateway/router")
public class RouterManagerEndpoint {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @PostMapping
    public synchronized CommonCodeResponse refreshRoute() {
        applicationContext.publishEvent(new CustomRefreshRoutesEvent(this));
        return CommonCodeResponse.success();
    }

    @EventListener
    public void onRefreshRoutesResultEvent(RefreshRoutesResultEvent event) {
        Throwable throwable = event.getThrowable();
        if (throwable != null) {
            log.error("【路由刷新】>>> 刷新路由异常: ", throwable);
        } else {
            log.info("【路由刷新】>>> 刷新路由成功");
        }
    }

    @GetMapping
    public Mono listRoute() {
        return routeDefinitionLocator.getRouteDefinitions()
                .collectList();
    }

    @GetMapping("/{id}")
    public Mono listRoute(@PathVariable String id) {
        return routeDefinitionLocator.getRouteDefinitions()
                .filter(r -> Objects.equals(r.getId(), id))
                .collectList();
    }

}
