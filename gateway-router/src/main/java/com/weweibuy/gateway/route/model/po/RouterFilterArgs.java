package com.weweibuy.gateway.route.model.po;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RouterFilterArgs {
    /**
     * id
     */
    private Long id;

    /**
     * 关联过滤器表 filter_id
     */
    private String filterId;

    /**
     * 过滤器参数id
     */
    private String filterArgsId;

    /**
     * 参数名
     */
    private String argsName;

    /**
     * 参数值
     */
    private String argsValue;

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