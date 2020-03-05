package com.weweibuy.gateway.core.support;

import lombok.Getter;

/**
 * @author durenhao
 * @date 2020/3/5 15:27
 **/
@Getter
public class ObjectWrapper<T> {

    private T object;

    public ObjectWrapper(T object) {
        this.object = object;
    }
}
