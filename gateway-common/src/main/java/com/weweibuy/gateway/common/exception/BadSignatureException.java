package com.weweibuy.gateway.common.exception;

/**
 * @author durenhao
 * @date 2020/2/28 23:17
 **/
public class BadSignatureException extends RuntimeException{

    public BadSignatureException(String message) {
        super(message);
    }

}
