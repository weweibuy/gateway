package com.weweibuy.gateway.manager.model.vo;

import lombok.Data;

/**
 * @author durenhao
 * @date 2019/5/19 18:02
 **/
@Data
public class AccessSystemUpdateVo {

    private Long id;

    private String systemId;

    private String systemName;

    private String systemDomain;

    private String systemDesc;
}
