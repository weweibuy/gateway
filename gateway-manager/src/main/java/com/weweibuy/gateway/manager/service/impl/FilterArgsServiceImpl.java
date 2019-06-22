package com.weweibuy.gateway.manager.service.impl;

import com.weweibuy.gateway.common.exception.BusinessException;
import com.weweibuy.gateway.manager.mapper.FilterArgsMapper;
import com.weweibuy.gateway.manager.model.eum.GatewayManagerErrorCode;
import com.weweibuy.gateway.manager.model.po.FilterArgs;
import com.weweibuy.gateway.manager.model.po.FilterArgsExample;
import com.weweibuy.gateway.manager.model.vo.FilterArgsAddVo;
import com.weweibuy.gateway.manager.model.vo.FilterArgsUpdateVo;
import com.weweibuy.gateway.manager.service.FilterArgsService;
import com.weweibuy.gateway.manager.utils.ObjectConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 16:44
 **/
@Service
public class FilterArgsServiceImpl implements FilterArgsService {

    @Autowired
    private FilterArgsMapper filterArgsMapper;

    @Override
    public FilterArgs getFilterArgsById(Long id) {
        return filterArgsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<FilterArgs> getFilterArgsByFilterId(Long filterId) {
        FilterArgsExample example = new FilterArgsExample();
        example.createCriteria()
                .andFilterIdEqualTo(filterId);
        return filterArgsMapper.selectByExample(example);
    }

    @Override
    public void addFilterArgs(FilterArgsAddVo filterArgsAddVo) {
        int i = filterArgsMapper.insertSelective(ObjectConvertUtil.convert(filterArgsAddVo, FilterArgs.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.FILTER_ARGS_ADD_FAIL);
        }
    }

    @Override
    public void updateFilterArgs(FilterArgsUpdateVo filterArgsUpdateVo) {
        int i = filterArgsMapper.updateByPrimaryKeySelective(ObjectConvertUtil.convert(filterArgsUpdateVo, FilterArgs.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.FILTER_ARGS_NOT_EXISTED);
        }
    }

    @Override
    public void deleteFilterArgsById(Long id) {
        filterArgsMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFilterArgsByFilterId(Long filterId) {
        FilterArgsExample example = new FilterArgsExample();
        example.createCriteria()
                .andFilterIdEqualTo(filterId);
        filterArgsMapper.deleteByExample(example);
    }
}
