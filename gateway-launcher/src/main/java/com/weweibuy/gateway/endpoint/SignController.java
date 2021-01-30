package com.weweibuy.gateway.endpoint;

import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.model.eum.CommonErrorCodeEum;
import com.weweibuy.gateway.route.filter.constant.RequestHeaderConstant;
import com.weweibuy.gateway.route.filter.sign.SignTypeEum;
import com.weweibuy.gateway.route.filter.sign.SystemRequestParam;
import com.weweibuy.gateway.route.filter.utils.SignUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author durenhao
 * @date 2020/3/5 22:16
 **/
@RestController
@RequestMapping("/endpoint")
@Profile("dev")
public class SignController {

    private DefaultDataBufferFactory defaultDataBufferFactory = new DefaultDataBufferFactory();

    @RequestMapping("/_sign")
    public Mono<ResponseEntity<CommonDataResponse<String>>> healthCheck(ServerHttpRequest request) {
        MultiValueMap<String, String> queryParams = request.getQueryParams();


        HttpHeaders headers = request.getHeaders();
        String appKey = headers.getFirst(RequestHeaderConstant.X_CA_APP_KEY);
        String nonce = headers.getFirst(RequestHeaderConstant.X_CA_NONCE);
        String timestamp = headers.getFirst(RequestHeaderConstant.X_CA_TIMESTAMP);
        String signature = headers.getFirst(RequestHeaderConstant.X_CA_SIGNATURE);
        String signType = headers.getFirst(RequestHeaderConstant.X_CA_SIGN_TYPE);

        String appSecret = headers.getFirst("appSecret");

        SignTypeEum signTypeEum = SignTypeEum.getSignType(signType);

        if (StringUtils.isAnyBlank(appKey, timestamp, nonce, signType, signature) ||
                !StringUtils.isNumeric(timestamp) || signTypeEum == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CommonDataResponse.response(CommonErrorCodeEum.BAD_REQUEST_PARAM, null)));
        }

        SystemRequestParam systemRequestParam = SystemRequestParam.builder()
                .appKey(appKey)
                .nonce(nonce)
                .signature(signature)
                .signType(signTypeEum)
                .timestamp(Long.valueOf(timestamp))
                .build();

        return request.getBody()
                .defaultIfEmpty(defaultDataBufferFactory.allocateBuffer(0))
                .map(dataBuffer -> {
                    ByteBuffer byteBuffer = dataBuffer.asByteBuffer();
                    CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
                    DataBufferUtils.release(dataBuffer);
                    return charBuffer;
                })
                .next()
                .map(buf -> SignUtil.sign(signTypeEum, appSecret, queryParams, buf.toString(), systemRequestParam))
                .map(s -> ResponseEntity.ok(CommonDataResponse.success(s)));
    }


}
