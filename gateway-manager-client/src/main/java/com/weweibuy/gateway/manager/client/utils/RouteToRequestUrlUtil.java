package com.weweibuy.gateway.manager.client.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.containsEncodedParts;

/**
 * @author durenhao
 * @date 2019/7/6 12:07
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RouteToRequestUrlUtil {

    private static final String SCHEME_REGEX = "[a-zA-Z]([a-zA-Z]|\\d|\\+|\\.|-)*:.*";

    static final Pattern schemePattern = Pattern.compile(SCHEME_REGEX);


    public static Optional<URI> getRequestUri(ServerWebExchange exchange) {
        return Optional.ofNullable(exchange.getRequest().getURI());
    }

    public static Optional<URI> getLbUri(ServerWebExchange exchange) {
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        if (route == null) {
            return Optional.empty();
        }
        URI uri = exchange.getRequest().getURI();
        boolean encoded = containsEncodedParts(uri);
        URI routeUri = route.getUri();

        if (hasAnotherScheme(routeUri)) {
            routeUri = URI.create(routeUri.getSchemeSpecificPart());
        }

        if ("lb".equalsIgnoreCase(routeUri.getScheme()) && routeUri.getHost() == null) {
            throw new IllegalStateException("Invalid host: " + routeUri.toString());
        }

        URI mergedUrl = UriComponentsBuilder.fromUri(uri)
                // .uri(routeUri)
                .scheme(routeUri.getScheme()).host(routeUri.getHost())
                .port(routeUri.getPort()).build(encoded).toUri();
        return Optional.ofNullable(mergedUrl);
    }


    static boolean hasAnotherScheme(URI uri) {
        return schemePattern.matcher(uri.getSchemeSpecificPart()).matches()
                && uri.getHost() == null && uri.getRawPath() == null;
    }
}
