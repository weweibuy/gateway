package com.weweibuy.gateway.route.filter.sign;

import com.weweibuy.framework.common.core.utils.HttpRequestUtils;
import com.weweibuy.gateway.route.filter.authorization.model.AppInfo;
import com.weweibuy.gateway.route.filter.utils.SignUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.DigestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;
import org.synchronoss.cloud.nio.stream.storage.StreamStorage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author durenhao
 * @date 2021/1/31 10:48
 **/
public class SignHelper {


    /**
     * 签名
     *
     * @param request
     * @param systemRequestParam
     * @param appInfo
     * @param body
     * @param fileMd5Map         文件 名称 - MD5 map
     * @return
     */
    public static String sign(ServerHttpRequest request,
                              SystemRequestParam systemRequestParam,
                              AppInfo appInfo,
                              String body,
                              Map<String, String> fileMd5Map) {
        Map<String, String> bodyParamMap = new HashMap<>();
        boolean needBody = decodeBodyIfNecessary(body, bodyParamMap, request);
        if (MapUtils.isNotEmpty(fileMd5Map)) {
            fileMd5Map.forEach((k, v) -> bodyParamMap.put(k, v));
        }
        SignTypeEum signType = systemRequestParam.getSignType();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        return SignUtil.sign(signType, appInfo, queryParams, bodyParamMap,
                needBody ? body : null, systemRequestParam);
    }

    private static String[] splitParam(String param) {
        int indexOf = param.indexOf('=');
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

    /**
     * multipart 验签参数; 文件取MD5值验签
     *
     * @param singleValueMap
     * @return
     */
    public static Map<String, String> multipartSignBodyParam(Map<String, Part> singleValueMap) {
        Map<String, String> fileInfoMap = new HashMap<>(singleValueMap.size());
        singleValueMap.forEach((k, v) -> {
            if (v instanceof SynchronossPartHttpMessageReader.SynchronossFormFieldPart) {
                String name = v.name();
                String value = ((SynchronossPartHttpMessageReader.SynchronossFormFieldPart) v).value();
                fileInfoMap.put(name, value);
            } else if (v instanceof SynchronossPartHttpMessageReader.SynchronossFilePart) {
                String name = v.name();
                StreamStorage storage = ((SynchronossPartHttpMessageReader.SynchronossFilePart) v).getStorage();
                String md5 = null;
                try {
                    // 文件MD5值
                    md5 = DigestUtils.md5DigestAsHex(storage.getInputStream());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
                fileInfoMap.put(name, md5);
            }

        });
        return fileInfoMap;
    }

}
