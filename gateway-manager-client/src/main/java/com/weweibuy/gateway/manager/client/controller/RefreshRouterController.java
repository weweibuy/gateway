package com.weweibuy.gateway.manager.client.controller;

import com.weweibuy.gateway.manager.client.model.event.CustomRefreshRoutesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author durenhao
 * @date 2019/5/22 23:07
 **/
@RestController
@RequestMapping("/_router-refresh")
public class RefreshRouterController {

    @Autowired
    private ApplicationContext applicationContext;

    @PutMapping
    public String refresh(){
        applicationContext.publishEvent(new CustomRefreshRoutesEvent(this));
        return "success";
    }

}
