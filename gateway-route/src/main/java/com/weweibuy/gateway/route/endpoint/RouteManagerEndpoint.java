package com.weweibuy.gateway.route.endpoint;

import com.weweibuy.gateway.core.mode.event.CustomRefreshRoutesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author durenhao
 * @date 2020/2/23 11:11
 **/
@RestController
@RequestMapping("/gateway/route")
public class RouteManagerEndpoint {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @PostMapping
    public String refreshRoute() {
        applicationContext.publishEvent(new CustomRefreshRoutesEvent(this));
        return "success";
    }


    @GetMapping
    public Mono listRoute() {
        return routeDefinitionLocator.getRouteDefinitions()
                .collectList();
    }

}
