package com.weweibuy.gateway.router.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author durenhao
 * @date 2021/8/14 16:58
 **/
@Data
public class PredicateArgRespDTO {

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
