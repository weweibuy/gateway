package com.weweibuy.gateway.manager.service.impl;

import com.weweibuy.gateway.common.exception.BusinessException;
import com.weweibuy.gateway.manager.mananger.RouterManager;
import com.weweibuy.gateway.manager.mapper.RouterFilterMapper;
import com.weweibuy.gateway.manager.model.eum.GatewayManagerErrorCode;
import com.weweibuy.gateway.manager.model.po.RouterFilter;
import com.weweibuy.gateway.manager.model.po.RouterFilterExample;
import com.weweibuy.gateway.manager.model.vo.FilterAddVo;
import com.weweibuy.gateway.manager.model.vo.FilterUpdateVo;
import com.weweibuy.gateway.manager.service.FilterService;
import com.weweibuy.gateway.manager.utils.ObjectConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 16:43
 **/
@Service
public class FilterServiceImpl implements FilterService {

    @Autowired
    private RouterFilterMapper filterMapper;

    @Autowired
    private RouterManager routerManager;

    @Override
    public RouterFilter getFilterById(Long id) {
        return filterMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RouterFilter> getFiltersByRouterId(String routerId) {
        RouterFilterExample example = new RouterFilterExample();
        example.createCriteria()
                .andRouterIdEqualTo(routerId);
        return filterMapper.selectByExample(example);
    }

    @Override
    public void addFilter(FilterAddVo filterAddVo) {
        int i = filterMapper.insertSelective(ObjectConvertUtil.convert(filterAddVo, RouterFilter.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.FILTER_ADD_FAIL);
        }
    }

    @Override
    public void updateFilter(FilterUpdateVo filterUpdateVo) {
        int i = filterMapper.updateByPrimaryKeySelective(ObjectConvertUtil.convert(filterUpdateVo, RouterFilter.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.FILTER_NOT_EXISTED);
        }
    }

    @Override
    public void deleteFilterById(Long id) {
        routerManager.deleteFilterById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFiltersByRouterId(String routerId) {
        routerManager.deleteFiltersByRouterId(routerId);
    }
}
