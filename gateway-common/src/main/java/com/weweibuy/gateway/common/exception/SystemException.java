package com.weweibuy.gateway.common.exception;

import com.weweibuy.gateway.common.model.ResponseCodeAndMsg;

/**
 * 系统级别异常
 *
 * @author durenhao
 * @date 2019/5/18 23:34
 **/
public class SystemException extends RuntimeException {

    private ResponseCodeAndMsg codeAndMsg;

    public SystemException(ResponseCodeAndMsg codeAndMsg) {
        super(codeAndMsg.getMsg());
        this.codeAndMsg = codeAndMsg;
    }

    public SystemException(ResponseCodeAndMsg codeAndMsg, Throwable cause) {
        super(codeAndMsg.getMsg(), cause);
        this.codeAndMsg = codeAndMsg;
    }

    public ResponseCodeAndMsg getCodeAndMsg() {
        return codeAndMsg;
    }
}

