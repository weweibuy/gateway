package com.weweibuy.gateway.router.model.po;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RouterPredicate {
    /**
     * id
     */
    private Long id;

    /**
     * route_id 关联路由表
     */
    private String routerId;

    /**
     * 断言id
     */
    private String predicateId;

    /**
     * 断言名称
     */
    private String predicateName;

    /**
     * 断言优先级
     */
    private Integer predicatePriority;

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