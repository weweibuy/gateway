package com.weweibuy.gateway.route.mapper;

import com.weweibuy.gateway.route.model.example.RouterPredicateArgsExample;
import com.weweibuy.gateway.route.model.po.RouterPredicateArgs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RouterPredicateArgsMapper {
    long countByExample(RouterPredicateArgsExample example);

    int deleteByExample(RouterPredicateArgsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RouterPredicateArgs record);

    int insertSelective(RouterPredicateArgs record);

    RouterPredicateArgs selectOneByExample(RouterPredicateArgsExample example);

    List<RouterPredicateArgs> selectByExample(RouterPredicateArgsExample example);

    RouterPredicateArgs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RouterPredicateArgs record, @Param("example") RouterPredicateArgsExample example);

    int updateByExample(@Param("record") RouterPredicateArgs record, @Param("example") RouterPredicateArgsExample example);

    int updateByPrimaryKeySelective(RouterPredicateArgs record);

    int updateByPrimaryKey(RouterPredicateArgs record);
}