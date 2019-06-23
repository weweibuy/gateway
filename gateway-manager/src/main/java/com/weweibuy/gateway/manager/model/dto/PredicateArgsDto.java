package com.weweibuy.gateway.manager.model.dto;

import com.weweibuy.gateway.manager.model.po.PredicateArgs;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @author durenhao
 * @date 2019/5/18 22:23
 **/
@Data
@Builder
public class PredicateArgsDto {

    private static final BeanCopier COPIER = BeanCopier.create(PredicateArgs.class, PredicateArgsDto.class, false);

    private Long id;

    private Long predicateId;

    private String argsName;

    private String argsValue;

    private Long dictId;

    private String argsDesc;

    public static PredicateArgsDto convert(PredicateArgs predicateArgs){
        PredicateArgsDto predicateArgsDto = PredicateArgsDto.builder().build();
        COPIER.copy(predicateArgs, predicateArgsDto, null);
        return predicateArgsDto;
    }

}
