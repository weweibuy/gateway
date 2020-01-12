package com.weweibuy.gateway.manager.model.vo;

import com.weweibuy.gateway.common.utils.IdWorker;
import com.weweibuy.gateway.manager.model.po.RouterFilter;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 15:06
 **/
@Data
public class FilterAddVo {

    private static final BeanCopier COPIER = BeanCopier.create(FilterAddVo.class, RouterFilter.class, false);

    private Long predicateId;

    private String filterName;

    private String dictType;

    private Long dictId;

    private String filterDesc;

    private Integer priority;

    private List<FilterArgsAddVo> filterArgs;

    public static RouterFilter convertToPo(FilterAddVo vo) {
        RouterFilter routerFilter = new RouterFilter();
        COPIER.copy(vo, routerFilter, null);
        routerFilter.setFilterId(IdWorker.nextStringId());
        return routerFilter;
    }

}
