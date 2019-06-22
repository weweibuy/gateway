package com.weweibuy.gateway.manager.model.dto;

import com.weweibuy.gateway.manager.model.po.RouterFilter;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/18 22:24
 **/
@Data
@Builder
public class FilterDto {

    private Long id;

    private Long predicateId;

    private String filterName;

    private Long dictId;

    private String filterDesc;

    private Integer priority;

    private List<FilterArgsDto> filterArgs;

    public static FilterDto convert(RouterFilter routerFilter, List<FilterArgsDto> args){
        FilterDto filterDto = FilterDto.builder()
                .filterArgs(args)
                .build();
        BeanUtils.copyProperties(routerFilter, filterDto);
        return filterDto;
    }
}
