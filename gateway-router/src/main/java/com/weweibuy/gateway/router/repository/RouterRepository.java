package com.weweibuy.gateway.router.repository;

import com.weweibuy.gateway.router.mapper.GatewayRouterMapper;
import com.weweibuy.gateway.router.model.example.GatewayRouterExample;
import com.weweibuy.gateway.router.model.po.GatewayRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author durenhao
 * @date 2021/8/12 21:22
 **/
@Repository
@RequiredArgsConstructor
public class RouterRepository {

    private final GatewayRouterMapper gatewayRouterMapper;


    public List<GatewayRouter> select(GatewayRouterExample example) {
        return gatewayRouterMapper.selectByExample(example);
    }

    public Optional<GatewayRouter> select(String routerId) {
        GatewayRouterExample example = GatewayRouterExample.newAndCreateCriteria()
                .andIsDeleteEqualTo(false)
                .andRouterIdEqualTo(routerId)
                .example();
        return Optional.ofNullable(gatewayRouterMapper.selectOneByExample(example));
    }

}
