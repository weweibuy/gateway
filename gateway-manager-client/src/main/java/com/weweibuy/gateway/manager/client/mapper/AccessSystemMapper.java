package com.weweibuy.gateway.manager.client.mapper;

import com.weweibuy.gateway.manager.client.model.po.AccessSystem;
import com.weweibuy.gateway.manager.client.model.po.AccessSystemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccessSystemMapper {
    long countByExample(AccessSystemExample example);

    int deleteByExample(AccessSystemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AccessSystem record);

    int insertSelective(AccessSystem record);

    AccessSystem selectOneByExample(AccessSystemExample example);

    List<AccessSystem> selectByExample(AccessSystemExample example);

    AccessSystem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AccessSystem record, @Param("example") AccessSystemExample example);

    int updateByExample(@Param("record") AccessSystem record, @Param("example") AccessSystemExample example);

    int updateByPrimaryKeySelective(AccessSystem record);

    int updateByPrimaryKey(AccessSystem record);
}