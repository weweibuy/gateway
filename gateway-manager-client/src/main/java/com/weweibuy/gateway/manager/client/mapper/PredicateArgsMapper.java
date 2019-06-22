package com.weweibuy.gateway.manager.client.mapper;

import com.weweibuy.gateway.manager.client.model.po.PredicateArgs;
import com.weweibuy.gateway.manager.client.model.po.PredicateArgsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PredicateArgsMapper {
    long countByExample(PredicateArgsExample example);

    int deleteByExample(PredicateArgsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PredicateArgs record);

    int insertSelective(PredicateArgs record);

    PredicateArgs selectOneByExample(PredicateArgsExample example);

    List<PredicateArgs> selectByExample(PredicateArgsExample example);

    PredicateArgs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PredicateArgs record, @Param("example") PredicateArgsExample example);

    int updateByExample(@Param("record") PredicateArgs record, @Param("example") PredicateArgsExample example);

    int updateByPrimaryKeySelective(PredicateArgs record);

    int updateByPrimaryKey(PredicateArgs record);
}