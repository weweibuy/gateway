package com.weweibuy.gateway.route.mapper;

import com.weweibuy.gateway.route.model.po.RoutePredicate;
import com.weweibuy.gateway.route.model.po.RoutePredicateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoutePredicateMapper {
    long countByExample(RoutePredicateExample example);

    int deleteByExample(RoutePredicateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoutePredicate record);

    int insertSelective(RoutePredicate record);

    RoutePredicate selectOneByExample(RoutePredicateExample example);

    List<RoutePredicate> selectByExample(RoutePredicateExample example);

    RoutePredicate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoutePredicate record, @Param("example") RoutePredicateExample example);

    int updateByExample(@Param("record") RoutePredicate record, @Param("example") RoutePredicateExample example);

    int updateByPrimaryKeySelective(RoutePredicate record);

    int updateByPrimaryKey(RoutePredicate record);
}