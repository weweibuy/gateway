package com.weweibuy.gateway.route.filter.authorization;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.core.lb.LoadBalancerHelper;
import com.weweibuy.gateway.route.filter.authorization.model.AuthorizationReq;
import com.weweibuy.gateway.route.filter.authorization.model.AuthorizationResp;
import com.weweibuy.gateway.route.filter.config.AuthenticationProperties;
import com.weweibuy.gateway.route.filter.sign.SystemRequestParam;
import com.weweibuy.webuy.common.model.dto.CommonCodeJsonResponse;
import com.weweibuy.webuy.common.model.dto.CommonDataJsonResponse;
import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author durenhao
 * @date 2019/7/6 9:27
 **/
@Slf4j
@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory {

    private JavaType authorizationRespType;

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    public AuthenticationGatewayFilterFactory(ObjectMapper objectMapper) {
        authorizationRespType = objectMapper.getTypeFactory().constructParametricType(CommonDataJsonResponse.class, AuthorizationResp.class);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String service = exchange.getAttribute(ExchangeAttributeConstant.SERVICE_KEY);

            SystemRequestParam systemRequestParam = (SystemRequestParam) exchange.getAttributes().get(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM);

            String appKey = systemRequestParam.getAppKey();

            if (StringUtils.isAnyBlank(appKey, service) || appKey.length() <= 6) {
                return ReactorHttpHelper.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeJsonResponse.unauthorized(), exchange);
            }

            AuthorizationReq authorizationRe = new AuthorizationReq(appKey, service, exchange.getRequest());

            URI uri = loadBalancerHelper.toLbUrl(authenticationProperties.getAuthUrl());
            return ReactorHttpHelper.<CommonDataJsonResponse<AuthorizationResp>>executeForJson(HttpMethod.POST, uri.toString(),
                    null, authorizationRe, authorizationRespType)
                    .flatMap(res -> hashAuthentication(res, chain, exchange));
        };
    }


    private Mono<Void> hashAuthentication(ResponseEntity<CommonDataJsonResponse<AuthorizationResp>> responseEntity,
                                          GatewayFilterChain chain, ServerWebExchange exchange) {


        int status = responseEntity.getStatusCode().value();
        if (status == 200 && "0".equals(responseEntity.getBody().getCode())) {
            CommonDataJsonResponse<AuthorizationResp> body = responseEntity.getBody();
            // 设置app 信息
            exchange.getAttributes().put(ExchangeAttributeConstant.APP_SECRET_ATTR, body.getData().getAppSecret());
            return chain.filter(exchange);
        } else if (status >= 400 && status < 500) {
            return ReactorHttpHelper.buildAndWriteJson(HttpStatus.BAD_REQUEST, CommonCodeJsonResponse.badRequestParam(), exchange);
        } else if (status >= 500) {
            return ReactorHttpHelper.buildAndWriteJson(HttpStatus.INTERNAL_SERVER_ERROR, CommonCodeJsonResponse.unknownException(), exchange);
        }
        return ReactorHttpHelper.buildAndWriteJson(HttpStatus.UNAUTHORIZED, CommonCodeJsonResponse.unauthorized(), exchange);
    }


}
