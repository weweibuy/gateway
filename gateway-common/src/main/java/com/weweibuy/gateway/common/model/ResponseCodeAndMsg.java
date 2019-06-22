package com.weweibuy.gateway.common.model;

/**
 * 通用的响应code和 msg
 *
 * @author durenhao
 * @date 2019/5/12 22:25
 **/
public interface ResponseCodeAndMsg {

    /**
     * 获取响应的code
     *
     * @return
     */
    String getCode();

    /**
     * 获取响应的msg
     *
     * @return
     */
    String getMsg();

}
