package com.weweibuy.gateway.manager.client.model.vo;

import com.weweibuy.gateway.manager.client.model.po.RouterFilter;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * @author durenhao
 * @date 2019/5/18 22:24
 **/
@Data
@Builder
public class FilterVo {

    private Long id;

    private Long predicateId;

    private String filterName;

    private Long dictId;

    private String filterDesc;

    private Integer priority;

    private Map<String, String> filterArgs;

    public static FilterVo convert(RouterFilter routerFilter, Map<String, String> filterArgs){
        FilterVo filterDto = FilterVo.builder()
                .filterArgs(filterArgs)
                .build();
        BeanUtils.copyProperties(routerFilter, filterDto);
        return filterDto;
    }
}
