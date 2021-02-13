package com.weweibuy.gateway.sentinel.common.rule;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.weweibuy.gateway.sentinel.common.SentinelGatewayConstants;
import lombok.EqualsAndHashCode;

/**
 * @author durenhao
 * @date 2021/2/13 18:16
 **/
@EqualsAndHashCode(callSuper = true)
public class GatewayDegradeRule extends DegradeRule {

    private int resourceMode = SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID;


    public int getResourceMode() {
        return resourceMode;
    }

    public void setResourceMode(int resourceMode) {
        this.resourceMode = resourceMode;
    }
}
