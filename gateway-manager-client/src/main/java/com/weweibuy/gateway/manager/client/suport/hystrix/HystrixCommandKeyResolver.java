package com.weweibuy.gateway.manager.client.suport.hystrix;

import com.netflix.hystrix.HystrixCommandKey;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author durenhao
 * @date 2019/7/7 21:20
 **/
public interface HystrixCommandKeyResolver {

    HystrixCommandKey getHystrixCommandKey(ServerWebExchange exchange);


}
