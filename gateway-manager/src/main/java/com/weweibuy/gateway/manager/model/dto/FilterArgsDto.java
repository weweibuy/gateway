package com.weweibuy.gateway.manager.model.dto;

import com.weweibuy.gateway.manager.model.po.FilterArgs;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author durenhao
 * @date 2019/5/18 22:25
 **/
@Data
@Builder
public class FilterArgsDto {

    private Long id;

    private Long filterId;

    private String argsName;

    private String argsValue;

    private Long dictId;

    private String argsDesc;

    public static FilterArgsDto convert(FilterArgs filterArgs){
        FilterArgsDto filterArgsDto = FilterArgsDto.builder().build();
        BeanUtils.copyProperties(filterArgs, filterArgsDto);
        return filterArgsDto;
    }

}
