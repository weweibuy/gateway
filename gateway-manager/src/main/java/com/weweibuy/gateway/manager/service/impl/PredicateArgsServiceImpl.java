package com.weweibuy.gateway.manager.service.impl;

import com.netflix.discovery.converters.Auto;
import com.weweibuy.gateway.common.exception.BusinessException;
import com.weweibuy.gateway.manager.mapper.PredicateArgsMapper;
import com.weweibuy.gateway.manager.model.eum.GatewayManagerErrorCode;
import com.weweibuy.gateway.manager.model.po.PredicateArgs;
import com.weweibuy.gateway.manager.model.po.PredicateArgsExample;
import com.weweibuy.gateway.manager.model.vo.PredicateArgsAddVo;
import com.weweibuy.gateway.manager.model.vo.PredicateArgsUpdateVo;
import com.weweibuy.gateway.manager.service.PredicateArgsService;
import com.weweibuy.gateway.manager.utils.ObjectConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 16:29
 **/
@Service
public class PredicateArgsServiceImpl implements PredicateArgsService {

    @Auto
    private PredicateArgsMapper predicateArgsMapper;

    @Override
    public PredicateArgs getPredicateArgsById(Long id) {
        return predicateArgsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PredicateArgs> getPredicateArgsByPredicateId(Long predicateId) {
        PredicateArgsExample example = new PredicateArgsExample();
        example.createCriteria()
                .andPredicateIdEqualTo(predicateId);
        return predicateArgsMapper.selectByExample(example);
    }

    @Override
    public void addPredicateArgs(PredicateArgsAddVo predicateArgsAddVo) {
        int i = predicateArgsMapper.insertSelective(ObjectConvertUtil.convert(predicateArgsAddVo, PredicateArgs.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.PREDICATE_ARGS_ADD_FAIL);
        }
    }

    @Override
    public void updatePredicateArgs(PredicateArgsUpdateVo predicateArgsUpdateVo) {
        int i = predicateArgsMapper.updateByPrimaryKeySelective(ObjectConvertUtil.convert(predicateArgsUpdateVo, PredicateArgs.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.PREDICATE_ARGS_NOT_EXISTED);
        }
    }

    @Override
    public void deletePredicateArgsById(Long id) {
        predicateArgsMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePredicateArgsByPredicateId(Long predicateId) {
        PredicateArgsExample example = new PredicateArgsExample();
        example.createCriteria()
                .andPredicateIdEqualTo(predicateId);
        predicateArgsMapper.deleteByExample(example);
    }
}