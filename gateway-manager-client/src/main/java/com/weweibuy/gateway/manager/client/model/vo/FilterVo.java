package com.weweibuy.gateway.manager.client.model.vo;

import com.weweibuy.gateway.manager.client.model.po.RouterFilter;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Map;

/**
 * @author durenhao
 * @date 2019/5/18 22:24
 **/
@Data
public class FilterVo {

    private static final BeanCopier COPIER = BeanCopier.create(RouterFilter.class, FilterVo.class, false);

    private Long id;

    private String routerId;

    private String filterId;

    private String filterName;

    private Integer priority;

    private Boolean isUse;

    private Map<String, String> filterArgs;

    public static FilterVo convert(RouterFilter routerFilter, Map<String, String> filterArgs) {
        FilterVo filterVo = new FilterVo();
        BeanUtils.copyProperties(routerFilter, filterVo);
        filterVo.setFilterArgs(filterArgs);
        return filterVo;
    }
}
