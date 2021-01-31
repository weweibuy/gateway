package com.weweibuy.gateway.route.filter.sign;

import com.weweibuy.framework.common.core.utils.HttpRequestUtils;
import com.weweibuy.gateway.route.filter.utils.SignUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author durenhao
 * @date 2021/1/31 10:48
 **/
public class SignHelper {


    public static String sign(ServerHttpRequest request,
                              SystemRequestParam systemRequestParam,
                              String appSecret,
                              String body) {
        Map<String, String> bodyParamMap = new HashMap<>();
        boolean needBody = decodeBodyIfNecessary(body, bodyParamMap, request);
        SignTypeEum signType = systemRequestParam.getSignType();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        return SignUtil.sign(signType, appSecret, queryParams, bodyParamMap,
                needBody ? body : null, systemRequestParam);
    }

    private static String[] splitParam(String param) {
        int indexOf = param.lastIndexOf('=');
        String key = param.substring(0, indexOf);
        String value = param.substring(indexOf + 1, param.length());
        if (StringUtils.isNotEmpty(key)) {
            key = HttpRequestUtils.urlDecode(key);
        }
        if (StringUtils.isNotEmpty(value)) {
            value = HttpRequestUtils.urlDecode(value);
        }
        String[] arr = {key, value};
        return arr;
    }


    private static boolean decodeBodyIfNecessary(String body, Map<String, String> bodyParamMap, ServerHttpRequest request) {
        String contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        if (StringUtils.isBlank(contentType)) {
            return true;
        }
        MediaType mediaType = MediaType.parseMediaType(contentType);

        // application/json 不处理
        if (mediaType.equalsTypeAndSubtype(MediaType.APPLICATION_JSON)) {
            return true;
        }

        // application/x-www-form-urlencoded  分隔参数并URL解码
        if (mediaType.equalsTypeAndSubtype(MediaType.APPLICATION_FORM_URLENCODED)) {
            String[] paramKeyValue = body.split("&");
            Arrays.stream(paramKeyValue)
                    .map(s -> splitParam(s))
                    .filter(a -> StringUtils.isNotEmpty(a[0]))
                    .forEach(s -> bodyParamMap.put(s[0], s[1]));
            return false;
        }
        // multipart/form-data  TODO 如何处理?
        if (mediaType.equalsTypeAndSubtype(MediaType.MULTIPART_FORM_DATA)) {
            return false;
        }
        throw new UnsupportedMediaTypeException(mediaType,
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA));

    }


}
