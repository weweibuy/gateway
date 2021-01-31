package com.weweibuy.gateway.core.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.weweibuy.framework.common.core.model.constant.CommonConstant;
import com.weweibuy.gateway.core.advice.DefaultExceptionMatchHandler;
import com.weweibuy.gateway.core.advice.ExceptionMatchHandlerComposite;
import com.weweibuy.gateway.core.advice.WebExceptionHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.WebExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        return new WebExceptionHandlerImpl(composite);
    }


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer localDateTimeJackson2ObjectMapperBuilderCustomizer() {
        return builder ->
                builder.serializerByType(LocalDateTime.class, localDateTimeSerializer())
                        .deserializerByType(LocalDateTime.class, localDateTimeDeserializer());

    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer localDateJackson2ObjectMapperBuilderCustomizer() {
        return builder ->
                builder.serializerByType(LocalDate.class, localDateSerializer())
                        .deserializerByType(LocalDate.class, localDateDeserializer());
    }


    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(CommonConstant.DateConstant.STANDARD_DATE_TIME_FORMATTER);
    }

    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(CommonConstant.DateConstant.STANDARD_DATE_TIME_FORMATTER);
    }

    public LocalDateSerializer localDateSerializer() {
        return new LocalDateSerializer(CommonConstant.DateConstant.STANDARD_DATE_FORMATTER);
    }

    public LocalDateDeserializer localDateDeserializer() {
        return new LocalDateDeserializer(CommonConstant.DateConstant.STANDARD_DATE_FORMATTER);
    }

}
