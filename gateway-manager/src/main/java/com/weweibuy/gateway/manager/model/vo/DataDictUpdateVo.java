package com.weweibuy.gateway.manager.model.vo;

import lombok.Data;

/**
 * @author durenhao
 * @date 2019/5/19 18:31
 **/
@Data
public class DataDictUpdateVo {

    private Long id;

    private Long parentId;

    private String dictType;

    private String dictName;

    private String dictValues;

    private String dictDesc;
}
