package com.weweibuy.gateway.route.mapper;

import com.weweibuy.gateway.route.model.example.RouterFilterExample;
import com.weweibuy.gateway.route.model.po.RouterFilter;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RouterFilterMapper {
    long countByExample(RouterFilterExample example);

    int deleteByExample(RouterFilterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RouterFilter record);

    int insertSelective(RouterFilter record);

    RouterFilter selectOneByExample(RouterFilterExample example);

    List<RouterFilter> selectByExample(RouterFilterExample example);

    RouterFilter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RouterFilter record, @Param("example") RouterFilterExample example);

    int updateByExample(@Param("record") RouterFilter record, @Param("example") RouterFilterExample example);

    int updateByPrimaryKeySelective(RouterFilter record);

    int updateByPrimaryKey(RouterFilter record);
}