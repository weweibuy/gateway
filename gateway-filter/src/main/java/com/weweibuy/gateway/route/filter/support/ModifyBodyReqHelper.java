package com.weweibuy.gateway.route.filter.support;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author durenhao
 * @date 2021/2/2 19:40
 **/
public class ModifyBodyReqHelper {


    public static ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                                      CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(headers);
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }


    public static Mono<Void> release(ServerWebExchange exchange,
                                 CachedBodyOutputMessage outputMessage, Throwable throwable) {
        if (outputMessage.isCached()) {
            return outputMessage.getBody().map(DataBufferUtils::release)
                    .then(Mono.error(throwable));
        }
        return Mono.error(throwable);
    }

}
