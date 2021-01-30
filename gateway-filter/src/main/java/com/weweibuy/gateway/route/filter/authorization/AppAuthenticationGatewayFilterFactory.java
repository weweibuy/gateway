package com.weweibuy.gateway.route.filter.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.route.filter.authorization.model.AppAuthorizationReq;
import com.weweibuy.gateway.route.filter.authorization.model.AppAuthorizationResp;
import com.weweibuy.gateway.route.filter.path.ServiceMatchStripPrefixGatewayFilterFactory;
import com.weweibuy.gateway.route.filter.sign.SystemRequestParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;

/**
 * app 权限控制
 * 依赖{@link ServiceMatchStripPrefixGatewayFilterFactory} 设置请求服务
 *
 * @author durenhao
 * @date 2019/7/6 9:27
 **/
@Slf4j
@Component
public class AppAuthenticationGatewayFilterFactory extends AbstractAuthGatewayFilterFactory<AbstractAuthGatewayFilterFactory.Config, AppAuthorizationReq, AppAuthorizationResp> {


    public AppAuthenticationGatewayFilterFactory(ObjectMapper objectMapper) {
        super(AppAuthenticationGatewayFilterFactory.Config.class,
                objectMapper.getTypeFactory().constructParametricType(CommonDataResponse.class, AppAuthorizationResp.class));
    }

    @Override
    public void onAuthSuccess(AppAuthorizationResp response, GatewayFilterChain chain, ServerWebExchange exchange) {
        Optional.ofNullable(response)
                .map(AppAuthorizationResp::getAppId)
                .ifPresent(user -> exchange.getAttributes().put(ExchangeAttributeConstant.USER_ID_ATTR, user));

        Optional.ofNullable(response)
                .map(AppAuthorizationResp::getAppSecret)
                .ifPresent(user -> exchange.getAttributes().put(ExchangeAttributeConstant.APP_SECRET_ATTR, user));

    }

    @Override
    protected AppAuthorizationReq authReq(Config config, GatewayFilterChain chain, ServerWebExchange exchange) {
        String service = exchange.getAttribute(ExchangeAttributeConstant.SERVICE_KEY);

        SystemRequestParam systemRequestParam = (SystemRequestParam) exchange.getAttributes().get(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM);

        String appKey = systemRequestParam.getAppKey();

        return new AppAuthorizationReq(appKey, service, exchange.getRequest());
    }

    @Override
    protected boolean preCheck(Config config, GatewayFilterChain chain, ServerWebExchange exchange) {

        String service = exchange.getAttribute(ExchangeAttributeConstant.SERVICE_KEY);

        SystemRequestParam systemRequestParam = (SystemRequestParam) exchange.getAttributes().get(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM);

        String appKey = systemRequestParam.getAppKey();

        if (StringUtils.isAnyBlank(appKey, service) || appKey.length() <= 6) {
            return false;
        }
        return true;
    }


}
