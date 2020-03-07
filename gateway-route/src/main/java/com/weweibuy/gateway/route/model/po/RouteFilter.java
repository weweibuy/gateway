package com.weweibuy.gateway.route.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RouteFilter {
    private Long id;

    private String routeId;

    private String filterId;

    private String filterName;

    private Integer filterPriority;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}