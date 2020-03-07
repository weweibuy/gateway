package com.weweibuy.gateway.route.model.po;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RoutePredicateArgs {
    private Long id;

    private String predicateId;

    private String predicateArgId;

    private String argsName;

    private String argsValue;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}