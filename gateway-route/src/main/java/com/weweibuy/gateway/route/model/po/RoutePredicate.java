package com.weweibuy.gateway.route.model.po;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RoutePredicate {
    private Long id;

    private String routeId;

    private String predicateId;

    private String predicateName;

    private Integer predicatePriority;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}