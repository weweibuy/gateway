package com.weweibuy.gateway.manager.client.suport.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.server.ServerWebExchange;

/**
 * 解析hystrix 的groupKey , commandKey 和 threadKey
 *
 * @author durenhao
 * @date 2019/7/7 21:14
 **/
public interface HystrixGroupKeyResolver {


    HystrixCommandGroupKey getHystrixGroupKey(ServerWebExchange exchange);



}
