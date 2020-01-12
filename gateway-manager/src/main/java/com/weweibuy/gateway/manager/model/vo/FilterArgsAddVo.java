package com.weweibuy.gateway.manager.model.vo;

import com.weweibuy.gateway.common.utils.IdWorker;
import com.weweibuy.gateway.manager.model.po.FilterArgs;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @author durenhao
 * @date 2019/5/19 15:09
 **/
@Data
public class FilterArgsAddVo {

    private static final BeanCopier COPIER = BeanCopier.create(FilterArgsAddVo.class, FilterArgs.class, false);

    private Long filterId;

    private String argsName;

    private String argsValue;

    private Long dictId;

    private String dictType;

    private String argsDesc;

    public static FilterArgs convertToPo(FilterArgsAddVo vo) {
        FilterArgs filterArgs = new FilterArgs();
        COPIER.copy(vo, filterArgs, null);
        filterArgs.setFilterId(IdWorker.nextStringId());
        return filterArgs;
    }

}
