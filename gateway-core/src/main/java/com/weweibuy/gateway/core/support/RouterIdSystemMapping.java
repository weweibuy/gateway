package com.weweibuy.gateway.core.support;

import java.util.Optional;

/**
 * @author durenhao
 * @date 2021/2/7 21:22
 **/
public interface RouterIdSystemMapping {

    /**
     * RouterId 获取 系统 id
     *
     * @param routerId
     * @return
     */
    Optional<String> routerIdToSystem(String routerId);

}
