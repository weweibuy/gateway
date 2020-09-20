package com.weweibuy.gateway.router.model.vo;

import com.weweibuy.gateway.router.model.po.RouterFilter;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * @author durenhao
 * @date 2019/5/18 22:24
 **/
@Data
public class FilterVo {

    private String routerId;

    private String filterId;

    private String filterName;

    private Integer filterPriority;

    private Map<String, String> filterArgs;

    public static FilterVo convert(RouterFilter routerFilter, Map<String, String> filterArgs) {
        FilterVo filterVo = new FilterVo();
        BeanUtils.copyProperties(routerFilter, filterVo);
        filterVo.setFilterArgs(filterArgs);
        return filterVo;
    }
}
