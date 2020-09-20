package com.weweibuy.gateway.route.model.vo;

import com.weweibuy.gateway.route.model.po.GatewayRouter;
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

    private String routerId;

    private String systemId;

    private String systemName;

    private String routerUri;

    private Integer routerPriority;

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
