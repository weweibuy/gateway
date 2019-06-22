package com.weweibuy.gateway.manager.controller;

import com.weweibuy.gateway.common.model.dto.CommonBizJsonResponse;
import com.weweibuy.gateway.common.model.dto.CommonJsonResponse;
import com.weweibuy.gateway.manager.controller.constant.ApiPrefixConstant;
import com.weweibuy.gateway.manager.model.dto.AccessSystemQueryDto;
import com.weweibuy.gateway.manager.model.po.AccessSystem;
import com.weweibuy.gateway.manager.model.vo.AccessSystemAddVo;
import com.weweibuy.gateway.manager.model.vo.AccessSystemQueryVo;
import com.weweibuy.gateway.manager.model.vo.AccessSystemUpdateVo;
import com.weweibuy.gateway.manager.service.AccessSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 接入系统
 *
 * @author durenhao
 * @date 2019/5/19 17:46
 **/
@RestController
@RequestMapping(value = ApiPrefixConstant.API_PREFIX + "/access-system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccessSystemController {

    @Autowired
    private AccessSystemService accessSystemService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonBizJsonResponse<AccessSystem>> getAccessSystem(@PathVariable Long id) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(accessSystemService.getSystemById(id)));
    }

    @GetMapping("/router/{routerId}")
    public ResponseEntity<CommonBizJsonResponse<AccessSystem>> getAccessSystemByRouterId(@PathVariable String routerId) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(accessSystemService.getSystemByRouterId(routerId)));
    }

    @GetMapping
    public ResponseEntity<CommonBizJsonResponse<AccessSystemQueryDto>> getAccessSystems(AccessSystemQueryVo queryVo) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(accessSystemService.getAccessSystems(queryVo)));
    }

    @PostMapping
    public ResponseEntity<CommonJsonResponse> addAccessSystem(
            @Validated @RequestBody AccessSystemAddVo accessSystemAddVo) {
        accessSystemService.addAccessSystem(accessSystemAddVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @PutMapping
    public ResponseEntity<CommonJsonResponse> updateAccessSystem(
            @Validated @RequestBody AccessSystemUpdateVo accessSystemUpdateVo) {
        accessSystemService.updateAccessSystem(accessSystemUpdateVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonJsonResponse> deleteAccessSystem(@PathVariable Long id) {
        accessSystemService.deleteAccessSystemById(id);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

}
