package com.weweibuy.gateway.route.filter.support;

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
            dataPermissionList.forEach(p -> {
                Object o = reqMap.get(p.getFieldName());
                String fieldType = p.getFieldType();
                FieldTypeEum fieldTypeEum = FieldTypeEum.fieldTypeEum(p.getFieldType())
                        .orElseThrow(() -> Exceptions.formatSystem("数据权限无法识别的字段类型: %s", p.getFieldType()));
                String fieldValue = p.getFieldValue();
                if (o == null) {
                    reqMap.put(p.getFieldName(), convertBodyValue(fieldTypeEum, fieldValue));
                } else {
                    reqMap.replace(p.getFieldName(), modifyBodyValue(fieldTypeEum, o, fieldValue));
                }
            });

        }

        // 操作多层
        List<DataPermissionResp> nestingPermissionList = listMap.get(true);


        return reqMap;
    }


    public static Object modifyBodyValue(FieldTypeEum fieldTypeEum, Object body, String pValue) {
        if (!FieldTypeEum.isCollection(fieldTypeEum) && body instanceof List) {
            throw Exceptions.formatBusiness("数据权限字段类型输入类型错误, 预计: %s, 实际: %s", fieldTypeEum, "List");
        }
        if (FieldTypeEum.isCollection(fieldTypeEum) && !(body instanceof List)) {
            throw Exceptions.formatBusiness("数据权限字段类型输入类型错误, 预计: %s, 实际: %s", fieldTypeEum, "非List");
        }
        if (!FieldTypeEum.isCollection(fieldTypeEum) && !Objects.equals(body, pValue)) {
            // TODO 是否支持隐式 集合 类似 (1,2,3) 这种形式
            // 没有权限
            throw Exceptions.business(CommonErrorCodeEum.FORBIDDEN,
                    "没有数据权限: " + body);
        }
        if (FieldTypeEum.isCollection(fieldTypeEum)) {
            List<Object> bodyList = (List) body;
            String[] split = pValue.split(",");
            Set<String> collect = Arrays.stream(split)
                    .map(s -> convertBodyValue(fieldTypeEum, s) + "")
                    .collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(bodyList)) {
                return collect;
            }
            List<String> stringList = bodyList.stream()
                    .map(b -> b + "")
                    .filter(collect::contains)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(stringList)) {
                throw Exceptions.business(CommonErrorCodeEum.FORBIDDEN,
                        "没有数据权限: " + bodyList);
            }
            return stringList;
        }
        return convertBodyValue(fieldTypeEum, pValue);
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
                            // TODO 没有权限 响应

                        }
                        queryParams.put(p.getFieldName(), Collections.singletonList(allowedValue));
                    } else {
                        if (!value.equals(fieldValue)) {
                            // TODO 没有权限 响应

                        }
                    }
                });
    }

}
