package com.weweibuy.gateway.route.filter.path;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * 匹配服务名称 并忽略1层前缀
 *
 * @author durenhao
 * @date 2020/2/23 14:40
 **/
@Component
public class ServiceMatchStripPrefixGatewayFilterFactory extends AbstractGatewayFilterFactory<ServiceMatchStripPrefixGatewayFilterFactory.Config> {


    /**
     * Parts key.
     */
    public static final String PARTS_KEY = "parts";

    public ServiceMatchStripPrefixGatewayFilterFactory() {
        super(ServiceMatchStripPrefixGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARTS_KEY);
    }

    @Override
    public GatewayFilter apply(ServiceMatchStripPrefixGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            addOriginalRequestUrl(exchange, request.getURI());
            String path = request.getURI().getRawPath();

            String[] array = StringUtils.tokenizeToStringArray(path, "/");
            if (array.length > 0 && StringUtils.hasLength(array[0])) {
            }

            String newPath = "/"
                    + Arrays.stream(array)
                    .skip(config.parts).collect(Collectors.joining("/"));

            newPath += (newPath.length() > 1 && path.endsWith("/") ? "/" : "");
            ServerHttpRequest newRequest = request.mutate().path(newPath).build();

            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }

    @Data
    public static class Config {

        /**
         * 匹配层数
         */
        private int parts;

    }

}
