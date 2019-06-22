package com.weweibuy.gateway.manager.service.impl;

import com.weweibuy.gateway.common.exception.BusinessException;
import com.weweibuy.gateway.manager.mananger.GatewayDataDictionaryManager;
import com.weweibuy.gateway.manager.mapper.GatewayDataDictionaryMapper;
import com.weweibuy.gateway.manager.model.constant.DictConstant;
import com.weweibuy.gateway.manager.model.eum.GatewayManagerErrorCode;
import com.weweibuy.gateway.manager.model.po.GatewayDataDictionary;
import com.weweibuy.gateway.manager.model.po.GatewayDataDictionaryExample;
import com.weweibuy.gateway.manager.model.vo.DataDictAddVo;
import com.weweibuy.gateway.manager.model.vo.DataDictUpdateVo;
import com.weweibuy.gateway.manager.service.DataDictService;
import com.weweibuy.gateway.manager.utils.ObjectConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 18:15
 **/
@Service
public class DataDictServiceImpl implements DataDictService {

    @Autowired
    private GatewayDataDictionaryMapper dictionaryMapper;

    @Autowired
    private GatewayDataDictionaryManager dictionaryManager;

    @Override
    public GatewayDataDictionary getDictById(Long id) {
        return dictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<GatewayDataDictionary> getChildDictByPid(Long id) {
        GatewayDataDictionaryExample example = new GatewayDataDictionaryExample();
        example.createCriteria()
                .andParentIdEqualTo(id);
        return dictionaryMapper.selectByExample(example);
    }

    @Override
    public List<GatewayDataDictionary> getDictByType(String dictType) {
        GatewayDataDictionaryExample example = new GatewayDataDictionaryExample();
        example.createCriteria()
                .andDictTypeEqualTo(dictType);
        return dictionaryMapper.selectByExample(example);
    }

    @Override
    public void addDict(DataDictAddVo dataDictAddVo) {
        int i = dictionaryMapper.insertSelective(ObjectConvertUtil.convert(dataDictAddVo, GatewayDataDictionary.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.DICT_ADD_FAIL);
        }
    }

    @Override
    public void updateDict(DataDictUpdateVo dataDictUpdateVo) {
        int i = dictionaryMapper.updateByPrimaryKeySelective(ObjectConvertUtil.convert(dataDictUpdateVo, GatewayDataDictionary.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.DICT_NOT_EXISTED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictById(Long id) {
        dictionaryManager.deleteDictById(id);
    }

    @Override
    public List<GatewayDataDictionary> getFilters() {
        return getChildDictByType(DictConstant.ROUTER_FILTER_TYPE);
    }

    @Override
    public List<GatewayDataDictionary> getPredicates() {
        return getChildDictByType(DictConstant.ROUTER_PREDICATE_TYPE);
    }

    private List getChildDictByType(String type) {
        GatewayDataDictionaryExample example = new GatewayDataDictionaryExample();
        GatewayDataDictionaryExample.Criteria criteria = example.createCriteria()
                .andDictTypeEqualTo(type);
        GatewayDataDictionary dataDictionary = dictionaryMapper.selectOneByExample(example);
        if (dataDictionary == null) {
            return Collections.emptyList();
        }
        example.clear();
        example.createCriteria()
                .andParentIdEqualTo(dataDictionary.getId());
        return dictionaryMapper.selectByExample(example);
    }



}
