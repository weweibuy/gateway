package com.weweibuy.gateway.router.model.dto;

import com.weweibuy.framework.common.core.model.dto.CommonPageRequest;
import com.weweibuy.gateway.router.model.example.GatewayRouterExample;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * 路由数据查询
 *
 * @author durenhao
 * @date 2021/8/12 20:55
 **/
@Data
public class RouterQueryReqDTO extends CommonPageRequest {

    /**
     * 路由id
     */
    private String routerId;

    /**
     * 路由系统id
     */
    private String systemId;

    /**
     * 路由系统名称
     */
    private String systemName;

    /**
     * uri
     */
    private String routerUri;

    public GatewayRouterExample toExample() {
        GatewayRouterExample.Criteria criteria = GatewayRouterExample.newAndCreateCriteria()
                .andIsDeleteEqualTo(false);

        Optional.ofNullable(routerId)
                .filter(StringUtils::isNotBlank)
                .ifPresent(criteria::andRouterIdEqualTo);

        Optional.ofNullable(systemId)
                .filter(StringUtils::isNotBlank)
                .ifPresent(criteria::andSystemIdEqualTo);

        Optional.ofNullable(systemName)
                .filter(StringUtils::isNotBlank)
                .ifPresent(n -> criteria.andSystemNameLike("%" + n + "%"));

        Optional.ofNullable(routerUri)
                .filter(StringUtils::isNotBlank)
                .ifPresent(n -> criteria.andRouterUriLike("%" + n + "%"));

        return criteria.example();
    }

}
