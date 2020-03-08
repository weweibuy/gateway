package com.weweibuy.gateway.common.validator;


import com.weweibuy.webuy.common.model.eum.ValueEum;

/**
 * 枚举值验证器
 *
 * @author durenhao
 * @date 2019/6/22 20:58
 **/
public class EumValueValidator {

    public static boolean validatorInOneOfValue(String value, ValueEum... valueEums) {
        for (ValueEum valueEum : valueEums) {
            if (valueEum.getValue().equals(value)) {
                return true;
            }
        }
        return false;

    }


}
