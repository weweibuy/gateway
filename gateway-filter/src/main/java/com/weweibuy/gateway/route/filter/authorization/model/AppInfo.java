package com.weweibuy.gateway.route.filter.authorization.model;

import lombok.Data;

/**
 * App 信息
 *
 * @author durenhao
 * @date 2020/2/24 21:24
 **/
@Data
public class AppInfo {

    private String appId;

    private String appName;

    private String appSecret;


}
