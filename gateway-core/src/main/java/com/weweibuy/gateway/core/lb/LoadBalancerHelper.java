package com.weweibuy.gateway.core.lb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

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

    public URI toLbUrl(String lb) {


        URI url = null;
        try {
            url = new URI(lb);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
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
