package com.weweibuy.gateway.manager.controller;

import com.weweibuy.gateway.common.model.dto.CommonDataJsonResponse;
import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
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

import javax.validation.constraints.NotBlank;
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
    public ResponseEntity<CommonDataJsonResponse<RouterFilter>> getFilter(@PathVariable Long id) {
        return ResponseEntity.ok(CommonDataJsonResponse.success(filterService.getFilterById(id)));
    }

    @GetMapping
    public ResponseEntity<CommonDataJsonResponse<List<RouterFilter>>> getFilters(
            @Validated @NotNull(message = "routerId不能为空") String routerId) {
        return ResponseEntity.ok(CommonDataJsonResponse.success(filterService.getFiltersByRouterId(routerId)));
    }

    @PostMapping
    public ResponseEntity<CommonCodeJsonResponse> addFilter(@RequestBody @Validated FilterAddVo filterAddVo) {
        filterService.addFilter(filterAddVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @PutMapping
    public ResponseEntity<CommonCodeJsonResponse> updateFilter(@RequestBody @Validated FilterUpdateVo filterUpdateVo) {
        filterService.updateFilter(filterUpdateVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonCodeJsonResponse> deleteFilterById(Long id) {
        filterService.deleteFilterById(id);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @DeleteMapping
    public ResponseEntity<CommonCodeJsonResponse> deleteFiltersByRouterId(
            @Validated @NotNull(message = "predicateId不能为空") String routerId) {
        filterService.deleteFiltersByRouterId(routerId);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }


    @GetMapping("/args/{id}")
    public ResponseEntity<CommonDataJsonResponse<FilterArgs>> getFilterArgsById(@PathVariable Long id) {
        return ResponseEntity.ok(CommonDataJsonResponse.success(filterArgsService.getFilterArgsById(id)));
    }

    @GetMapping("/args")
    public ResponseEntity<CommonDataJsonResponse<List<FilterArgs>>> getFilterArgsByFilterId(
            @Validated @NotBlank(message = "filterId不能为空") String filterId) {
        return ResponseEntity
                .ok(CommonDataJsonResponse.success(filterArgsService.getFilterArgsByFilterId(filterId)));
    }

    @PostMapping("/args")
    public ResponseEntity<CommonCodeJsonResponse> addFilterArgs(@Validated FilterArgsAddVo filterArgsAddVo) {
        filterArgsService.addFilterArgs(filterArgsAddVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @PutMapping("/args")
    public ResponseEntity<CommonCodeJsonResponse> updateFilterArgs(@Validated FilterArgsUpdateVo filterArgsUpdateVo) {
        filterArgsService.updateFilterArgs(filterArgsUpdateVo);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @DeleteMapping("/args/{id}")
    public ResponseEntity<CommonCodeJsonResponse> deleteFilterArgsById(@PathVariable Long id) {
        filterArgsService.deleteFilterArgsById(id);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

    @DeleteMapping("/args")
    public ResponseEntity<CommonCodeJsonResponse> deleteFilterArgsByFilterId(
            @Validated @NotBlank(message = "filterId不能为空") String filterId) {
        filterArgsService.deleteFilterArgsByFilterId(filterId);
        return ResponseEntity.ok(CommonCodeJsonResponse.success());
    }

}
