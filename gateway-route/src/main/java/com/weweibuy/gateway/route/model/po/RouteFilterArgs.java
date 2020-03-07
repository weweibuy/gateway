package com.weweibuy.gateway.route.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RouteFilterArgs {
    private Long id;

    private String filterId;

    private String filterArgsId;

    private String argsName;

    private String argsValue;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}