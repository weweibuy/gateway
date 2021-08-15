package com.weweibuy.gateway.router.service;

import com.github.pagehelper.Page;
import com.weweibuy.framework.common.core.model.dto.CommonPageResult;
import com.weweibuy.framework.common.core.utils.BeanCopyUtils;
import com.weweibuy.framework.common.db.utils.PageHelperUtils;
import com.weweibuy.gateway.router.model.dto.*;
import com.weweibuy.gateway.router.model.example.GatewayRouterExample;
import com.weweibuy.gateway.router.model.po.GatewayRouter;
import com.weweibuy.gateway.router.model.po.RouterFilter;
import com.weweibuy.gateway.router.model.po.RouterPredicate;
import com.weweibuy.gateway.router.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2021/8/12 21:01
 **/
@Service
@RequiredArgsConstructor
public class RouterService {

    private final RouterRepository routerRepository;

    private final PredicateRepository predicateRepository;

    private final FilterRepository filterRepository;

    private final PredicateArgRepository predicateArgRepository;

    private final FilterArgRepository filterArgRepository;


    public CommonPageResult<RouterRespDTO> routerList(RouterQueryReqDTO queryReqDTO) {
        Page<Object> page = PageHelperUtils.startPage(queryReqDTO);
        GatewayRouterExample example = Optional.ofNullable(queryReqDTO)
                .orElseGet(RouterQueryReqDTO::new)
                .toExample();
        List<GatewayRouter> routerList = routerRepository.select(example);
        List<RouterRespDTO> dtoList = BeanCopyUtils.copyCollection(routerList, GatewayRouter.class, RouterRespDTO.class);
        return CommonPageResult.withTotalAndList(page.getTotal(), dtoList);

    }

    public RouterRespDTO router(String routerId) {
        return routerRepository.select(routerId)
                .map(r -> BeanCopyUtils.copy(r, RouterRespDTO.class))
                .orElse(null);
    }

    public List<PredicateRespDTO> predicateList(String routerId) {
        if (StringUtils.isBlank(routerId)) {
            return Collections.emptyList();
        }
        List<RouterPredicate> predicateList = predicateRepository.select(routerId);

        List<String> predicateIdList = predicateList.stream()
                .map(RouterPredicate::getPredicateId)
                .collect(Collectors.toList());
        Map<String, List<PredicateArgRespDTO>> predicateIdArgMap = Optional.ofNullable(predicateIdList)
                .filter(CollectionUtils::isNotEmpty)
                .map(predicateArgRepository::select)
                .map(l -> l.stream()
                        .map(a -> BeanCopyUtils.copy(a, PredicateArgRespDTO.class))
                        .collect(Collectors.groupingBy(PredicateArgRespDTO::getPredicateId)))
                .orElse(Collections.emptyMap());

        return predicateList.stream()
                .sorted(Comparator.comparing(RouterPredicate::getPredicatePriority))
                .map(p -> BeanCopyUtils.copy(p, PredicateRespDTO.class))
                .peek(p -> p.setArgList(Optional.ofNullable(predicateIdArgMap.get(p.getPredicateId()))
                        .orElse(Collections.emptyList())))
                .collect(Collectors.toList());


    }

    public List<FilterRespDTO> filterList(String routerId) {
        if (StringUtils.isBlank(routerId)) {
            return Collections.emptyList();
        }
        List<RouterFilter> filterList = filterRepository.select(routerId);

        List<String> filterIdList = filterList.stream()
                .map(RouterFilter::getFilterId)
                .collect(Collectors.toList());

        Map<String, List<FilterArgRespDTO>> filterIdArgMap = Optional.ofNullable(filterIdList)
                .filter(CollectionUtils::isNotEmpty)
                .map(filterArgRepository::select)
                .map(l -> l.stream()
                        .map(a -> BeanCopyUtils.copy(a, FilterArgRespDTO.class))
                        .collect(Collectors.groupingBy(FilterArgRespDTO::getFilterId)))
                .orElse(Collections.emptyMap());

        return filterList.stream()
                .sorted(Comparator.comparing(RouterFilter::getFilterPriority))
                .map(p -> BeanCopyUtils.copy(p, FilterRespDTO.class))
                .peek(p -> p.setArgList(Optional.ofNullable(filterIdArgMap.get(p.getFilterId()))
                        .orElse(Collections.emptyList())))
                .collect(Collectors.toList());

    }
}
