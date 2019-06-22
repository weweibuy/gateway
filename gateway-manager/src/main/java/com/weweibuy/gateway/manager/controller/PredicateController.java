package com.weweibuy.gateway.manager.controller;

import com.weweibuy.gateway.common.model.dto.CommonDataJsonResponse;
import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
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
    public ResponseEntity<CommonDataJsonResponse<RouterPredicate>> getPredicate(@PathVariable Long id) {
        return ResponseEntity.ok(CommonDataJsonResponse.success(predicateService.getPredicateById(id)));
    }

    @GetMapping
    public ResponseEntity<CommonDataJsonResponse<List<RouterPredicate>>> getPredicates(
            @Validated @NotBlank(message = "routerId不能为空") String routerId) {
        return ResponseEntity.ok(CommonDataJsonResponse.success(predicateService.getPredicatesByRouterId(routerId)));
    }

    @PostMapping
    public ResponseEntity<CommonCodeJsonResponse> addPredicate(@RequestBody @Validated PredicateAddVo predicateAddVo) {
        predicateService.addPredicate(predicateAddVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @PutMapping
    public ResponseEntity<CommonCodeJsonResponse> updatePredicate(@RequestBody @Validated PredicateUpdateVo predicateUpdateVo) {
        predicateService.updatePredicate(predicateUpdateVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonCodeJsonResponse> deletePredicateById(Long id) {
        predicateService.deletePredicateById(id);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @DeleteMapping
    public ResponseEntity<CommonCodeJsonResponse> deletePredicatesByRouterId(
            @Validated @NotNull(message = "routerId不能为空") String routerId) {
        predicateService.deletePredicatesByRouterId(routerId);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @GetMapping("/args/{id}")
    public ResponseEntity<CommonDataJsonResponse<PredicateArgs>> getPredicateArgs(@PathVariable Long id) {
        return ResponseEntity.ok(CommonDataJsonResponse.success(predicateArgsService.getPredicateArgsById(id)));
    }

    @GetMapping("/args")
    public ResponseEntity<CommonDataJsonResponse<List<PredicateArgs>>> getPredicateArgsByPredicateId(
            @Validated @NotNull(message = "PredicateId不能为空") Long predicateId) {
        return ResponseEntity
                .ok(CommonDataJsonResponse.success(predicateArgsService.getPredicateArgsByPredicateId(predicateId)));
    }

    @PostMapping("/args")
    public ResponseEntity<CommonCodeJsonResponse> addPredicateArgs(@Validated PredicateArgsAddVo predicateArgsAddVo) {
        predicateArgsService.addPredicateArgs(predicateArgsAddVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @PutMapping("/args")
    public ResponseEntity<CommonCodeJsonResponse> updatePredicateArgs(@Validated PredicateArgsUpdateVo predicateArgsUpdateVo) {
        predicateArgsService.updatePredicateArgs(predicateArgsUpdateVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @DeleteMapping("/args/{id}")
    public ResponseEntity<CommonCodeJsonResponse> deletePredicateArgs(@PathVariable Long id) {
        predicateArgsService.deletePredicateArgsById(id);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @DeleteMapping("/args")
    public ResponseEntity<CommonCodeJsonResponse> deletePredicateArgsByPredicateId(
            @Validated @NotNull(message = "predicateId不能为空") Long predicateId) {
        predicateArgsService.deletePredicateArgsByPredicateId(predicateId);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

}
