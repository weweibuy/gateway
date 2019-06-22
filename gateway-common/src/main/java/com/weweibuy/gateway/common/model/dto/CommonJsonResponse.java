package com.weweibuy.gateway.common.model.dto;

import com.weweibuy.gateway.common.eum.CommonResponseEum;
import com.weweibuy.gateway.common.model.ResponseCodeAndMsg;
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
public class CommonJsonResponse {

    private String code;

    private String msg;

    public CommonJsonResponse(ResponseCodeAndMsg responseCodeAndMsg) {
        this.code = responseCodeAndMsg.getCode();
        this.msg = responseCodeAndMsg.getMsg();
    }

    public static CommonJsonResponse success(){
        return new CommonJsonResponse(CommonResponseEum.SUCCESS);
    }

    public static CommonJsonResponse unknownException(){
        return new CommonJsonResponse(CommonResponseEum.SYSTEM_UNKNOWN_EXCEPTION);
    }

    public static CommonJsonResponse badRequestParam(){
        return new CommonJsonResponse(CommonResponseEum.BAD_REQUEST_PARAM);
    }

    public static CommonJsonResponse response(String code, String msg){
        return new CommonJsonResponse(code, msg);
    }

    public static CommonJsonResponse response(ResponseCodeAndMsg responseCodeAndMsg){
        return new CommonJsonResponse(responseCodeAndMsg);
    }


}
