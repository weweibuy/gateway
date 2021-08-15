package com.weweibuy.gateway.router.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author durenhao
 * @date 2021/8/12 21:17
 **/
@Data
public class FilterArgRespDTO {

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
