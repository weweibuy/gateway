package com.weweibuy.gateway.manager.model.vo;

import lombok.Data;

/**
 * @author durenhao
 * @date 2019/5/19 15:06
 **/
@Data
public class PredicateUpdateVo {

    private Long id;

    private String routerId;

    private String predicateName;

    private Long dictId;

    private String dictType;

    private String predicateDesc;


}
