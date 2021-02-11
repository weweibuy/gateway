package com.weweibuy.gateway.core.config;

import com.weweibuy.framework.common.core.support.SystemIdGetter;
import com.weweibuy.gateway.core.advice.DefaultExceptionMatchHandler;
import com.weweibuy.gateway.core.advice.ExceptionMatchHandlerComposite;
import com.weweibuy.gateway.core.advice.WebExceptionHandlerImpl;
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

    @Autowired(required = false)
    private SystemIdGetter systemIdGetter;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebExceptionHandler webExceptionHandler() {
        ExceptionMatchHandlerComposite composite = new ExceptionMatchHandlerComposite();
        if (!CollectionUtils.isEmpty(webConfigurerList)) {
            webConfigurerList.forEach(c -> c.addExceptionMatchHandler(composite));
        }
        composite.addHandler(new DefaultExceptionMatchHandler(systemIdGetter));
        return new WebExceptionHandlerImpl(composite);
    }


}
