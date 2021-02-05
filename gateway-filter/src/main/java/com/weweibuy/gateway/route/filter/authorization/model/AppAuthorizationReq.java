package com.weweibuy.gateway.route.filter.authorization.model;

import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * App 鉴权请求
 *
 * @author durenhao
 * @date 2020/2/24 21:24
 **/
@Data
public class AppAuthorizationReq {

    private String clientId;

    private HttpMethod httpMethod;

    private String path;

    private String service;

    private String accessToken;


    public AppAuthorizationReq(String clientId, String service, ServerHttpRequest request, String accessToken) {
        this.service = service;
        this.clientId = clientId;
        this.httpMethod = request.getMethod();
        this.path = request.getURI().getPath();
        this.accessToken = accessToken;
    }
}
