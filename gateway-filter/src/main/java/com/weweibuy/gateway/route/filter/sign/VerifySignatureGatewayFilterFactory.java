package com.weweibuy.gateway.route.filter.sign;

import com.weweibuy.gateway.route.filter.constant.ExchangeAttributeConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * @author durenhao
 * @date 2020/2/22 19:56
 **/
@Component
@Slf4j
public class VerifySignatureGatewayFilterFactory extends AbstractGatewayFilterFactory {

    private static final Long MAX_TIME_INTERVAL_S = 120L;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            SystemRequestParam systemRequestParam = (SystemRequestParam) exchange.getAttributes().get(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM);

            Long timestamp = systemRequestParam.getTimestamp();
//            if (DateUtils.localDateTimeToTimestampSecond(LocalDateTime.now()) - timestamp > MAX_TIME_INTERVAL_S) {
//                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST,
//                        CommonCodeJsonResponse.badRequestParam("请求时间戳错误"), exchange);
//            }


            // 验证签名
            SignTypeEum signType = systemRequestParam.getSignType();
            ServerHttpRequest httpRequest = exchange.getRequest();
            MultiValueMap<String, String> queryParams = httpRequest.getQueryParams();

            String contentType = httpRequest.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);

            MediaType mediaType = MediaType.valueOf(contentType);

            if(mediaType.isCompatibleWith(MediaType.MULTIPART_FORM_DATA)){
            }

            boolean compatibleWith = mediaType.isCompatibleWith(MediaType.APPLICATION_JSON);

            boolean compatibleWith1 = mediaType.isCompatibleWith(MediaType.MULTIPART_FORM_DATA);

            boolean compatibleWith2 = mediaType.isCompatibleWith(MediaType.TEXT_HTML);



            switch (signType) {
                case MD5:
                case HMAC_SHA256:
                default:

            }

            return chain.filter(exchange);
        };
    }
}
