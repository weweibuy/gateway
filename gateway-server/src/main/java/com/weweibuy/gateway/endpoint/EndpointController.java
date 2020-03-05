package com.weweibuy.gateway.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author durenhao
 * @date 2020/3/5 22:16
 **/
@RestController
@RequestMapping("/endpoint")
public class EndpointController {

    @GetMapping("/_health")
    public Mono<ResponseEntity> healthCheck() {
        return Mono.just(ResponseEntity.noContent().build());
    }


}
