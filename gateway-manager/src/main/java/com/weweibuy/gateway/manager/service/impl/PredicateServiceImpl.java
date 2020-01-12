package com.weweibuy.gateway.manager.service.impl;

import com.weweibuy.gateway.common.exception.BusinessException;
import com.weweibuy.gateway.common.utils.IdWorker;
import com.weweibuy.gateway.manager.mananger.RouterManager;
import com.weweibuy.gateway.manager.mapper.PredicateArgsMapper;
import com.weweibuy.gateway.manager.mapper.RouterPredicateMapper;
import com.weweibuy.gateway.manager.model.eum.GatewayManagerErrorCode;
import com.weweibuy.gateway.manager.model.po.RouterPredicate;
import com.weweibuy.gateway.manager.model.po.RouterPredicateExample;
import com.weweibuy.gateway.manager.model.vo.PredicateAddVo;
import com.weweibuy.gateway.manager.model.vo.PredicateArgsAddVo;
import com.weweibuy.gateway.manager.model.vo.PredicateUpdateVo;
import com.weweibuy.gateway.manager.service.PredicateService;
import com.weweibuy.gateway.manager.utils.ObjectConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 16:16
 **/
@Service
public class PredicateServiceImpl implements PredicateService {

    private final RouterPredicateMapper predicateMapper;

    private final RouterManager routerManager;

    private final PredicateArgsMapper predicateArgsMapper;

    public PredicateServiceImpl(RouterPredicateMapper predicateMapper, RouterManager routerManager, PredicateArgsMapper predicateArgsMapper) {
        this.predicateMapper = predicateMapper;
        this.routerManager = routerManager;
        this.predicateArgsMapper = predicateArgsMapper;
    }

    @Override
    public RouterPredicate getPredicateById(Long id) {
        return predicateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RouterPredicate> getPredicatesByRouterId(String routerId) {
        RouterPredicateExample example = new RouterPredicateExample();
        example.createCriteria()
                .andRouterIdEqualTo(routerId);
        return predicateMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPredicate(PredicateAddVo predicateAddVo) {
        final String id = IdWorker.nextStringId();
        RouterPredicate routerPredicate = PredicateAddVo.convertToPo(predicateAddVo, id);
        List<PredicateArgsAddVo> predicateArgList = predicateAddVo.getPredicateArgs();

        predicateArgList.stream()
                .map(vo -> {
                    return PredicateArgsAddVo.convertToPo(vo, id);
                })
                .forEach(po -> {
                    predicateArgsMapper.insertSelective(po);
                });
        predicateMapper.insertSelective(routerPredicate);

    }

    @Override
    public void updatePredicate(PredicateUpdateVo predicateUpdateVo) {
        int i = predicateMapper.updateByPrimaryKeySelective(ObjectConvertUtil.convert(predicateUpdateVo, RouterPredicate.class));
        if (i == 0) {
            throw new BusinessException(GatewayManagerErrorCode.PREDICATE_NOT_EXISTED);
        }
    }

    @Override
    public void deletePredicateById(Long id) {
        routerManager.deletePredicateById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePredicatesByRouterId(String routerId) {
        routerManager.deletePredicateByRouterId(routerId);
    }
}
