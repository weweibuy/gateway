package com.weweibuy.gateway.route.filter.authorization;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weweibuy.framework.common.core.exception.Exceptions;
import com.weweibuy.framework.common.core.model.dto.CommonDataResponse;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import com.weweibuy.gateway.core.lb.LoadBalancerHelper;
import com.weweibuy.gateway.core.support.RouterIdSystemMapping;
import com.weweibuy.gateway.route.filter.authorization.model.UserAuthorizationReq;
import com.weweibuy.gateway.route.filter.authorization.model.UserAuthorizationResp;
import com.weweibuy.gateway.route.filter.path.ServiceMatchStripPrefixGatewayFilterFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * 用户权限控制
 * 依赖{@link ServiceMatchStripPrefixGatewayFilterFactory} 设置请求服务
 *
 * @author durenhao
 * @date 2020/12/26 11:25
 **/
@Component
public class UserAuthenticationGatewayFilterFactory extends AbstractAuthGatewayFilterFactory<AbstractAuthGatewayFilterFactory.Config, UserAuthorizationReq, UserAuthorizationResp> {

    private JavaType authorizationRespType;

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    @Autowired
    private RouterIdSystemMapping routerIdSystemMapping;

    public UserAuthenticationGatewayFilterFactory(ObjectMapper objectMapper) {
        super(UserAuthenticationGatewayFilterFactory.Config.class, objectMapper.getTypeFactory()
                .constructParametricType(CommonDataResponse.class, UserAuthorizationResp.class));
    }


    @Override
    protected UserAuthorizationReq authReq(Config config, GatewayFilterChain chain, ServerWebExchange exchange) {

        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);

        String service = routerIdSystemMapping.routerIdToSystem(route.getId())
                .orElseThrow(() -> Exceptions.system("路由id,无法找到系统id"));


        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        HttpMethod method = request.getMethod();
        // token
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);

        URI uri = (URI) exchange.getAttributes().get(GATEWAY_REQUEST_URL_ATTR);

        return new UserAuthorizationReq(service, uri.getPath(), method, authorization);
    }

    @Override
    protected boolean preCheck(Config config, GatewayFilterChain chain, ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();

        // token
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);

        URI uri = Optional.ofNullable(exchange.getAttributes())
                // 鉴权请求传递 真实请求路径
                .map(m -> (URI) m.get(GATEWAY_REQUEST_URL_ATTR))
                .orElse(null);

        if (StringUtils.isBlank(authorization) || authorization.length() <= 6 || uri == null) {
            return false;
        }
        return true;
    }


    @Override
    public void onAuthSuccess(UserAuthorizationResp response, GatewayFilterChain chain, ServerWebExchange exchange) {
        Optional.ofNullable(response)
                .map(UserAuthorizationResp::getUsername)
                .ifPresent(user -> exchange.getAttributes().put(ExchangeAttributeConstant.USER_ID_ATTR, user));
    }


}
