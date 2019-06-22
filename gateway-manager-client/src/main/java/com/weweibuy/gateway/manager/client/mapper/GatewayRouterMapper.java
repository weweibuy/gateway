package com.weweibuy.gateway.manager.client.mapper;

import com.weweibuy.gateway.manager.client.model.po.GatewayRouter;
import com.weweibuy.gateway.manager.client.model.po.GatewayRouterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GatewayRouterMapper {
    long countByExample(GatewayRouterExample example);

    int deleteByExample(GatewayRouterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GatewayRouter record);

    int insertSelective(GatewayRouter record);

    GatewayRouter selectOneByExample(GatewayRouterExample example);

    List<GatewayRouter> selectByExample(GatewayRouterExample example);

    GatewayRouter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GatewayRouter record, @Param("example") GatewayRouterExample example);

    int updateByExample(@Param("record") GatewayRouter record, @Param("example") GatewayRouterExample example);

    int updateByPrimaryKeySelective(GatewayRouter record);

    int updateByPrimaryKey(GatewayRouter record);
}