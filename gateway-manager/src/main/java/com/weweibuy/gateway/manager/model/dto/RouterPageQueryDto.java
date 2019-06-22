package com.weweibuy.gateway.manager.model.dto;

import com.weweibuy.gateway.manager.model.po.GatewayRouter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 15:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterPageQueryDto {

    private Long total;

    private List<GatewayRouter> routers;

}
