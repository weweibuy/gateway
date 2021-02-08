package com.weweibuy.gateway.route.filter.sign;

import com.weweibuy.framework.common.core.model.dto.CommonCodeResponse;
import com.weweibuy.framework.common.core.utils.DateTimeUtils;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.route.filter.config.VerifySignatureProperties;
import com.weweibuy.gateway.route.filter.constant.RequestHeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;

/**
 * 验签 系统级输入参数过滤器
 *
 * @author durenhao
 * @date 2020/2/27 20:14
 **/
@Slf4j
@Component
public class SystemRequestParamGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Autowired
    private VerifySignatureProperties verifySignatureProperties;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            HttpHeaders headers = request.getHeaders();
            String contentType = headers.getFirst(HttpHeaders.CONTENT_TYPE);
            if (!validateMediaType(contentType)) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                        CommonCodeResponse.unSupportedMediaType(), exchange);
            }

            String timestamp = headers.getFirst(RequestHeaderConstant.X_CA_TIMESTAMP);
            String nonce = headers.getFirst(RequestHeaderConstant.X_CA_NONCE);
            String signType = headers.getFirst(RequestHeaderConstant.X_CA_SIGN_TYPE);
            String signature = headers.getFirst(RequestHeaderConstant.X_CA_SIGNATURE);
            String accessToken = headers.getFirst(HttpHeaders.AUTHORIZATION);

            SignTypeEum signTypeEum = null;

            if (StringUtils.isAnyBlank(timestamp, nonce, signType, signature, accessToken) ||
                    !StringUtils.isNumeric(timestamp) || (signTypeEum = SignTypeEum.getSignType(signType)) == null) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST, CommonCodeResponse.badSystemRequestParam(), exchange);
            }

            if (accessToken.length() < 10) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeResponse.unauthorized(), exchange);
            }

            Long timestampL = Long.valueOf(timestamp);

            if (DateTimeUtils.localDateTimeToTimestampSecond(LocalDateTime.now()) - timestampL > verifySignatureProperties.getTimestampIntervalSecond()) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST,
                        CommonCodeResponse.badRequestParam("请求时间戳错误"), exchange);
            }

            LinkedHashSet<URI> uris = exchange
                    .getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
            URI uri = uris.iterator().next();

            HttpMethod method = request.getMethod();

            SystemRequestParam systemRequestParam = SystemRequestParam.builder()
                    .path(uri.getPath())
                    .method(method)
                    .nonce(nonce)
                    .signature(signature)
                    .signType(signTypeEum)
                    .timestamp(Long.valueOf(timestamp))
                    .accessToken(accessToken)
                    .build();

            Map<String, Object> attributeMap = exchange.getAttributes();
            attributeMap.put(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM, systemRequestParam);
            return chain.filter(exchange);
        };
    }

    private boolean validateMediaType(String contentType) {

        return Optional.ofNullable(contentType)
                .filter(StringUtils::isNotBlank)
                .map(MediaType::parseMediaType)
                .map(m -> m.isPresentIn(Arrays.asList(MediaType.APPLICATION_JSON,
                        MediaType.APPLICATION_FORM_URLENCODED,
                        MediaType.MULTIPART_FORM_DATA)))
                .orElse(true);

    }


}
