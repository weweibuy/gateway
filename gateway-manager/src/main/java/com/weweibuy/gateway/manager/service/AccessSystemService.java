package com.weweibuy.gateway.manager.service;

import com.weweibuy.gateway.manager.model.dto.AccessSystemQueryDto;
import com.weweibuy.gateway.manager.model.po.AccessSystem;
import com.weweibuy.gateway.manager.model.vo.AccessSystemAddVo;
import com.weweibuy.gateway.manager.model.vo.AccessSystemQueryVo;
import com.weweibuy.gateway.manager.model.vo.AccessSystemUpdateVo;

/**
 * @author durenhao
 * @date 2019/5/19 17:47
 **/
public interface AccessSystemService {

    AccessSystem getSystemById(Long id);

    AccessSystem getSystemByRouterId(String routerId);

    AccessSystemQueryDto getAccessSystems(AccessSystemQueryVo queryVo);

    void addAccessSystem(AccessSystemAddVo accessSystemAddVo);

    void updateAccessSystem(AccessSystemUpdateVo accessSystemUpdateVo);

    void deleteAccessSystemById(Long id);


}
