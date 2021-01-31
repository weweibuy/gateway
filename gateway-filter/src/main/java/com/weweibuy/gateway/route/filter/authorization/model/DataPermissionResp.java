package com.weweibuy.gateway.route.filter.authorization.model;

import lombok.Data;

/**
 * 数据权限响应
 *
 * @author durenhao
 * @date 2021/1/26 21:43
 **/
@Data
public class DataPermissionResp {

    public static final Integer QUERY_PARAM_TYPE = 0;

    public static final Integer BODY_PARAM_TYPE = 1;


    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String fieldValue;

    /**
     * 请求参数类型 0: query; 1: body
     */
    private Integer reqParamType;

    /**
     * 字段类型
     */
    private String fieldType;

}
