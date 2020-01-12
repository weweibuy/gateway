package com.weweibuy.gateway.manager.client.model.vo;

import com.weweibuy.gateway.manager.client.model.po.RouterPredicate;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * @author durenhao
 * @date 2019/5/18 22:19
 **/
@Data
@Builder
public class PredicateVo {

    private Long id;

    private String routerId;

    private String predicateId;

    private String predicateName;

    private Boolean isUse;

    private Map<String, String> predicateArgs;

    public static PredicateVo convert(RouterPredicate predicate, Map<String, String> predicateArgs){
        PredicateVo predicateDto = PredicateVo.builder()
                .predicateArgs(predicateArgs)
                .build();
        BeanUtils.copyProperties(predicate, predicateDto);
        return predicateDto;
    }

}
