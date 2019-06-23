package com.weweibuy.gateway.manager.model.dto;

import com.weweibuy.gateway.manager.model.po.RouterPredicate;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/18 22:19
 **/
@Data
@Builder
public class PredicateDto {

    private static final BeanCopier COPIER = BeanCopier.create(RouterPredicate.class, PredicateDto.class, false);

    private Long id;

    private String routerId;

    private String predicateName;

    private Long dictId;

    private String predicateDesc;

    private List<PredicateArgsDto> predicateArgs;

    private List<FilterDto> filters;

    public static PredicateDto convert(RouterPredicate predicate, List<PredicateArgsDto> predicateArgs,
                                       List<FilterDto> filters){
        PredicateDto predicateDto = PredicateDto.builder()
                .predicateArgs(predicateArgs)
                .filters(filters)
                .build();
        COPIER.copy(predicate, predicateDto, null);
        return predicateDto;
    }

}
