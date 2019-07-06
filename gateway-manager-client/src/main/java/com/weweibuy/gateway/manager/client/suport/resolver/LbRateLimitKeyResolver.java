package com.weweibuy.gateway.manager.client.suport.resolver;

import com.weweibuy.gateway.manager.client.utils.RouteToRequestUrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR;

/**
 * 根据服务实例的接口限流;  多台实例,每个服务实例限流相同
 *
 * @author durenhao
 * @date 2019/7/6 9:56
 **/
@Slf4j
@Component
@Primary
public class LbRateLimitKeyResolver implements KeyResolver {

    protected final LoadBalancerClient loadBalancer;

    public LbRateLimitKeyResolver(LoadBalancerClient loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return getRequestUrl(exchange);
    }


    private Mono<String> getRequestUrl(ServerWebExchange exchange) {
        log.info("限流过滤器");
        Optional<URI> lbUri = RouteToRequestUrlUtil.getLbUri(exchange);
        String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
        if (!lbUri.isPresent()
                || (!"lb".equals(lbUri.get().getScheme()) && !"lb".equals(schemePrefix))) {
            return Mono.empty();
        }
        ServiceInstance instance = choose(exchange, lbUri.get());
        URI uri = exchange.getRequest().getURI();
        String overrideScheme = instance.isSecure() ? "https" : "http";
        if (schemePrefix != null) {
            overrideScheme = lbUri.get().getScheme();
        }

        URI requestUrl = loadBalancer.reconstructURI(
                new DelegatingServiceInstance(instance, overrideScheme), uri);
        return Mono.just(requestUrl.toString());
    }


    private ServiceInstance choose(ServerWebExchange exchange, URI uri) {
        return loadBalancer.choose(uri.getHost());
    }

    class DelegatingServiceInstance implements ServiceInstance {

        final ServiceInstance delegate;

        private String overrideScheme;

        DelegatingServiceInstance(ServiceInstance delegate, String overrideScheme) {
            this.delegate = delegate;
            this.overrideScheme = overrideScheme;
        }

        @Override
        public String getServiceId() {
            return delegate.getServiceId();
        }

        @Override
        public String getHost() {
            return delegate.getHost();
        }

        @Override
        public int getPort() {
            return delegate.getPort();
        }

        @Override
        public boolean isSecure() {
            return delegate.isSecure();
        }

        @Override
        public URI getUri() {
            return delegate.getUri();
        }

        @Override
        public Map<String, String> getMetadata() {
            return delegate.getMetadata();
        }

        @Override
        public String getScheme() {
            String scheme = delegate.getScheme();
            if (scheme != null) {
                return scheme;
            }
            return this.overrideScheme;
        }

    }


}
