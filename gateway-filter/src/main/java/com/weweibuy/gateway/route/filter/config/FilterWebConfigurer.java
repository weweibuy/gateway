package com.weweibuy.gateway.route.filter.config;

import com.weweibuy.gateway.core.mode.event.config.WebConfigurer;
import com.weweibuy.gateway.core.mode.event.exception.ExceptionMatchHandlerComposite;
import com.weweibuy.gateway.core.mode.event.response.ResponseWriter;
import com.weweibuy.gateway.route.filter.sentinel.SentinelExceptionMatchHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author durenhao
 * @date 2020/2/23 19:06
 **/
@Component
public class FilterWebConfigurer implements WebConfigurer {

    @Autowired
    private ResponseWriter responseWriter;

    @Override
    public void addExceptionMatchHandler(ExceptionMatchHandlerComposite composite) {
        composite.addHandler(new SentinelExceptionMatchHandler(responseWriter));
    }
}
