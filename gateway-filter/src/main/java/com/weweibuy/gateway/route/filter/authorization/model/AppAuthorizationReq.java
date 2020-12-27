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

    private String appKey;

    private HttpMethod httpMethod;

    private String path;

    private String service;

    public AppAuthorizationReq() {
    }

    public AppAuthorizationReq(String appKey, HttpMethod httpMethod, String path, String service) {
        this.appKey = appKey;
        this.httpMethod = httpMethod;
        this.path = path;
        this.service = service;
    }

    public AppAuthorizationReq(String appKey, String service, ServerHttpRequest request) {
        this.service = service;
        this.appKey = appKey;
        this.httpMethod = request.getMethod();
        this.path = request.getURI().getPath();
    }
}
