package com.weweibuy.gateway.manager.model.dto;

import com.weweibuy.gateway.manager.model.po.GatewayRouter;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/18 22:18
 **/
@Data
@Builder
public class RouterDto {

    private Long id;

    private String routerId;

    private String systemId;

    private String systemName;

    private String uri;

    private Integer priority;

    private Byte status;

    private List<PredicateDto> predicates;

    private Date createTime;

    private Date updateTime;

    public static RouterDto convert(GatewayRouter router, List<PredicateDto> predicates){
        RouterDto routerDto = RouterDto.builder()
                .predicates(predicates)
                .build();
        BeanUtils.copyProperties(router, routerDto);
        return routerDto;
    }


}
