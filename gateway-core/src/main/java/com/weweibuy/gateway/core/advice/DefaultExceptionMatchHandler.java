package com.weweibuy.gateway.core.advice;

import com.weweibuy.framework.common.core.exception.BusinessException;
import com.weweibuy.framework.common.core.exception.SystemException;
import com.weweibuy.framework.common.core.model.dto.CommonCodeResponse;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.core.utils.MediaTypeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author durenhao
 * @date 2020/2/23 19:15
 **/
public class DefaultExceptionMatchHandler implements ExceptionMatchHandler {

    @Override
    public boolean match(ServerWebExchange exchange, Throwable ex) {
        return true;
    }

    @Override
    public Mono<ServerResponse> handler(ServerWebExchange exchange, Throwable ex) {
        if (MediaTypeUtils.acceptsHtml(exchange)) {
            return htmlErrorResponse(ex);
        }
        if (ex instanceof ResponseStatusException) {

            ResponseStatusException statusException = (ResponseStatusException) ex;
            HttpStatus status = statusException.getStatus();
            return toServerResponse(status,
                    CommonCodeResponse.response(status.value() + "", ex.getMessage()));
        }
        return toServerResponse(ex);
    }


    private Mono<ServerResponse> htmlErrorResponse(Throwable ex) {
        // TODO 字符集问题
        return ReactorHttpHelper.buildResponse(HttpStatus.TOO_MANY_REQUESTS, MediaType.TEXT_PLAIN, "服务暂时不可用: " + ex.getClass().getSimpleName());
    }


    public Mono<ServerResponse> toServerResponse(Throwable t) {
        if (null == t) {
            return toServerResponse(HttpStatus.INTERNAL_SERVER_ERROR, CommonCodeResponse.unknownException());
        }

        int counter = 0;
        Throwable cause = t;
        while (cause != null && counter++ < 50) {
            if (cause instanceof BusinessException) {
                return toServerResponse(HttpStatus.BAD_REQUEST,
                        CommonCodeResponse.response(((BusinessException) cause).getCodeAndMsg()));
            }

            if (cause instanceof SystemException) {
                return toServerResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        CommonCodeResponse.response(((SystemException) cause).getCodeAndMsg()));
            }
            cause = cause.getCause();
        }
        return toServerResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                CommonCodeResponse.unknownException());
    }

    private Mono<ServerResponse> toServerResponse(HttpStatus httpStatus, Object body) {
        return ReactorHttpHelper.buildResponse(httpStatus, MediaType.APPLICATION_JSON, body);
    }


}
