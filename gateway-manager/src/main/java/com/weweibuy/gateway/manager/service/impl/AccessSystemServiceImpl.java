package com.weweibuy.gateway.manager.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.weweibuy.gateway.common.exception.BusinessException;
import com.weweibuy.gateway.manager.mapper.AccessSystemMapper;
import com.weweibuy.gateway.manager.mapper.GatewayRouterMapper;
import com.weweibuy.gateway.manager.model.dto.AccessSystemQueryDto;
import com.weweibuy.gateway.manager.model.eum.GatewayManagerErrorCode;
import com.weweibuy.gateway.manager.model.po.AccessSystem;
import com.weweibuy.gateway.manager.model.po.AccessSystemExample;
import com.weweibuy.gateway.manager.model.po.GatewayRouter;
import com.weweibuy.gateway.manager.model.po.GatewayRouterExample;
import com.weweibuy.gateway.manager.model.vo.AccessSystemAddVo;
import com.weweibuy.gateway.manager.model.vo.AccessSystemQueryVo;
import com.weweibuy.gateway.manager.model.vo.AccessSystemUpdateVo;
import com.weweibuy.gateway.manager.service.AccessSystemService;
import com.weweibuy.gateway.manager.utils.ObjectConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 17:47
 **/
@Service
public class AccessSystemServiceImpl implements AccessSystemService {

    @Autowired
    private AccessSystemMapper accessSystemMapper;

    @Autowired
    private GatewayRouterMapper routerMapper;

    @Override
    public AccessSystem getSystemById(Long id) {
        return accessSystemMapper.selectByPrimaryKey(id);
    }

    @Override
    public AccessSystem getSystemByRouterId(String routerId) {
        GatewayRouterExample example = new GatewayRouterExample();
        example.createCriteria()
                .andSystemIdEqualTo(routerId);
        GatewayRouter gatewayRouter = routerMapper.selectOneByExample(example);
        if (gatewayRouter == null) {
            throw new BusinessException(GatewayManagerErrorCode.ROUTER_NOT_EXISTED);
        }
        AccessSystemExample systemExample = new AccessSystemExample();
        systemExample.createCriteria()
                .andSystemIdEqualTo(gatewayRouter.getSystemId());
        return accessSystemMapper.selectOneByExample(systemExample);
    }

    @Override
    public AccessSystemQueryDto getAccessSystems(AccessSystemQueryVo queryVo) {
        AccessSystemExample systemExample = queryVo.buildExample();
        Page<Object> objectPage = PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        List<AccessSystem> accessSystems = accessSystemMapper.selectByExample(systemExample);
        return new AccessSystemQueryDto(objectPage.getTotal(), accessSystems);
    }

    @Override
    public void addAccessSystem(AccessSystemAddVo accessSystemAddVo) {
        int i = accessSystemMapper.insertSelective(ObjectConvertUtil.convert(accessSystemAddVo, AccessSystem.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.ACCESS_SYSTEM_ADD_FAIL);
        }

    }

    @Override
    public void updateAccessSystem(AccessSystemUpdateVo accessSystemUpdateVo) {
        int i = accessSystemMapper.updateByPrimaryKeySelective(ObjectConvertUtil.convert(accessSystemUpdateVo, AccessSystem.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.ACCESS_SYSTEM_NOT_EXISTED);
        }
    }

    @Override
    public void deleteAccessSystemById(Long id) {
        AccessSystem accessSystem = accessSystemMapper.selectByPrimaryKey(id);
        if (accessSystem != null) {
            GatewayRouterExample example = new GatewayRouterExample();
            example.createCriteria()
                    .andSystemIdEqualTo(accessSystem.getSystemId());
            GatewayRouter gatewayRouter = routerMapper.selectOneByExample(example);
            if (gatewayRouter != null) {
                throw new BusinessException(GatewayManagerErrorCode.ROUTER_EXISTED_CANNOT_DELETE_ACCESS_SYSTEM);
            }
            accessSystemMapper.deleteByPrimaryKey(id);
        }
    }
}
