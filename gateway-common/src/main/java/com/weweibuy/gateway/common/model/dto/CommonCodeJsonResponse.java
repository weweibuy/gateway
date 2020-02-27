package com.weweibuy.gateway.common.model.dto;

import com.weweibuy.gateway.common.model.ResponseCodeAndMsg;
import com.weweibuy.gateway.common.model.eum.CommonResponseEum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author durenhao
 * @date 2019/5/12 22:19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeJsonResponse {

    private String code;

    private String msg;

    public CommonCodeJsonResponse(ResponseCodeAndMsg responseCodeAndMsg) {
        this.code = responseCodeAndMsg.getCode();
        this.msg = responseCodeAndMsg.getMsg();
    }

    public static CommonCodeJsonResponse success() {
        return new CommonCodeJsonResponse(CommonResponseEum.SUCCESS);
    }

    public static CommonCodeJsonResponse unknownException() {
        return new CommonCodeJsonResponse(CommonResponseEum.UNKNOWN_EXCEPTION);
    }

    public static CommonCodeJsonResponse requestLimit() {
        return new CommonCodeJsonResponse(CommonResponseEum.TOO_MANY_REQUESTS);
    }

    public static CommonCodeJsonResponse badRequestParam() {
        return new CommonCodeJsonResponse(CommonResponseEum.BAD_REQUEST_PARAM);
    }

    public static CommonCodeJsonResponse badRequestParam(String msg) {
        return new CommonCodeJsonResponse(CommonResponseEum.BAD_REQUEST_PARAM.getCode(), msg);
    }

    public static CommonCodeJsonResponse badSystemRequestParam() {
        return new CommonCodeJsonResponse(CommonResponseEum.BAD_SYSTEM_REQUEST_PARAM);
    }

    public static CommonCodeJsonResponse unauthorized() {
        return new CommonCodeJsonResponse(CommonResponseEum.UNAUTHORIZED);
    }

    public static CommonCodeJsonResponse forbidden() {
        return new CommonCodeJsonResponse(CommonResponseEum.FORBIDDEN);
    }

    public static CommonCodeJsonResponse response(String code, String msg) {
        return new CommonCodeJsonResponse(code, msg);
    }

    public static CommonCodeJsonResponse response(ResponseCodeAndMsg responseCodeAndMsg) {
        return new CommonCodeJsonResponse(responseCodeAndMsg);
    }


}
