package com.weweibuy.gateway.route.mapper;

import com.weweibuy.gateway.route.model.po.RouteFilterArgs;
import com.weweibuy.gateway.route.model.po.RouteFilterArgsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RouteFilterArgsMapper {
    long countByExample(RouteFilterArgsExample example);

    int deleteByExample(RouteFilterArgsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RouteFilterArgs record);

    int insertSelective(RouteFilterArgs record);

    RouteFilterArgs selectOneByExample(RouteFilterArgsExample example);

    List<RouteFilterArgs> selectByExample(RouteFilterArgsExample example);

    RouteFilterArgs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RouteFilterArgs record, @Param("example") RouteFilterArgsExample example);

    int updateByExample(@Param("record") RouteFilterArgs record, @Param("example") RouteFilterArgsExample example);

    int updateByPrimaryKeySelective(RouteFilterArgs record);

    int updateByPrimaryKey(RouteFilterArgs record);
}