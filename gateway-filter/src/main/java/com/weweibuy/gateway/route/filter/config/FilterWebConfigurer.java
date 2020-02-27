package com.weweibuy.gateway.route.filter.config;

import com.weweibuy.gateway.core.mode.event.config.WebConfigurer;
import com.weweibuy.gateway.core.mode.event.exception.ExceptionMatchHandlerComposite;
import com.weweibuy.gateway.route.filter.sentinel.SentinelExceptionMatchHandler;
import org.springframework.stereotype.Component;

/**
 * @author durenhao
 * @date 2020/2/23 19:06
 **/
@Component
public class FilterWebConfigurer implements WebConfigurer {

    @Override
    public void addExceptionMatchHandler(ExceptionMatchHandlerComposite composite) {
        composite.addHandler(new SentinelExceptionMatchHandler());
    }
}
