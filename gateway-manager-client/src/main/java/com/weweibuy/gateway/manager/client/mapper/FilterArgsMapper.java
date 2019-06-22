package com.weweibuy.gateway.manager.client.mapper;

import com.weweibuy.gateway.manager.client.model.po.FilterArgs;
import com.weweibuy.gateway.manager.client.model.po.FilterArgsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FilterArgsMapper {
    long countByExample(FilterArgsExample example);

    int deleteByExample(FilterArgsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FilterArgs record);

    int insertSelective(FilterArgs record);

    FilterArgs selectOneByExample(FilterArgsExample example);

    List<FilterArgs> selectByExample(FilterArgsExample example);

    FilterArgs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FilterArgs record, @Param("example") FilterArgsExample example);

    int updateByExample(@Param("record") FilterArgs record, @Param("example") FilterArgsExample example);

    int updateByPrimaryKeySelective(FilterArgs record);

    int updateByPrimaryKey(FilterArgs record);
}