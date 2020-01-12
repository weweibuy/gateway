package com.weweibuy.gateway.manager.model.vo;

import com.weweibuy.gateway.common.utils.IdWorker;
import com.weweibuy.gateway.manager.model.po.PredicateArgs;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotBlank;

/**
 * @author durenhao
 * @date 2019/5/19 15:08
 **/
@Data
public class PredicateArgsAddVo {

    private static final BeanCopier COPIER = BeanCopier.create(PredicateArgsAddVo.class, PredicateArgs.class, false);

    @NotBlank(message = "断言参数名称不能为空")
    private String argsName;

    @NotBlank(message = "断言参数值不能为空")
    private String argsValue;

    private Long dictId;

    private String dictType;

    @NotBlank(message = "断言参数描述不能为空")
    private String argsDesc;


    public static PredicateArgs convertToPo(PredicateArgsAddVo vo, String predicateId) {
        PredicateArgs predicateArgs = new PredicateArgs();
        COPIER.copy(vo, predicateArgs, null);
        if (StringUtils.isNotBlank(predicateId)) {
            predicateArgs.setPredicateId(predicateId);
        }
        predicateArgs.setPredicateArgId(IdWorker.nextStringId());
        return predicateArgs;
    }

}
