package com.weweibuy.gateway.endpoint;

import com.fasterxml.jackson.databind.JavaType;
import com.weweibuy.framework.common.core.exception.Exceptions;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.eum.CommonErrorCodeEum;
import com.weweibuy.framework.common.core.utils.BeanCopyUtils;
import com.weweibuy.framework.common.core.utils.JackJsonUtils;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.core.lb.LoadBalancerHelper;
import com.weweibuy.gateway.endpoint.model.AppRespDTO;
import com.weweibuy.gateway.route.filter.authorization.model.AppInfo;
import com.weweibuy.gateway.route.filter.constant.RequestHeaderConstant;
import com.weweibuy.gateway.route.filter.sign.SignHelper;
import com.weweibuy.gateway.route.filter.sign.SignTypeEum;
import com.weweibuy.gateway.route.filter.sign.SystemRequestParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author durenhao
 * @date 2020/3/5 22:16
 **/
@RestController
@RequestMapping("/endpoint")
@Profile("dev")
@RequiredArgsConstructor
public class SignController {

    private DefaultDataBufferFactory defaultDataBufferFactory = new DefaultDataBufferFactory();

    private final LoadBalancerHelper loadBalancerHelper;

    @Value("${gw.dev.app-query-url:lb://upms/app/query/accessToken}")
    private String appQueryUrl;

    @RequestMapping("/_sign")
    public Mono<ResponseEntity<CommonDataResponse<String>>> healthCheck(ServerHttpRequest request) {
        MultiValueMap<String, String> queryParams = request.getQueryParams();


        HttpHeaders headers = request.getHeaders();
        String nonce = headers.getFirst(RequestHeaderConstant.X_CA_NONCE);
        String timestamp = headers.getFirst(RequestHeaderConstant.X_CA_TIMESTAMP);
        String signType = headers.getFirst(RequestHeaderConstant.X_CA_SIGN_TYPE);
        String accessToken = headers.getFirst(HttpHeaders.AUTHORIZATION);

        String path = headers.getFirst("path");
        String method = headers.getFirst("method");


        SignTypeEum signTypeEum = SignTypeEum.getSignType(signType);

        if (!StringUtils.isNumeric(timestamp) || signTypeEum == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CommonDataResponse.response(CommonErrorCodeEum.BAD_REQUEST_PARAM, null)));
        }

        SystemRequestParam systemRequestParam = SystemRequestParam.builder()
                .path(path)
                .method(HttpMethod.resolve(method))
                .nonce(nonce)
                .signType(signTypeEum)
                .timestamp(Long.valueOf(timestamp))
                .accessToken(accessToken)
                .build();

        URI appQueryUri = loadBalancerHelper.strToUri(appQueryUrl);

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("accessToken", accessToken);

        JavaType javaType = JackJsonUtils.javaType(CommonDataResponse.class, AppRespDTO.class);


        return loadBalancerHelper.choose(appQueryUri)
                .flatMap(uri -> ReactorHttpHelper.<CommonDataResponse<AppRespDTO>>getForJson(uri.toString() + appQueryUri.getPath(), queryMap, javaType))
                .doOnNext(resp ->
                        Optional.ofNullable(resp)
                                .map(ResponseEntity::getBody)
                                .map(CommonDataResponse::getData)
                                .filter(a -> StringUtils.isNotBlank(a.getAppSecret()))
                                .orElseThrow(() -> Exceptions.business("app信息不存在")))
                .map(ResponseEntity::getBody)
                .map(CommonDataResponse::getData)
                .map(r -> BeanCopyUtils.copy(r, AppInfo.class))
                .flatMap(appInfo -> request.getBody()
                        .defaultIfEmpty(defaultDataBufferFactory.allocateBuffer(0))
                        .map(dataBuffer -> {
                            ByteBuffer byteBuffer = dataBuffer.asByteBuffer();
                            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
                            DataBufferUtils.release(dataBuffer);
                            return charBuffer;
                        })
                        .next()
                        .map(buf -> SignHelper.sign(request, systemRequestParam, appInfo, buf.toString()))
                        .map(s -> ResponseEntity.ok(CommonDataResponse.success(s))));
    }

    @PostMapping("/upload")
    public Mono<ResponseEntity> addAttach(@RequestPart("file") FilePart filePart,
                                          @RequestPart("qqq") String dataId) {

        String strFileName = filePart.filename();//获取文件名
        return Mono.just(ResponseEntity.noContent().build());

    }
}