package com.weweibuy.gateway.manager.model.vo;

import com.weweibuy.gateway.manager.model.po.GatewayRouterExample;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author durenhao
 * @date 2019/5/19 15:42
 **/
@Data
public class RouterQueryVo {

    private Integer page = 1;

    private Integer rows = 10;

    private String routerId;

    private String systemId;

    private String systemName;

    private String uri;

    private Boolean isUse;

    public GatewayRouterExample convertExample() {
        GatewayRouterExample example = new GatewayRouterExample();
        GatewayRouterExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(routerId)) {
            criteria.andRouterIdEqualTo(routerId);
        }
        if (StringUtils.isNotBlank(systemId)) {
            criteria.andSystemIdEqualTo(systemId);
        }
        if (StringUtils.isNotBlank(systemName)) {
            criteria.andSystemNameLike(systemName.trim() + "%");
        }
        if (StringUtils.isNotBlank(uri)) {
            criteria.andUriLike(uri.trim() + "%");
        }
        if (isUse != null) {
            criteria.andIsUseEqualTo(isUse);
        }
        return example;
    }
}
