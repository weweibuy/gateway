package com.weweibuy.gateway.route.filter.authorization.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 模型属性类型
 * COLLECTION_STRING
 * @author durenhao
 * @date 2020/11/8 11:34
 **/
@Getter
public enum FieldTypeEum {

    STRING(0),
    NUMBER(1),
    BOOLEAN(2),
    COLLECTION_STRING(3),
    COLLECTION_NUMBER(4),
    ;

    private final Integer code;

    private static final Map<Integer, FieldTypeEum> FIELD_TYPE_EUM_MAP;

    static {
        FIELD_TYPE_EUM_MAP = Arrays.stream(FieldTypeEum.values())
                .collect(Collectors.toMap(FieldTypeEum::getCode, Function.identity()));
    }

    FieldTypeEum(Integer code) {
        this.code = code;
    }

    public static boolean isCollection(Integer filedTypeCode) {
        return COLLECTION_STRING.getCode().equals(filedTypeCode)
                || COLLECTION_NUMBER.getCode().equals(filedTypeCode);
    }

    public static boolean isCollection(FieldTypeEum filedType) {
        return COLLECTION_STRING.equals(filedType)
                || COLLECTION_NUMBER.equals(filedType);
    }

    public static Optional<FieldTypeEum> fieldTypeEum(Integer filedTypeCode) {
        return Optional.ofNullable(FIELD_TYPE_EUM_MAP.get(filedTypeCode));
    }

}
