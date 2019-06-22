package com.weweibuy.gateway.manager.controller;

import com.weweibuy.gateway.common.model.dto.CommonDataJsonResponse;
import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
import com.weweibuy.gateway.manager.controller.constant.ApiPrefixConstant;
import com.weweibuy.gateway.manager.model.dto.RouterPageQueryDto;
import com.weweibuy.gateway.manager.model.po.GatewayRouter;
import com.weweibuy.gateway.manager.model.vo.RouterAddVo;
import com.weweibuy.gateway.manager.model.vo.RouterQueryVo;
import com.weweibuy.gateway.manager.model.vo.RouterUpdateVo;
import com.weweibuy.gateway.manager.service.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author durenhao
 * @date 2019/5/15 23:57
 **/
@RestController
@RequestMapping(value = ApiPrefixConstant.API_PREFIX + "/router", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RouterController {

    @Autowired
    private RouterService routerService;

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    @GetMapping
    public ResponseEntity<CommonDataJsonResponse<RouterPageQueryDto>> getRouters(RouterQueryVo queryVo) {
        return ResponseEntity.ok(CommonDataJsonResponse.success(routerService.getRouters(queryVo)));
    }

    /**
     * id 查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommonDataJsonResponse<GatewayRouter>> getRouter(@PathVariable Long id) {
        return ResponseEntity.ok(CommonDataJsonResponse.success(routerService.getRouterById(id)));
    }

    /**
     * 路由新增
     *
     * @param routerAddVo
     * @return
     */
    @PostMapping
    public ResponseEntity<CommonCodeJsonResponse> addRouter(@RequestBody @Validated RouterAddVo routerAddVo) {
        routerService.addRouter(routerAddVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    /**
     * 路由更新
     *
     * @param routerUpdateVo
     * @return
     */
    @PutMapping
    public ResponseEntity<CommonCodeJsonResponse> updateRouter(@RequestBody @Validated RouterUpdateVo routerUpdateVo) {
        routerService.updateRouter(routerUpdateVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonCodeJsonResponse> deleteRouter(@PathVariable Long id) {
        routerService.deleteRouter(id);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

}
