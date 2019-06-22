package com.weweibuy.gateway.manager.model.vo;

import lombok.Data;

/**
 * @author durenhao
 * @date 2019/5/19 15:10
 **/
@Data
public class FilterArgsUpdateVo {

    private Long id;

    private Long filterId;

    private String argsName;

    private String argsValue;

    private Long dictId;

    private String dictType;

    private String argsDesc;
}
