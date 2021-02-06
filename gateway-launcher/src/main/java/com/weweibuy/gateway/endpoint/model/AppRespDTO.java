package com.weweibuy.gateway.endpoint.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author durenhao
 * @date 2021/1/31 13:28
 **/
@Data
public class AppRespDTO {

    /**
     * id
     */
    private Long id;

    /**
     * app_id
     */
    private String appId;

    /**
     * app_name
     */
    private String appName;

    /**
     * app_secret
     */
    private String appSecret;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
