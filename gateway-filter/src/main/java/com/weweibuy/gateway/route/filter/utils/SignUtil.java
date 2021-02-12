package com.weweibuy.gateway.route.filter.utils;

import com.weweibuy.framework.common.codec.HexUtils;
import com.weweibuy.framework.common.core.exception.Exceptions;
import com.weweibuy.framework.common.core.model.constant.CommonConstant;
import com.weweibuy.gateway.route.filter.authorization.model.AppInfo;
import com.weweibuy.gateway.route.filter.sign.SignTypeEum;
import com.weweibuy.gateway.route.filter.sign.SystemRequestParam;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;
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

    public static String hmacSha256Sign(AppInfo appInfo, String content) {
        try {
            Mac hmacSha256 = Mac.getInstance(CommonConstant.SignConstant.HMAC_SHA256);
            SecretKey secretKey = SECRET_KEY_MAP.computeIfAbsent(appInfo.getAppSecret(), SignUtil::generateKey);
            hmacSha256.init(secretKey);
            byte[] bytes = hmacSha256.doFinal(content.getBytes(CommonConstant.CharsetConstant.UTF8_STR));
            return HexUtils.toHexString(bytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String sign(SignTypeEum signType, AppInfo appInfo, Map<String, List<String>> queryParams,
                              Map<String, String> bodyParam, String body, SystemRequestParam requestParam) {
        String sign = null;
        String content = buildArgsToString(appInfo, queryParams, bodyParam, body, requestParam);
        switch (signType) {
            case MD5:
                sign = SignUtil.md5Sign(content);
                break;
            case HMAC_SHA256:
                sign = SignUtil.hmacSha256Sign(appInfo, content);
                break;
            default:
        }
        return sign;
    }


    public static String md5Sign(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
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


    private static String buildArgsToString(AppInfo appInfo, Map<String, List<String>> queryMap, Map<String, String> bodyMap,
                                            String body, SystemRequestParam requestParam) {

        Map<String, String> treeMap = new TreeMap<>();
        convertMapAddPut(treeMap, queryMap);
        if (MapUtils.isNotEmpty(bodyMap)) {
            treeMap.putAll(bodyMap);
        }

        StringBuilder builder = new StringBuilder();

        builder
                .append("path").append("=").append(requestParam.getPath()).append("&")
                .append("method").append("=").append(requestParam.getMethod()).append("&")
                .append("signType").append("=").append(requestParam.getSignType()).append("&")
                .append("clientId").append("=").append(appInfo.getAppId()).append("&")
                .append("clientSecret").append("=").append(appInfo.getAppSecret()).append("&")
                .append("nonce").append("=").append(requestParam.getNonce()).append("&")
                .append("timestamp").append("=").append(requestParam.getTimestamp()).append("&")
                .append("accessToken").append("=").append(requestParam.getAccessToken()).append("&");

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
