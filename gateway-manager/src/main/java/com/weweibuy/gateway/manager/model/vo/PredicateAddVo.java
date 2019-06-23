package com.weweibuy.gateway.manager.model.vo;

import com.weweibuy.gateway.manager.model.constant.DictConstant;
import com.weweibuy.gateway.manager.model.po.RouterPredicate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 15:04
 **/
@Data
public class PredicateAddVo {

    private static final BeanCopier COPIER = BeanCopier.create(PredicateAddVo.class, RouterPredicate.class, false);

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

    @NotNull(message = "必选选择使用状态")
    private Boolean isUse;

    @Valid
    @NotEmpty(message = "断言参数不能为空")
    private List<PredicateArgsAddVo> predicateArgs;


    public static RouterPredicate convertToPo(PredicateAddVo vo, String predicateId) {
        RouterPredicate routerPredicate = new RouterPredicate();
        COPIER.copy(vo, routerPredicate, null);
        if (StringUtils.isNotBlank(predicateId)) {
            routerPredicate.setPredicateId(predicateId);
        }
        return routerPredicate;
    }
}
