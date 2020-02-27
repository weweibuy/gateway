package com.weweibuy.gateway.core.mode.event.config;

import com.weweibuy.gateway.core.mode.event.exception.DefaultExceptionMatchHandler;
import com.weweibuy.gateway.core.mode.event.exception.ExceptionMatchHandlerComposite;
import com.weweibuy.gateway.core.mode.event.exception.WebExceptionHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.WebExceptionHandler;

import java.util.List;

/**
 * @author durenhao
 * @date 2020/2/23 19:08
 **/
@Configuration
public class CoreWebConfigurer {

    @Autowired(required = false)
    private List<WebConfigurer> webConfigurerList;


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebExceptionHandler webExceptionHandler() {
        ExceptionMatchHandlerComposite composite = new ExceptionMatchHandlerComposite();
        if (!CollectionUtils.isEmpty(webConfigurerList)) {
            webConfigurerList.forEach(c -> c.addExceptionMatchHandler(composite));
        }
        composite.addHandler(new DefaultExceptionMatchHandler());
        return new WebExceptionHandlerImpl( composite);
    }


}
