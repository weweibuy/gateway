package com.weweibuy.gateway.route.filter.utils;

import com.weweibuy.framework.common.core.exception.Exceptions;
import com.weweibuy.framework.common.core.model.constant.CommonConstant;
import com.weweibuy.gateway.route.filter.sign.SignTypeEum;
import com.weweibuy.gateway.route.filter.sign.SystemRequestParam;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author durenhao
 * @date 2020/2/27 21:32
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUtil {

    private static final ConcurrentHashMap<String, SecretKey> SECRET_KEY_MAP = new ConcurrentHashMap();

    public static String hmacSha256Sign(String appSecret, Map<String, List<String>> queryMap,
                                        String body, SystemRequestParam requestParam) {
        try {
            Mac hmacSha256 = Mac.getInstance(CommonConstant.SignConstant.HMAC_SHA256);
            SecretKey secretKey = SECRET_KEY_MAP.computeIfAbsent(appSecret, SignUtil::generateKey);
            hmacSha256.init(secretKey);
            return base64Encode(
                    hmacSha256.doFinal(buildArgsToString(appSecret, queryMap, body, requestParam).getBytes(CommonConstant.CharsetConstant.UTF8_STR)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String sign(SignTypeEum signType, String appSecret, Map<String, List<String>> queryParams,
                              String body, SystemRequestParam requestParam) {
        String sign = null;
        switch (signType) {
            case MD5:
                sign = SignUtil.md5Sign(appSecret, queryParams, body, requestParam);
                break;
            case HMAC_SHA256:
                sign = SignUtil.hmacSha256Sign(appSecret, queryParams, body, requestParam);
                break;
            default:
        }
        return sign;
    }


    public static String md5Sign(String appSecret, Map<String, List<String>> queryMap,
                                 String body, SystemRequestParam requestParam) {
        return base64Encode(DigestUtils.md5DigestAsHex(buildArgsToString(appSecret, queryMap, body, requestParam).getBytes()));
    }

    private static SecretKey generateKey(String appSecret) {
        try {
            byte[] keyBytes = appSecret.getBytes(CommonConstant.CharsetConstant.UTF8_STR);
            return new SecretKeySpec(keyBytes, 0, keyBytes.length, CommonConstant.SignConstant.HMAC_SHA256);
        } catch (UnsupportedEncodingException e) {
            throw Exceptions.uncheckedIO(e);
        }
    }


    public static String base64Encode(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes()));
    }

    public static String base64Encode(byte[] byteArr) {
        return new String(Base64.getEncoder().encode(byteArr));
    }


    private static String buildArgsToString(String appSecret, Map<String, List<String>> queryMap,
                                            String body, SystemRequestParam requestParam) {
        Map<String, String> treeMap = new TreeMap<>();
        convertMapAddPut(treeMap, queryMap);

        StringBuilder builder = new StringBuilder();

        builder.append("app_key").append("=").append(requestParam.getAppKey()).append("&")
                .append("app_secret").append("=").append(appSecret).append("&")
                .append("nonce").append("=").append(requestParam.getNonce()).append("&")
                .append("timestamp").append("=").append(requestParam.getTimestamp())
                .append("&");

        if (!treeMap.isEmpty()) {
            treeMap.forEach((k, v) ->
                    builder.append(k).append("=").append(v).append("&"));
        }
        builder.append("body=");
        Optional.ofNullable(body)
                .ifPresent(builder::append);
        return builder.toString();
    }


    private static void convertMapAddPut(Map<String, String> treeMap, Map<String, List<String>> queryMap) {
        queryMap.forEach((k, v) -> treeMap.put(k, v.get(0)));
    }

}
