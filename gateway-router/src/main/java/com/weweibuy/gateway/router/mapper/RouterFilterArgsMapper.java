package com.weweibuy.gateway.router.mapper;

import com.weweibuy.gateway.router.model.example.RouterFilterArgsExample;
import com.weweibuy.gateway.router.model.po.RouterFilterArgs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RouterFilterArgsMapper {
    long countByExample(RouterFilterArgsExample example);

    int deleteByExample(RouterFilterArgsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RouterFilterArgs record);

    int insertSelective(RouterFilterArgs record);

    RouterFilterArgs selectOneByExample(RouterFilterArgsExample example);

    List<RouterFilterArgs> selectByExample(RouterFilterArgsExample example);

    RouterFilterArgs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RouterFilterArgs record, @Param("example") RouterFilterArgsExample example);

    int updateByExample(@Param("record") RouterFilterArgs record, @Param("example") RouterFilterArgsExample example);

    int updateByPrimaryKeySelective(RouterFilterArgs record);

    int updateByPrimaryKey(RouterFilterArgs record);
}