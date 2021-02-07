package com.weweibuy.gateway.route.filter.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weweibuy.framework.common.core.exception.Exceptions;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.framework.common.core.utils.BeanCopyUtils;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.support.RouterIdSystemMapping;
import com.weweibuy.gateway.route.filter.authorization.model.AppAuthorizationReq;
import com.weweibuy.gateway.route.filter.authorization.model.AppAuthorizationResp;
import com.weweibuy.gateway.route.filter.authorization.model.AppInfo;
import com.weweibuy.gateway.route.filter.path.ServiceMatchStripPrefixGatewayFilterFactory;
import com.weweibuy.gateway.route.filter.sign.SystemRequestParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

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

    @Autowired
    private RouterIdSystemMapping routerIdSystemMapping;

    public AppAuthenticationGatewayFilterFactory(ObjectMapper objectMapper) {
        super(AppAuthenticationGatewayFilterFactory.Config.class,
                objectMapper.getTypeFactory().constructParametricType(CommonDataResponse.class, AppAuthorizationResp.class));
    }

    @Override
    public void onAuthSuccess(AppAuthorizationResp response, GatewayFilterChain chain, ServerWebExchange exchange) {
        Optional.ofNullable(response)
                .map(AppAuthorizationResp::getAppId)
                .ifPresent(appId -> exchange.getAttributes().put(ExchangeAttributeConstant.USER_ID_ATTR, appId));

        Optional.ofNullable(response)
                .map(r -> BeanCopyUtils.copy(r, AppInfo.class))
                .ifPresent(appInfo -> exchange.getAttributes().put(ExchangeAttributeConstant.APP_INFO_ATTR, appInfo));


    }

    @Override
    protected AppAuthorizationReq authReq(Config config, GatewayFilterChain chain, ServerWebExchange exchange) {

        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);

        String service = routerIdSystemMapping.routerIdToSystem(route.getId())
                .orElseThrow(() -> Exceptions.system("路由id,无法找到系统id"));

        SystemRequestParam systemRequestParam = (SystemRequestParam) exchange.getAttributes().get(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM);

        return new AppAuthorizationReq(service, exchange.getRequest(), systemRequestParam.getAccessToken());
    }

    @Override
    protected boolean preCheck(Config config, GatewayFilterChain chain, ServerWebExchange exchange) {
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);

        String service = routerIdSystemMapping.routerIdToSystem(route.getId())
                .orElseThrow(() -> Exceptions.system("路由id,无法找到系统id"));

        SystemRequestParam systemRequestParam = (SystemRequestParam) exchange.getAttributes().get(ExchangeAttributeConstant.SYSTEM_REQUEST_PARAM);

        String accessToken = systemRequestParam.getAccessToken();

        if (StringUtils.isAnyBlank(accessToken, service) || accessToken.length() <= 6) {
            return false;
        }
        return true;
    }


}
