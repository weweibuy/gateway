package com.weweibuy.gateway.manager.model.dto;

import com.weweibuy.gateway.manager.model.po.AccessSystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 17:54
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessSystemQueryDto {

    private Long total;

    private List<AccessSystem> accessSystems;

}
