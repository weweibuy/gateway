package com.weweibuy.gateway.manager.service;

import com.weweibuy.gateway.manager.model.po.GatewayDataDictionary;
import com.weweibuy.gateway.manager.model.vo.DataDictAddVo;
import com.weweibuy.gateway.manager.model.vo.DataDictUpdateVo;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/19 18:14
 **/
public interface DataDictService {

    GatewayDataDictionary getDictById(Long id);

    List<GatewayDataDictionary> getChildDictByPid(Long id);

    List<GatewayDataDictionary> getDictByType(@NotBlank(message = "类型不能为空") String dictType);

    void addDict(DataDictAddVo dataDictAddVo);

    void updateDict(DataDictUpdateVo dataDictUpdateVo);

    void deleteDictById(Long id);

    List<GatewayDataDictionary> getPredicates();

    List<GatewayDataDictionary> getFilters();
}
