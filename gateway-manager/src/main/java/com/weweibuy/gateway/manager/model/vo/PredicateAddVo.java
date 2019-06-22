package com.weweibuy.gateway.manager.model.vo;

import com.weweibuy.gateway.manager.model.constant.DictConstant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author durenhao
 * @date 2019/5/19 15:04
 **/
@Data
public class PredicateAddVo {

    @NotBlank(message = "routerId不能为空")
    @Length(max = 63, message = "routerId长度不能超过63位")
    private String routerId;

    @NotBlank(message = "断言Name不能为空")
    @Length(max = 63, message = "断言Name长度不能超过63位")
    private String predicateName;

    private Long dictId;

    private String dictType = DictConstant.ROUTER_PREDICATE_TYPE;

    @NotBlank(message = "断言描述不能为空")
    @Length(max = 255, message = "断言描述长度不能超过255位")
    private String predicateDesc;


}
