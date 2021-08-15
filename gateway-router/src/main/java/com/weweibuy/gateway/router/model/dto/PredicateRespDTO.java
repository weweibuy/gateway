package com.weweibuy.gateway.router.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 断言响应
 *
 * @author durenhao
 * @date 2021/8/12 21:18
 **/
@Data
public class PredicateRespDTO {

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
    private List<PredicateArgRespDTO> argList;


}
