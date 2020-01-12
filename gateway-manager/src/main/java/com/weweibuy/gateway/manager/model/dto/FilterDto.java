package com.weweibuy.gateway.manager.model.dto;

import com.weweibuy.gateway.manager.model.po.RouterFilter;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/18 22:24
 **/
@Data
@Builder
public class FilterDto {

    private static final BeanCopier COPIER = BeanCopier.create(RouterFilter.class, FilterDto.class, false);

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
        COPIER.copy(routerFilter, filterDto, null);
        return filterDto;
    }
}
