package com.weweibuy.gateway.router.mapper;

import com.weweibuy.gateway.router.model.example.RouterPredicateArgsExample;
import com.weweibuy.gateway.router.model.po.RouterPredicateArgs;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
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