package com.weweibuy.gateway.manager.service;

import com.weweibuy.gateway.manager.model.po.RouterFilter;
import com.weweibuy.gateway.manager.model.vo.FilterAddVo;
import com.weweibuy.gateway.manager.model.vo.FilterUpdateVo;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 16:43
 **/
public interface FilterService {

    RouterFilter getFilterById(Long id);

    List<RouterFilter> getFiltersByRouterId(String routerId);

    void addFilter(FilterAddVo filterAddVo);

    void updateFilter(FilterUpdateVo filterUpdateVo);

    void deleteFilterById(Long id);

    void deleteFiltersByRouterId(String routerId);

}
