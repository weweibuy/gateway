package com.weweibuy.gateway.controller;

import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.dto.CommonPageResult;
import com.weweibuy.gateway.controller.contant.GwBasePath;
import com.weweibuy.gateway.router.model.dto.FilterRespDTO;
import com.weweibuy.gateway.router.model.dto.PredicateRespDTO;
import com.weweibuy.gateway.router.model.dto.RouterQueryReqDTO;
import com.weweibuy.gateway.router.model.dto.RouterRespDTO;
import com.weweibuy.gateway.router.service.RouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 查询服务
 *
 * @author durenhao
 * @date 2021/8/12 20:54
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping(GwBasePath.PATH)
public class GwQueryController {

    private final RouterService routerService;

    @GetMapping("/router/list")
    public Mono<CommonDataResponse<CommonPageResult<RouterRespDTO>>> routerList(RouterQueryReqDTO queryReqDTO) {
        return Mono.just(CommonDataResponse.success(routerService.routerList(queryReqDTO)));
    }

    @GetMapping("/router/{routerId}")
    public Mono<CommonDataResponse<RouterRespDTO>> router(@PathVariable String routerId) {
        return Mono.just(CommonDataResponse.success(routerService.router(routerId)));
    }

    @GetMapping("/predicate/list")
    public Mono<CommonDataResponse<List<PredicateRespDTO>>> predicateList(String routerId) {
        return Mono.just(CommonDataResponse.success(routerService.predicateList(routerId)));
    }

    @GetMapping("/filter/list")
    public Mono<CommonDataResponse<List<FilterRespDTO>>> filterList(String routerId) {
        return Mono.just(CommonDataResponse.success(routerService.filterList(routerId)));
    }


}
