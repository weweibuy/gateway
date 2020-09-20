package com.weweibuy.gateway.router.model.po;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RouterFilter {
    /**
     * id
     */
    private Long id;

    /**
     * 关联路由表 router_id
     */
    private String routerId;

    /**
     * 过滤器id
     */
    private String filterId;

    /**
     * 过滤器名称
     */
    private String filterName;

    /**
     * 过滤器优先级
     */
    private Integer filterPriority;

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