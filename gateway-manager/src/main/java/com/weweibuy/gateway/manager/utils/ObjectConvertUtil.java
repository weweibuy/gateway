package com.weweibuy.gateway.manager.utils;

import com.weweibuy.gateway.common.exception.SystemException;
import com.weweibuy.gateway.manager.model.eum.GatewayManagerErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author durenhao
 * @date 2019/5/19 20:22
 **/
@Slf4j
public class ObjectConvertUtil {

    /**
     * 值属性转化
     *
     * @param source 不能为null
     * @param clazz 必须有空参构造
     * @param <T>
     * @return
     */
    public static <T> T convert(Object source, Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
            BeanUtils.copyProperties(source, instance);
            return instance;
        } catch (Exception e) {
            log.error("对象值转化错误: {}", e.getMessage());
            throw new SystemException(GatewayManagerErrorCode.OBJECT_CONVERT_ERROR);
        }
    }

}
