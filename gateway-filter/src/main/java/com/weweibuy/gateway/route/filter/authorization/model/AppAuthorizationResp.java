package com.weweibuy.gateway.route.filter.authorization.model;

import lombok.Data;

/**
 * App 鉴权请求
 *
 * @author durenhao
 * @date 2020/2/24 21:24
 **/
@Data
public class AppAuthorizationResp {

    private String appId;

    private String appName;

    private String appSecret;

    /**
     * 请求服务
     */
    private String service;

}
