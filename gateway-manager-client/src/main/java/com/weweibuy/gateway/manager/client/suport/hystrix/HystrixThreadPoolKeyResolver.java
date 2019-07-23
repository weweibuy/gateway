package com.weweibuy.gateway.manager.client.suport.hystrix;

import com.netflix.hystrix.HystrixThreadPoolKey;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author durenhao
 * @date 2019/7/7 21:20
 **/
public interface HystrixThreadPoolKeyResolver {

    HystrixThreadPoolKey getHystrixThreadPoolKey(ServerWebExchange exchange);

}
