package com.weweibuy.gateway.router.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author durenhao
 * @date 2021/8/12 21:17
 **/
@Data
public class FilterRespDTO {

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 参数列表
     */
    private List<FilterArgRespDTO> argList;

}
