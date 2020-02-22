package com.weweibuy.gateway.route.filter.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Objects;

/**
 * 请求Ip 工具
 *
 * @author durenhao
 * @date 2019/7/6 15:39
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestIpUtil {

    /**
     * 获取请求的真实ip
     *
     * @param exchange
     * @return
     */
    public static String getReallyIp(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        List<String> strings = headers.get("x-forwarded-for");
        if (Objects.isNull(strings) || strings.isEmpty()) {
            return request.getRemoteAddress().getAddress().toString();
        } else {
            return strings.get(0).split(",")[0];
        }
    }


}
