package com.weweibuy.gateway.manager.controller.advice;

import com.weweibuy.gateway.common.exception.BusinessException;
import com.weweibuy.gateway.common.exception.SystemException;
import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
import com.weweibuy.gateway.common.model.eum.CommonResponseEum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * rest响应全局异常处理
 *
 * @author durenhao
 * @date 2019/5/19 0:07
 **/
@Slf4j
@RestControllerAdvice
public class ExceptionRestAdvice {


    /**
     * 参数校验异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonCodeJsonResponse> handlerValidException(MethodArgumentNotValidException ex) {
        log.warn("请求参数错误: {}", ex.getMessage());
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder builder = new StringBuilder();
        fieldErrors.forEach(field -> {
            builder.append(field.getDefaultMessage());
            builder.append(";");
        });
        return ResponseEntity.badRequest()
                .body(CommonCodeJsonResponse.response(CommonResponseEum.BAD_REQUEST_PARAM.getCode(), builder.toString()));
    }

    /**
     * 业务处理异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonCodeJsonResponse> handlerBusinessException(BusinessException ex) {
        log.warn("业务处理异常", ex);
        return ResponseEntity.badRequest()
                .body(CommonCodeJsonResponse.response(ex.getCodeAndMsg()));
    }

    /**
     * 系统异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<CommonCodeJsonResponse> handlerSystemException(SystemException ex) {
        log.error("系统异常", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonCodeJsonResponse.response(ex.getCodeAndMsg()));
    }

    /**
     * 未知异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonCodeJsonResponse> handlerException(Exception ex) {
        log.error("未知异常", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonCodeJsonResponse.unknownException());
    }

}
