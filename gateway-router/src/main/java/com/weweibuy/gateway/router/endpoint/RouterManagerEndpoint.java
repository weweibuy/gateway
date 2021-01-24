package com.weweibuy.gateway.router.endpoint;

import com.weweibuy.framework.common.core.model.dto.CommonCodeResponse;
import com.weweibuy.gateway.core.mode.event.CustomRefreshRoutesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author durenhao
 * @date 2020/2/23 11:11
 **/
@RestController
@RequestMapping("/gateway/router")
public class RouterManagerEndpoint {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @PostMapping
    public CommonCodeResponse refreshRoute() {
        applicationContext.publishEvent(new CustomRefreshRoutesEvent(this));
        return CommonCodeResponse.success();
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
