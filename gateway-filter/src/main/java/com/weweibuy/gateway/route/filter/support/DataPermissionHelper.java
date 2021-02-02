package com.weweibuy.gateway.route.filter.support;

import com.weweibuy.framework.common.core.exception.BusinessException;
import com.weweibuy.framework.common.core.exception.Exceptions;
import com.weweibuy.framework.common.core.model.eum.CommonErrorCodeEum;
import com.weweibuy.gateway.route.filter.authorization.model.DataPermissionResp;
import com.weweibuy.gateway.route.filter.authorization.model.FieldTypeEum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author durenhao
 * @date 2021/2/2 19:30
 **/
public class DataPermissionHelper {


    /**
     * 修改请求体
     *
     * @param reqMap
     * @return
     */
    public static Map<String, Object> modifyBody(Map<String, Object> reqMap, List<DataPermissionResp> bodyDataPermissionList) {
        Map<Boolean, List<DataPermissionResp>> listMap = bodyDataPermissionList.stream()
                .collect(Collectors.groupingBy(d -> d.getFieldName().indexOf(".") != -1));
        // 只操作一层
        List<DataPermissionResp> dataPermissionList = listMap.get(false);
        if (CollectionUtils.isNotEmpty(dataPermissionList)) {
            dataPermissionList.forEach(p ->
                    modify(reqMap.get(p.getFieldName()), p, p.getFieldName(), reqMap));
        }

        // 操作多层
        List<DataPermissionResp> nestingPermissionList = listMap.get(true);

        if (CollectionUtils.isNotEmpty(nestingPermissionList)) {
            modifyNestingParam(nestingPermissionList, reqMap);
        }
        return reqMap;
    }

    private static void modify(Object oriValue, DataPermissionResp dataPermissionResp, String fieldName, Map<String, Object> reqMap) {
        FieldTypeEum fieldTypeEum = FieldTypeEum.fieldTypeEum(dataPermissionResp.getFieldType())
                .orElseThrow(() -> Exceptions.formatSystem("数据权限无法识别的字段类型: %s", dataPermissionResp.getFieldType()));
        if (oriValue == null) {
            reqMap.put(fieldName, convertBodyValue(fieldTypeEum, dataPermissionResp.getFieldValue()));
        } else {
            reqMap.replace(fieldName, modifyBodyValue(fieldTypeEum, oriValue, dataPermissionResp.getFieldValue()));
        }
    }

    private static void modifyNestingParam(List<DataPermissionResp> nestingPermissionList, Map<String, Object> reqMap) {
        nestingPermissionList.forEach(p -> modifyNestingParam(p, reqMap, p.getFieldName().split("\\."), 0));
    }

    private static void modifyNestingParam(DataPermissionResp permissionResp, Map<String, Object> reqMap, String[] split, int index) {
        if (index > split.length) {
            return;
        }
        String key = split[index];
        Object value = reqMap.get(key);
        if (index == split.length - 1) {
            modify(value, permissionResp, key, reqMap);
            return;
        }

        if (value == null) {
            Map<String, Object> linkedHashMap = new LinkedHashMap<>(8);
            value = linkedHashMap;
            reqMap.put(key, linkedHashMap);
        }
        if (!(value instanceof Map)) {
            throw Exceptions.formatBusiness("数据权限字段类型输入类型错误, 预计: %s, 实际: %s", "Map", value.getClass().getName());
        }
        modifyNestingParam(permissionResp, (Map<String, Object>) value, split, ++index);
    }


    public static Object modifyBodyValue(FieldTypeEum fieldTypeEum, Object body, String pValue) {
        boolean isCollection = FieldTypeEum.isCollection(fieldTypeEum);

        if (!isCollection && body instanceof List) {
            throw Exceptions.formatBusiness("数据权限字段类型输入类型错误, 预计: %s, 实际: %s", fieldTypeEum, "List");
        }
        if (isCollection && !(body instanceof List)) {
            throw Exceptions.formatBusiness("数据权限字段类型输入类型错误, 预计: %s, 实际: %s", fieldTypeEum, "非List");
        }
        if (!isCollection && !Objects.equals(body, pValue)) {
            // TODO 是否支持隐式 集合 类似 (1,2,3) 这种形式
            // 没有权限
            throw forbiddenException(body.toString());
        }
        if (isCollection) {
            List<Object> bodyList = (List) body;
            Set<String> collect = Arrays.stream(pValue.split(","))
                    .collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(bodyList)) {
                return collect;
            }
            List<String> stringList = bodyList.stream()
                    .map(b -> b + "")
                    .filter(collect::contains)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(stringList)) {
                throw forbiddenException(bodyList.toString());
            }
            return stringList;
        }
        return pValue;
    }


    public static Object convertBodyValue(FieldTypeEum fieldTypeEum, String value) {
        if (FieldTypeEum.isCollection(fieldTypeEum)) {
            return Arrays.stream(value.split(","))
                    .collect(Collectors.toList());
        }
        return value;
    }


    public static void modifyQueryParam(List<DataPermissionResp> queryDataPermissionList,
                                        MultiValueMap<String, String> queryParams) {

        queryDataPermissionList.stream()
                .forEach(p -> {
                    List<String> paramValueList = queryParams.get(p.getFieldName());
                    if (CollectionUtils.isEmpty(paramValueList)) {
                        // 设置权限
                        queryParams.add(p.getFieldName(), p.getFieldValue());
                        return;
                    }
                    String value = paramValueList.get(0);
                    String fieldValue = p.getFieldValue();
                    String fieldType = p.getFieldType();
                    if (FieldTypeEum.isCollection(fieldType)) {
                        Set<String> permissionValueSet = Arrays.stream(fieldValue.split(","))
                                .collect(Collectors.toSet());

                        String allowedValue = Arrays.stream(value.split(","))
                                .filter(permissionValueSet::contains)
                                .collect(Collectors.joining(","));
                        if (StringUtils.isBlank(allowedValue)) {
                            throw forbiddenException(value);
                        }
                        queryParams.put(p.getFieldName(), Collections.singletonList(allowedValue));
                    } else {
                        if (!value.equals(fieldValue)) {
                            throw forbiddenException(value);
                        }
                    }
                });
    }

    private static BusinessException forbiddenException(String detailMag) {
        // TODO 响应HTTP status 为403
        return Exceptions.business(CommonErrorCodeEum.FORBIDDEN,
                "没有数据权限: " + detailMag);
    }


}
