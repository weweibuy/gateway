package com.weweibuy.gateway.manager.service;

import com.weweibuy.gateway.manager.model.po.RouterPredicate;
import com.weweibuy.gateway.manager.model.vo.PredicateAddVo;
import com.weweibuy.gateway.manager.model.vo.PredicateUpdateVo;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 16:16
 **/
public interface PredicateService {

    RouterPredicate getPredicateById(Long id);

    List<RouterPredicate> getPredicatesByRouterId(String routerId);

    void addPredicate(PredicateAddVo predicateAddVo);

    void updatePredicate(PredicateUpdateVo predicateUpdateVo);

    void deletePredicateById(Long id);

    void deletePredicatesByRouterId(String routerId);

}
