package com.weweibuy.gateway.manager.mananger.impl;

import com.weweibuy.gateway.common.exception.BusinessException;
import com.weweibuy.gateway.manager.mananger.GatewayDataDictionaryManager;
import com.weweibuy.gateway.manager.mapper.*;
import com.weweibuy.gateway.manager.model.eum.GatewayManagerErrorCode;
import com.weweibuy.gateway.manager.model.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author durenhao
 * @date 2019/5/19 21:29
 **/
@Component
public class GatewayDataDictionaryManagerImpl implements GatewayDataDictionaryManager {

    @Autowired
    private GatewayDataDictionaryMapper dictionaryMapper;

    @Autowired
    private RouterPredicateMapper predicateMapper;

    @Autowired
    private PredicateArgsMapper predicateArgsMapper;

    @Autowired
    private RouterFilterMapper filterMapper;

    @Autowired
    private FilterArgsMapper filterArgsMapper;

    @Override
    public void deleteDictById(Long id) {
        // 存在子节点不能删除
        GatewayDataDictionaryExample example = new GatewayDataDictionaryExample();
        example.createCriteria()
                .andParentIdEqualTo(id);
        GatewayDataDictionary dataDictionary = dictionaryMapper.selectOneByExample(example);
        if (dataDictionary != null) {
            throw new BusinessException(GatewayManagerErrorCode.DICT_CHILD_EXISTED_CANNOT_DELETE);
        }

        RouterPredicateExample predicateExample = new RouterPredicateExample();
        predicateExample.createCriteria()
                .andDictIdEqualTo(id);
        RouterPredicate routerPredicate = predicateMapper.selectOneByExample(predicateExample);

        if(routerPredicate != null){
            throw new BusinessException(GatewayManagerErrorCode.DICT_IN_USED_CANNOT_DELETE);
        }

        PredicateArgsExample predicateArgsExample = new PredicateArgsExample();
        predicateArgsExample.createCriteria()
                .andDictIdEqualTo(id);
        PredicateArgs predicateArgs = predicateArgsMapper.selectOneByExample(predicateArgsExample);
        if(predicateArgs != null){
            throw new BusinessException(GatewayManagerErrorCode.DICT_IN_USED_CANNOT_DELETE);
        }

        RouterFilterExample filterExample = new RouterFilterExample();
        filterExample.createCriteria()
                .andDictIdEqualTo(id);
        RouterFilter routerFilter = filterMapper.selectOneByExample(filterExample);
        if(routerFilter != null){
            throw new BusinessException(GatewayManagerErrorCode.DICT_IN_USED_CANNOT_DELETE);
        }

        FilterArgsExample filterArgsExample = new FilterArgsExample();
        filterArgsExample.createCriteria()
                .andDictIdEqualTo(id);
        FilterArgs filterArgs = filterArgsMapper.selectOneByExample(filterArgsExample);
        if(filterArgs != null){
            throw new BusinessException(GatewayManagerErrorCode.DICT_IN_USED_CANNOT_DELETE);
        }

        // 删除
        dictionaryMapper.deleteByPrimaryKey(id);

    }
}
