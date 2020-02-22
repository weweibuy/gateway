package com.weweibuy.gateway.route.model.vo;

import com.weweibuy.gateway.route.model.po.RoutePredicate;
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

    private String routerId;

    private String predicateId;

    private String predicateName;

    private Map<String, String> predicateArgs;

    public static PredicateVo convert(RoutePredicate predicate, Map<String, String> predicateArgs){
        PredicateVo predicateDto = PredicateVo.builder()
                .predicateArgs(predicateArgs)
                .build();
        BeanUtils.copyProperties(predicate, predicateDto);
        return predicateDto;
    }

}
