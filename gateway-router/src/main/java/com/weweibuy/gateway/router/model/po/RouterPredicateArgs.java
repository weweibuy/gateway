package com.weweibuy.gateway.router.model.po;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RouterPredicateArgs {
    /**
     * id
     */
    private Long id;

    /**
     * 关联断言表 predicate_id
     */
    private String predicateId;

    /**
     * 断言参数id
     */
    private String predicateArgId;

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