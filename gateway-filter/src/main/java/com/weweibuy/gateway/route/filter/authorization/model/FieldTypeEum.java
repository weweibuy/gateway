package com.weweibuy.gateway.route.filter.authorization.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 模型属性类型
 *
 * @author durenhao
 * @date 2020/11/8 11:34
 **/
public enum FieldTypeEum {

    STRING,
    NUMBER,
    BOOLEAN,
    COLLECTION_NUMBER,
    COLLECTION_STRING,
    ;

    private static final Map<String, FieldTypeEum> FIELD_TYPE_EUM_MAP;

    static {
        FIELD_TYPE_EUM_MAP = Arrays.stream(FieldTypeEum.values())
                .collect(Collectors.toMap(FieldTypeEum::toString, Function.identity()));
    }

    public static boolean isCollection(String filedType) {
        return COLLECTION_STRING.toString().equals(filedType)
                || COLLECTION_NUMBER.toString().equals(filedType);
    }

    public static boolean isCollection(FieldTypeEum filedType) {
        return COLLECTION_STRING.equals(filedType)
                || COLLECTION_NUMBER.equals(filedType);
    }

    public static Optional<FieldTypeEum> fieldTypeEum(String fieldType) {
        return Optional.ofNullable(FIELD_TYPE_EUM_MAP.get(fieldType));
    }

}
