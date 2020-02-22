package com.weweibuy.gateway.route.mapper;

import com.weweibuy.gateway.route.model.po.RoutePredicateArgs;
import com.weweibuy.gateway.route.model.po.RoutePredicateArgsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoutePredicateArgsMapper {
    long countByExample(RoutePredicateArgsExample example);

    int deleteByExample(RoutePredicateArgsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoutePredicateArgs record);

    int insertSelective(RoutePredicateArgs record);

    RoutePredicateArgs selectOneByExample(RoutePredicateArgsExample example);

    List<RoutePredicateArgs> selectByExample(RoutePredicateArgsExample example);

    RoutePredicateArgs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoutePredicateArgs record, @Param("example") RoutePredicateArgsExample example);

    int updateByExample(@Param("record") RoutePredicateArgs record, @Param("example") RoutePredicateArgsExample example);

    int updateByPrimaryKeySelective(RoutePredicateArgs record);

    int updateByPrimaryKey(RoutePredicateArgs record);
}