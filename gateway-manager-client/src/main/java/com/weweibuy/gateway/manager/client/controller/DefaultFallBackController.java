package com.weweibuy.gateway.manager.client.controller;

import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 默认降级地址
 *
 * @author durenhao
 * @date 2019/7/7 12:19
 **/
@RestController
public class DefaultFallBackController {

    @RequestMapping("/_default-fallback")
    public Mono<ResponseEntity<CommonCodeJsonResponse>> fallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(CommonCodeJsonResponse.fallback()));
    }

}
