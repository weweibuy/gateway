package com.weweibuy.gateway.common.model.eum;

import com.weweibuy.gateway.common.model.ResponseCodeAndMsg;

/**
 * @author durenhao
 * @date 2019/5/12 22:27
 **/
public enum CommonResponseEum implements ResponseCodeAndMsg {

    SUCCESS("0", "成功"),
    BAD_REQUEST_PARAM("-400", "请求参数错误"),
    SYSTEM_UNKNOWN_EXCEPTION("999999999", "网关服务未知系统异常"),
    SERVICE_FALL_BACK("999999998", "网关服务暂时不可用"),
    ;

    private String code;

    private String msg;


    CommonResponseEum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
