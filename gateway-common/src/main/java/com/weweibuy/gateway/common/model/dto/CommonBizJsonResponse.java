package com.weweibuy.gateway.common.model.dto;

import com.weweibuy.gateway.common.eum.CommonResponseEum;
import com.weweibuy.gateway.common.model.ResponseCodeAndMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author durenhao
 * @date 2019/5/12 22:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonBizJsonResponse<T> extends CommonJsonResponse {

    private T data;

    public CommonBizJsonResponse(ResponseCodeAndMsg codeAndMsg, T data) {
        super(codeAndMsg);
        this.data = data;
    }

    public static <T> CommonBizJsonResponse success(T data) {
        return new CommonBizJsonResponse(CommonResponseEum.SUCCESS, data);
    }

    public static <T> CommonBizJsonResponse response(ResponseCodeAndMsg codeAndMsg, T data) {
        return new CommonBizJsonResponse(codeAndMsg, data);
    }
}
