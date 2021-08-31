package com.weweibuy.gateway.router.mapper;

import com.weweibuy.gateway.router.model.example.OpLogConfigExample;
import com.weweibuy.gateway.router.model.po.OpLogConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpLogConfigMapper {
    long countByExample(OpLogConfigExample example);

    int deleteByExample(OpLogConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpLogConfig record);

    int insertSelective(OpLogConfig record);

    OpLogConfig selectOneByExample(OpLogConfigExample example);

    List<OpLogConfig> selectByExample(OpLogConfigExample example);

    OpLogConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpLogConfig record, @Param("example") OpLogConfigExample example);

    int updateByExample(@Param("record") OpLogConfig record, @Param("example") OpLogConfigExample example);

    int updateByPrimaryKeySelective(OpLogConfig record);

    int updateByPrimaryKey(OpLogConfig record);
}