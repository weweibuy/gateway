package com.weweibuy.gateway.route.filter.utils;

import com.weweibuy.gateway.common.constant.CommonConstant;
import com.weweibuy.gateway.core.utils.JackJsonUtils;
import com.weweibuy.gateway.route.filter.sign.SystemRequestParam;
import org.springframework.util.DigestUtils;
import org.springframework.util.MultiValueMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author durenhao
 * @date 2020/2/27 21:32
 **/
public class SignUtil {

    public static String hmacSha256Sign(String appSecret, Map<String, List<String>> queryMap,
                                        Map<String, String> bodyMap, SystemRequestParam requestParam) {
        try {
            Mac hmacSha256 = Mac.getInstance(CommonConstant.SignConstant.HMAC_SHA256);
            byte[] keyBytes = appSecret.getBytes(CommonConstant.CharsetConstant.UTF8_STR);
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, CommonConstant.SignConstant.HMAC_SHA256));
            return base64Encode(
                    hmacSha256.doFinal(buildArgsToString(appSecret, queryMap, bodyMap, requestParam).getBytes(CommonConstant.CharsetConstant.UTF8_STR)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5Sign(String appSecret, Map<String, List<String>> queryMap,
                                 Map<String, String> bodyMap, SystemRequestParam requestParam) {
        return base64Encode(DigestUtils.md5DigestAsHex(buildArgsToString(appSecret, queryMap, bodyMap, requestParam).getBytes()));
    }


    public static String base64Encode(String str) {
        return new String(Base64.getDecoder().decode(str));
    }

    public static String base64Encode(byte[] byteArr) {
        return new String(Base64.getEncoder().encode(byteArr));
    }


    private static String buildArgsToString(String appSecret, Map queryMap,
                                            Map bodyMap, SystemRequestParam requestParam) {
        Map<String, String> treeMap = new TreeMap<String, String>();
        convertMapAddPut(treeMap, queryMap);
        convertMapAddPut(treeMap, bodyMap);

        StringBuilder builder = new StringBuilder();
        treeMap.forEach((k, v) -> {
            builder.append(k).append("=").append(v).append("&");
        });
        builder.append("app_key").append("=").append(requestParam.getAppKey()).append("&")
                .append("app_secret").append("=").append(appSecret).append("&")
                .append("nonce").append("=").append(requestParam.getNonce()).append("&")
                .append("timestamp").append("=").append(requestParam.getTimestamp());
        return builder.toString();
    }


    private static void convertMapAddPut(Map<String, String> treeMap, Map map) {
        if (map instanceof MultiValueMap) {
            MultiValueMap<String, String> multiValueMap = (MultiValueMap<String, String>) map;
            multiValueMap.forEach((k, v) -> treeMap.put(k, v.get(0)));
        } else {
            Map<String, Object> stringMap = map;
            stringMap.forEach((k, v) -> {
                if (v instanceof String) {
                    treeMap.put(k, (String) v);
                } else {
                    treeMap.put(k, JackJsonUtils.write(v));
                }
            });
        }

    }

}
