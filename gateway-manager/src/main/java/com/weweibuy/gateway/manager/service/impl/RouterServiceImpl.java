package com.weweibuy.gateway.manager.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.weweibuy.gateway.common.exception.BusinessException;
import com.weweibuy.gateway.common.exception.SystemException;
import com.weweibuy.gateway.common.model.eum.CommonResponseEum;
import com.weweibuy.gateway.manager.mananger.RouterManager;
import com.weweibuy.gateway.manager.mapper.GatewayRouterMapper;
import com.weweibuy.gateway.manager.model.dto.RouterPageQueryDto;
import com.weweibuy.gateway.manager.model.eum.GatewayManagerErrorCode;
import com.weweibuy.gateway.manager.model.po.GatewayRouter;
import com.weweibuy.gateway.manager.model.po.GatewayRouterExample;
import com.weweibuy.gateway.manager.model.vo.RouterAddVo;
import com.weweibuy.gateway.manager.model.vo.RouterQueryVo;
import com.weweibuy.gateway.manager.model.vo.RouterUpdateVo;
import com.weweibuy.gateway.manager.service.RouterService;
import com.weweibuy.gateway.manager.utils.ObjectConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/16 0:05
 **/
@Service
public class RouterServiceImpl implements RouterService {

    @Autowired
    private GatewayRouterMapper routerMapper;

    @Autowired
    private RouterManager routerManager;

    @Override
    public GatewayRouter getRouterById(Long id) {
        return routerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addRouter(RouterAddVo routerAddVo) {
        try {
            routerMapper.insertSelective(ObjectConvertUtil.convert(routerAddVo, GatewayRouter.class));
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new BusinessException(GatewayManagerErrorCode.ROUTER_EXISTED, e);
            }
            throw new SystemException(CommonResponseEum.SYSTEM_UNKNOWN_EXCEPTION, e);
        }
    }

    @Override
    public void updateRouter(RouterUpdateVo routerUpdateVo) {
        int i = routerMapper.updateByPrimaryKeySelective(ObjectConvertUtil.convert(routerUpdateVo, GatewayRouter.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.ROUTER_NOT_EXISTED);
        }
    }

    @Override
    public RouterPageQueryDto getRouters(RouterQueryVo queryVo) {
        Page<Object> pageHelper = PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        GatewayRouterExample example = queryVo.convertExample();
        List<GatewayRouter> gatewayRouterList = routerMapper.selectByExample(example);
        return new RouterPageQueryDto(pageHelper.getTotal(), gatewayRouterList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRouter(Long id) {
        routerManager.deleteRouter(id);
    }
}
