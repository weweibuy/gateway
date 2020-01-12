package com.weweibuy.gateway.manager.client.model.vo;

import com.weweibuy.gateway.manager.client.model.po.GatewayRouter;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/18 22:18
 **/
@Data
@Builder
public class RouterVo {

    private Long id;

    private String routerId;

    private String systemId;

    private String systemName;

    private String uri;

    private Integer priority;

    private Boolean isUse;

    private List<PredicateVo> predicates;

    private List<FilterVo> filters;

    public static RouterVo convert(GatewayRouter router, List<PredicateVo> predicates,
                                   List<FilterVo> filters){
        RouterVo routerDto = RouterVo.builder()
                .predicates(predicates)
                .filters(filters)
                .build();
        BeanUtils.copyProperties(router, routerDto);
        return routerDto;
    }


}
