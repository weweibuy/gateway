package com.weweibuy.gateway.manager.mapper;

import com.weweibuy.gateway.manager.model.po.RouterPredicate;
import com.weweibuy.gateway.manager.model.po.RouterPredicateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RouterPredicateMapper {
    long countByExample(RouterPredicateExample example);

    int deleteByExample(RouterPredicateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RouterPredicate record);

    int insertSelective(RouterPredicate record);

    RouterPredicate selectOneByExample(RouterPredicateExample example);

    List<RouterPredicate> selectByExample(RouterPredicateExample example);

    RouterPredicate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RouterPredicate record, @Param("example") RouterPredicateExample example);

    int updateByExample(@Param("record") RouterPredicate record, @Param("example") RouterPredicateExample example);

    int updateByPrimaryKeySelective(RouterPredicate record);

    int updateByPrimaryKey(RouterPredicate record);
}