package com.weweibuy.gateway.route.mapper;

import com.weweibuy.gateway.route.model.po.GatewayRoute;
import com.weweibuy.gateway.route.model.po.GatewayRouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GatewayRouteMapper {
    long countByExample(GatewayRouteExample example);

    int deleteByExample(GatewayRouteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GatewayRoute record);

    int insertSelective(GatewayRoute record);

    GatewayRoute selectOneByExample(GatewayRouteExample example);

    List<GatewayRoute> selectByExample(GatewayRouteExample example);

    GatewayRoute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GatewayRoute record, @Param("example") GatewayRouteExample example);

    int updateByExample(@Param("record") GatewayRoute record, @Param("example") GatewayRouteExample example);

    int updateByPrimaryKeySelective(GatewayRoute record);

    int updateByPrimaryKey(GatewayRoute record);
}