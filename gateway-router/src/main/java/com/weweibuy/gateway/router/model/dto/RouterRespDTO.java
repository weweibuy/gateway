package com.weweibuy.gateway.router.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 路由数据响应
 *
 * @author durenhao
 * @date 2021/8/12 20:55
 **/
@Data
public class RouterRespDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 路由id
     */
    private String routerId;

    /**
     * 路由系统id
     */
    private String systemId;

    /**
     * 路由系统名称
     */
    private String systemName;

    /**
     * uri
     */
    private String routerUri;

    /**
     * 优先级
     */
    private Integer routerPriority;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;



}
