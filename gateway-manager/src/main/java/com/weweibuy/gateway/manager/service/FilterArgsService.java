package com.weweibuy.gateway.manager.service;

import com.weweibuy.gateway.manager.model.po.FilterArgs;
import com.weweibuy.gateway.manager.model.vo.FilterArgsAddVo;
import com.weweibuy.gateway.manager.model.vo.FilterArgsUpdateVo;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 16:44
 **/
public interface FilterArgsService {

    FilterArgs getFilterArgsById(Long id);

    List<FilterArgs> getFilterArgsByFilterId(String filterId);

    void addFilterArgs(FilterArgsAddVo filterArgsAddVo);

    void updateFilterArgs(FilterArgsUpdateVo filterArgsUpdateVo);

    void deleteFilterArgsById(Long id);

    void deleteFilterArgsByFilterId(String filterId);

}
