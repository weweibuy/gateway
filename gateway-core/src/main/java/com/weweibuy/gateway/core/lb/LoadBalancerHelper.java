package com.weweibuy.gateway.core.lb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.Response;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author durenhao
 * @date 2020/2/29 12:49
 **/
@Component
public class LoadBalancerHelper {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private LoadBalancerClientFactory clientFactory;


    public URI strToUri(String lb) {
        try {
            return new URI(lb);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }

    }

    public URI toLbUrl(String lb) {
        URI url = strToUri(lb);
        String schemePrefix = url.getScheme();
        if ((!"lb".equals(url.getScheme()) && !"lb".equals(schemePrefix))) {
            return url;
        }
        ServiceInstance choose = loadBalancer.choose(url.getHost());
        if (choose == null) {
            return url;
        }
        String overrideScheme = choose.isSecure() ? "https" : "http";
        if (schemePrefix != null) {
            overrideScheme = url.getScheme();
        }
        return loadBalancer.reconstructURI(
                new LoadBalancerHelper.DelegatingServiceInstance(choose, overrideScheme), url);
    }


    public Mono<URI> choose(String lb) {
        URI uri = strToUri(lb);
        return choose(uri);
    }

    public Mono<URI> choose(URI uri) {
        ReactorLoadBalancer<ServiceInstance> loadBalancer = this.clientFactory
                .getInstance(uri.getHost(), ReactorLoadBalancer.class,
                        ServiceInstance.class);
        if (loadBalancer == null) {
            throw new NotFoundException("无法发现认证服务器: " + uri.getHost());
        }
        return loadBalancer.choose(null)
                .doOnNext(r -> {
                    if (!r.hasServer()) {
                        throw new NotFoundException("无法发现认证服务器: " + uri.getHost());
                    }
                })
                .map(Response::getServer)
                .map(ServiceInstance::getUri);
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
