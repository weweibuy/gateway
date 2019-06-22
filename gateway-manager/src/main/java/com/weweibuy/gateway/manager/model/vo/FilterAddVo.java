package com.weweibuy.gateway.manager.model.vo;

import lombok.Data;

/**
 * @author durenhao
 * @date 2019/5/19 15:06
 **/
@Data
public class FilterAddVo {

    private Long predicateId;

    private String filterName;

    private String dictType;

    private Long dictId;

    private String filterDesc;

    private Integer priority;
}
