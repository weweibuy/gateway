package com.weweibuy.gateway.core.exception;

/**
 * @author durenhao
 * @date 2021/2/4 21:34
 **/
public class ForbiddenException extends RuntimeException {


    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

}
