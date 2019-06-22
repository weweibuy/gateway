package com.weweibuy.gateway.manager.client.mapper;

import com.weweibuy.gateway.manager.client.model.po.GatewayDataDictionary;
import com.weweibuy.gateway.manager.client.model.po.GatewayDataDictionaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GatewayDataDictionaryMapper {
    long countByExample(GatewayDataDictionaryExample example);

    int deleteByExample(GatewayDataDictionaryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GatewayDataDictionary record);

    int insertSelective(GatewayDataDictionary record);

    GatewayDataDictionary selectOneByExample(GatewayDataDictionaryExample example);

    List<GatewayDataDictionary> selectByExample(GatewayDataDictionaryExample example);

    GatewayDataDictionary selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GatewayDataDictionary record, @Param("example") GatewayDataDictionaryExample example);

    int updateByExample(@Param("record") GatewayDataDictionary record, @Param("example") GatewayDataDictionaryExample example);

    int updateByPrimaryKeySelective(GatewayDataDictionary record);

    int updateByPrimaryKey(GatewayDataDictionary record);
}