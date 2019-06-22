package com.weweibuy.gateway.manager.controller;

import com.weweibuy.gateway.common.model.dto.CommonBizJsonResponse;
import com.weweibuy.gateway.common.model.dto.CommonJsonResponse;
import com.weweibuy.gateway.manager.controller.constant.ApiPrefixConstant;
import com.weweibuy.gateway.manager.model.po.GatewayDataDictionary;
import com.weweibuy.gateway.manager.model.vo.DataDictAddVo;
import com.weweibuy.gateway.manager.model.vo.DataDictUpdateVo;
import com.weweibuy.gateway.manager.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 数据字典
 *
 * @author durenhao
 * @date 2019/5/19 18:08
 **/
@RestController
@RequestMapping(value = ApiPrefixConstant.API_PREFIX + "/dict", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataDictController {

    @Autowired
    private DataDictService dataDictService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonBizJsonResponse<GatewayDataDictionary>> getDict(Long id) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(dataDictService.getDictById(id)));
    }

    /**
     *
     * 直接获取全部断言
     *
     * @return
     */
    @GetMapping("/predicates")
    public ResponseEntity<CommonBizJsonResponse<List<GatewayDataDictionary>>> getPredicates() {
        return ResponseEntity.ok(CommonBizJsonResponse.success(dataDictService.getPredicates()));
    }

    @GetMapping("/filters")
    public ResponseEntity<CommonBizJsonResponse<List<GatewayDataDictionary>>> getFilters() {
        return ResponseEntity.ok(CommonBizJsonResponse.success(dataDictService.getFilters()));
    }



    /**
     * 根据 pid 查子节点
     *
     * @param id
     * @return
     */
    @GetMapping("/parent/{id}")
    public ResponseEntity<CommonBizJsonResponse<List<GatewayDataDictionary>>> getChildDict(Long id) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(dataDictService.getChildDictByPid(id)));
    }

    /**
     * 根据类型查询
     *
     * @param dictType
     * @return
     */
    @GetMapping("/type")
    public ResponseEntity<CommonBizJsonResponse<List<GatewayDataDictionary>>> getDictByType(
            @Validated @NotBlank(message = "类型不能为空") String dictType) {
        return ResponseEntity.ok(CommonBizJsonResponse.success(dataDictService.getDictByType(dictType)));
    }

    @PostMapping
    public ResponseEntity<CommonJsonResponse> addDict(@RequestBody @Validated DataDictAddVo dataDictAddVo){
        dataDictService.addDict(dataDictAddVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @PutMapping
    public ResponseEntity<CommonJsonResponse> updateDict(@RequestBody @Validated DataDictUpdateVo dataDictUpdateVo){
        dataDictService.updateDict(dataDictUpdateVo);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonJsonResponse> deleteDict(Long id){
        dataDictService.deleteDictById(id);
        return ResponseEntity.ok(CommonJsonResponse.success());
    }




}
