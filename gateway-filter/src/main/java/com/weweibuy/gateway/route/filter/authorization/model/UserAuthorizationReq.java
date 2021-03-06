package com.weweibuy.gateway.route.filter.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

/**
 * 用户鉴权请求
 *
 * @author durenhao
 * @date 2020/12/27 17:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorizationReq {

    private String service;

    private String path;

    private HttpMethod httpMethod;

    private String authorization;

}
