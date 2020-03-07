package com.weweibuy.gateway.route.filter.sign;

import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
import com.weweibuy.gateway.common.utils.DateUtils;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.route.filter.config.VerifySignatureProperties;
import com.weweibuy.gateway.route.filter.constant.RequestHeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
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
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String appKey = headers.getFirst(RequestHeaderConstant.X_CA_APP_KEY);
            String timestamp = headers.getFirst(RequestHeaderConstant.X_CA_TIMESTAMP);
            String nonce = headers.getFirst(RequestHeaderConstant.X_CA_NONCE);
            String signType = headers.getFirst(RequestHeaderConstant.X_CA_SIGN_TYPE);
            String signature = headers.getFirst(RequestHeaderConstant.X_CA_SIGNATURE);
            SignTypeEum signTypeEum = null;

            if (StringUtils.isAnyBlank(appKey, timestamp, nonce, signType, signature) ||
                    !NumberUtils.isCreatable(timestamp) || (signTypeEum = SignTypeEum.getSignType(signType)) == null) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST, CommonCodeJsonResponse.badSystemRequestParam(), exchange);
            }

            Long timestampL = Long.valueOf(timestamp);

            if (DateUtils.localDateTimeToTimestampSecond(LocalDateTime.now()) - timestampL > verifySignatureProperties.getTimestampIntervalSecond()) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST,
                        CommonCodeJsonResponse.badRequestParam("请求时间戳错误"), exchange);
            }

            String contentType = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
            if (StringUtils.isBlank(contentType)) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST, CommonCodeJsonResponse.UnSupportedMediaType(), exchange);
            }

            MediaType mediaType = MediaType.parseMediaType(contentType);

            if (!(mediaType.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED) || mediaType.isCompatibleWith(MediaType.APPLICATION_JSON))) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST, CommonCodeJsonResponse.UnSupportedMediaType(), exchange);
            }

            SystemRequestParam systemRequestParam = SystemRequestParam.builder()
                    .appKey(appKey)
                    .nonce(nonce)
                    .signature(signature)
                    .signType(signTypeEum)
                    .timestamp(Long.valueOf(timestamp))
                    .build();

            Map<String, Object> attributeMap = exchange.getAttributes();
            attributeMap.put(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM, systemRequestParam);
            return chain.filter(exchange);
        };
    }


}
