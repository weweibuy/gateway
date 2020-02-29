package com.weweibuy.gateway.route.filter.sign;

import java.util.HashMap;
import java.util.Map;

/**
 * @author durenhao
 * @date 2020/2/27 20:03
 **/
public enum SignTypeEum {

    MD5,

    HMAC_SHA256,;

    private static final Map<String, SignTypeEum> SIGN_TYPE_EUM_MAP = new HashMap<>(4);

    static {
        SIGN_TYPE_EUM_MAP.put("MD5", SignTypeEum.MD5);
        SIGN_TYPE_EUM_MAP.put("HMAC_SHA256", SignTypeEum.HMAC_SHA256);
    }

    public static SignTypeEum getSignType(String type) {
        return SIGN_TYPE_EUM_MAP.get(type);
    }


}
