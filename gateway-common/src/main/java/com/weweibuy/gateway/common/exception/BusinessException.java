package com.weweibuy.gateway.common.exception;

import com.weweibuy.gateway.common.model.ResponseCodeAndMsg;

/**
 * 业务处理异常
 *
 * @author durenhao
 * @date 2019/5/18 23:30
 **/
public class BusinessException extends RuntimeException {

    private ResponseCodeAndMsg codeAndMsg;

    public BusinessException(ResponseCodeAndMsg codeAndMsg) {
        super(codeAndMsg.getMsg());
        this.codeAndMsg = codeAndMsg;
    }

    public BusinessException(ResponseCodeAndMsg codeAndMsg, Throwable cause) {
        super(codeAndMsg.getMsg(), cause);
        this.codeAndMsg = codeAndMsg;
    }

    public ResponseCodeAndMsg getCodeAndMsg() {
        return codeAndMsg;
    }
}
