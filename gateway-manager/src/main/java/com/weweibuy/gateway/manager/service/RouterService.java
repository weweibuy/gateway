package com.weweibuy.gateway.manager.service;

import com.weweibuy.gateway.manager.model.dto.RouterPageQueryDto;
import com.weweibuy.gateway.manager.model.po.GatewayRouter;
import com.weweibuy.gateway.manager.model.vo.RouterAddVo;
import com.weweibuy.gateway.manager.model.vo.RouterQueryVo;
import com.weweibuy.gateway.manager.model.vo.RouterUpdateVo;

/**
 * @author durenhao
 * @date 2019/5/16 0:04
 **/
public interface RouterService {

    GatewayRouter getRouterById(Long id);

    void addRouter(RouterAddVo routerAddVo);

    void updateRouter(RouterUpdateVo routerUpdateVo);

    RouterPageQueryDto getRouters(RouterQueryVo queryVo);

    void deleteRouter(Long id);

}
