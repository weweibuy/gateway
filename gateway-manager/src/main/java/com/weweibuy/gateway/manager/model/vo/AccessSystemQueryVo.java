package com.weweibuy.gateway.manager.model.vo;

import com.weweibuy.gateway.manager.model.po.AccessSystemExample;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author durenhao
 * @date 2019/5/19 17:54
 **/
@Data
public class AccessSystemQueryVo {

    private Integer page = 1;

    private Integer rows = 10;

    private String systemId;

    private String systemName;

    private String systemDomain;

    public AccessSystemExample buildExample() {
        AccessSystemExample example = new AccessSystemExample();
        AccessSystemExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(systemId)) {
            criteria.andSystemIdEqualTo(systemId);
        }
        if (StringUtils.isNotBlank(systemName)) {
            criteria.andSystemNameLike(systemName + "%");
        }
        if (StringUtils.isNotBlank(systemDomain)) {
            criteria.andSystemDomainLike(systemDomain + "%");
        }
        return example;
    }
}
