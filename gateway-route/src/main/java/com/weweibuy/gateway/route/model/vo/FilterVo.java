package com.weweibuy.gateway.route.model.vo;

import com.weweibuy.gateway.route.model.po.RouteFilter;
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

    private Integer priority;

    private Map<String, String> filterArgs;

    public static FilterVo convert(RouteFilter routerFilter, Map<String, String> filterArgs) {
        FilterVo filterVo = new FilterVo();
        BeanUtils.copyProperties(routerFilter, filterVo);
        filterVo.setFilterArgs(filterArgs);
        return filterVo;
    }
}
