package com.weweibuy.gateway.route.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GatewayRoute {
    private Long id;

    private String routeId;

    private String systemId;

    private String systemName;

    private String routeUri;

    private Integer routePriority;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}