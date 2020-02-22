package com.weweibuy.gateway.route.mapper;

import com.weweibuy.gateway.route.model.po.RouteFilter;
import com.weweibuy.gateway.route.model.po.RouteFilterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RouteFilterMapper {
    long countByExample(RouteFilterExample example);

    int deleteByExample(RouteFilterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RouteFilter record);

    int insertSelective(RouteFilter record);

    RouteFilter selectOneByExample(RouteFilterExample example);

    List<RouteFilter> selectByExample(RouteFilterExample example);

    RouteFilter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RouteFilter record, @Param("example") RouteFilterExample example);

    int updateByExample(@Param("record") RouteFilter record, @Param("example") RouteFilterExample example);

    int updateByPrimaryKeySelective(RouteFilter record);

    int updateByPrimaryKey(RouteFilter record);
}