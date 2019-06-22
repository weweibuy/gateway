package com.weweibuy.gateway.manager.model.vo;

import lombok.Data;

/**
 * @author durenhao
 * @date 2019/5/19 15:08
 **/
@Data
public class PredicateArgsAddVo {

    private Long predicateId;

    private String argsName;

    private String argsValue;

    private Long dictId;

    private String dictType;

    private String argsDesc;


}
