package com.weweibuy.gateway.manager.service;

import com.weweibuy.gateway.manager.model.po.PredicateArgs;
import com.weweibuy.gateway.manager.model.vo.PredicateArgsAddVo;
import com.weweibuy.gateway.manager.model.vo.PredicateArgsUpdateVo;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 16:29
 **/
public interface PredicateArgsService {

    PredicateArgs getPredicateArgsById(Long id);

    List<PredicateArgs> getPredicateArgsByPredicateId(String predicateId);

    void addPredicateArgs(PredicateArgsAddVo predicateArgsAddVo);

    void updatePredicateArgs(PredicateArgsUpdateVo predicateArgsUpdateVo);

    void deletePredicateArgsById(Long id);

    void deletePredicateArgsByPredicateId(String predicateId);
}
