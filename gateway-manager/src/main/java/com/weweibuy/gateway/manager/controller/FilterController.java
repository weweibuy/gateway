package com.weweibuy.gateway.manager.controller;

import com.weweibuy.gateway.common.model.dto.CommonBizJsonResponse;
import com.weweibuy.gateway.common.model.dto.CommonJsonResponse;
import com.weweibuy.gateway.manager.controller.constant.ApiPrefixConstant;
import com.weweibuy.gateway.manager.model.po.FilterArgs;
import com.weweibuy.gateway.manager.model.po.RouterFilter;
import com.weweibuy.gateway.manager.model.vo.FilterAddVo;
import com.weweibuy.gateway.manager.model.vo.FilterArgsAddVo;
import com.weweibuy.gateway.manager.model.vo.FilterArgsUpdateVo;
import com.weweibuy.gateway.manager.model.vo.FilterUpdateVo;
import com.weweibuy.gateway.manager.service.FilterArgsService;
import com.weweibuy.gateway.manager.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 14:59
 **/
@RestController
@RequestMapping(value = ApiPrefixConstant.API_PREFIX + "/filter", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FilterController {

    @Autowired
    private FilterService filterService;

    @Autowired
    private FilterArgsService filterArgsService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonBizJsonResponse<RouterFilter>> getFilter(@PathVariable Long id) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(filterService.getFilterById(id)));
    }

    @GetMapping
    public ResponseEntity<CommonBizJsonResponse<List<RouterFilter>>> getFilters(
            @Validated @NotNull(message = "routerId不能为空") String routerId) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(filterService.getFiltersByRouterId(routerId)));
    }

    @PostMapping
    public ResponseEntity<CommonJsonResponse> addFilter(@RequestBody @Validated FilterAddVo filterAddVo) {
        filterService.addFilter(filterAddVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @PutMapping
    public ResponseEntity<CommonJsonResponse> updateFilter(@RequestBody @Validated FilterUpdateVo filterUpdateVo) {
        filterService.updateFilter(filterUpdateVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonJsonResponse> deleteFilterById(Long id) {
        filterService.deleteFilterById(id);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping
    public ResponseEntity<CommonJsonResponse> deleteFiltersByRouterId(
            @Validated @NotNull(message = "predicateId不能为空") String routerId) {
        filterService.deleteFiltersByRouterId(routerId);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }


    @GetMapping("/args/{id}")
    public ResponseEntity<CommonBizJsonResponse<FilterArgs>> getFilterArgs(@PathVariable Long id) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(filterArgsService.getFilterArgsById(id)));
    }

    @GetMapping("/args")
    public ResponseEntity<CommonBizJsonResponse<List<FilterArgs>>> getFilterArgsByFilterId(
            @Validated @NotNull(message = "filterId不能为空") Long filterId) {
        return ResponseEntity
                .ok(CommonBizJsonResponse.success(filterArgsService.getFilterArgsByFilterId(filterId)));
    }

    @PostMapping("/args")
    public ResponseEntity<CommonJsonResponse> addFilterArgs(@Validated FilterArgsAddVo filterArgsAddVo) {
        filterArgsService.addFilterArgs(filterArgsAddVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @PutMapping("/args")
    public ResponseEntity<CommonJsonResponse> updateFilterArgs(@Validated FilterArgsUpdateVo filterArgsUpdateVo) {
        filterArgsService.updateFilterArgs(filterArgsUpdateVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping("/args/{id}")
    public ResponseEntity<CommonJsonResponse> deleteFilterArgs(@PathVariable Long id) {
        filterArgsService.deleteFilterArgsById(id);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping("/args")
    public ResponseEntity<CommonJsonResponse> deleteFilterArgsByFilterId(
            @Validated @NotNull(message = "filterId不能为空") Long filterId) {
        filterArgsService.deleteFilterArgsByFilterId(filterId);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

}
