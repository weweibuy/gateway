package com.weweibuy.gateway.manager.controller;

import com.weweibuy.gateway.common.model.dto.CommonBizJsonResponse;
import com.weweibuy.gateway.common.model.dto.CommonJsonResponse;
import com.weweibuy.gateway.manager.controller.constant.ApiPrefixConstant;
import com.weweibuy.gateway.manager.model.po.PredicateArgs;
import com.weweibuy.gateway.manager.model.po.RouterPredicate;
import com.weweibuy.gateway.manager.model.vo.PredicateAddVo;
import com.weweibuy.gateway.manager.model.vo.PredicateArgsAddVo;
import com.weweibuy.gateway.manager.model.vo.PredicateArgsUpdateVo;
import com.weweibuy.gateway.manager.model.vo.PredicateUpdateVo;
import com.weweibuy.gateway.manager.service.PredicateArgsService;
import com.weweibuy.gateway.manager.service.PredicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 14:59
 **/
@RestController
@RequestMapping(value = ApiPrefixConstant.API_PREFIX + "/predicate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PredicateController {

    @Autowired
    private PredicateService predicateService;

    @Autowired
    private PredicateArgsService predicateArgsService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonBizJsonResponse<RouterPredicate>> getPredicate(@PathVariable Long id) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(predicateService.getPredicateById(id)));
    }

    @GetMapping
    public ResponseEntity<CommonBizJsonResponse<List<RouterPredicate>>> getPredicates(
            @Validated @NotBlank(message = "routerId不能为空") String routerId) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(predicateService.getPredicatesByRouterId(routerId)));
    }

    @PostMapping
    public ResponseEntity<CommonJsonResponse> addPredicate(@RequestBody @Validated PredicateAddVo predicateAddVo) {
        predicateService.addPredicate(predicateAddVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }


    @PutMapping
    public ResponseEntity<CommonJsonResponse> updatePredicate(@RequestBody @Validated PredicateUpdateVo predicateUpdateVo) {
        predicateService.updatePredicate(predicateUpdateVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonJsonResponse> deletePredicateById(Long id) {
        predicateService.deletePredicateById(id);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping
    public ResponseEntity<CommonJsonResponse> deletePredicatesByRouterId(
            @Validated @NotNull(message = "routerId不能为空") String routerId) {
        predicateService.deletePredicatesByRouterId(routerId);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @GetMapping("/args/{id}")
    public ResponseEntity<CommonBizJsonResponse<PredicateArgs>> getPredicateArgs(@PathVariable Long id) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(predicateArgsService.getPredicateArgsById(id)));
    }

    @GetMapping("/args")
    public ResponseEntity<CommonBizJsonResponse<List<PredicateArgs>>> getPredicateArgsByPredicateId(
            @Validated @NotNull(message = "PredicateId不能为空") Long predicateId) {
        return ResponseEntity
                .ok(CommonBizJsonResponse.success(predicateArgsService.getPredicateArgsByPredicateId(predicateId)));
    }

    @PostMapping("/args")
    public ResponseEntity<CommonJsonResponse> addPredicateArgs(@Validated PredicateArgsAddVo predicateArgsAddVo) {
        predicateArgsService.addPredicateArgs(predicateArgsAddVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @PutMapping("/args")
    public ResponseEntity<CommonJsonResponse> updatePredicateArgs(@Validated PredicateArgsUpdateVo predicateArgsUpdateVo) {
        predicateArgsService.updatePredicateArgs(predicateArgsUpdateVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping("/args/{id}")
    public ResponseEntity<CommonJsonResponse> deletePredicateArgs(@PathVariable Long id) {
        predicateArgsService.deletePredicateArgsById(id);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping("/args")
    public ResponseEntity<CommonJsonResponse> deletePredicateArgsByPredicateId(
            @Validated @NotNull(message = "predicateId不能为空") Long predicateId) {
        predicateArgsService.deletePredicateArgsByPredicateId(predicateId);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

}
