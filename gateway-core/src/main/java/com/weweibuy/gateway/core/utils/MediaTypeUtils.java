package com.weweibuy.gateway.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * @author durenhao
 * @date 2020/2/23 19:20
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MediaTypeUtils {


    public static boolean acceptsHtml(ServerWebExchange exchange) {
        try {
            List<MediaType> acceptedMediaTypes = exchange.getRequest().getHeaders().getAccept();
            acceptedMediaTypes.remove(MediaType.ALL);
            MediaType.sortBySpecificityAndQuality(acceptedMediaTypes);
            return acceptedMediaTypes.stream()
                    .anyMatch(MediaType.TEXT_HTML::isCompatibleWith);
        } catch (InvalidMediaTypeException ex) {
            return false;
        }
    }
}
