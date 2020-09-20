package com.weweibuy.gateway.route.model.po;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class GatewayRouter {
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
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}