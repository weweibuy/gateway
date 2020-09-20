package com.weweibuy.gateway.core.mode.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义路由刷新事件
 *
 * @author durenhao
 * @date 2019/5/25 10:29
 **/
public class CustomRefreshRoutesEvent extends ApplicationEvent {

    public CustomRefreshRoutesEvent(Object source) {
        super(source);
    }
}
