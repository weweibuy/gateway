package com.weweibuy.gateway.manager.controller.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author durenhao
 * @date 2019/5/19 9:28
 **/
@Slf4j
@Component
@Order(Integer.MIN_VALUE)
public class TraceCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long l = System.currentTimeMillis();
        try {
            MDC.put("TraceCode", UUID.randomUUID().toString());
            log.info("【请求日志】请求路径: {}, 请求方法: {}", request.getRequestURI(), request.getMethod());
            filterChain.doFilter(request, response);
        }finally {
            log.info("【响应日志】处理耗时: {} ms", System.currentTimeMillis() - l);
            MDC.clear();
        }
    }
}
