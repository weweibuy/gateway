package com.weweibuy.gateway.core.mode.event.response;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @author durenhao
 * @date 2020/2/24 20:27
 **/
@Component
public class ResponseWriter {

    private List<ViewResolver> viewResolvers;

    private List<HttpMessageWriter<?>> messageWriters;


    public ResponseWriter(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                          ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.messageWriters = serverCodecConfigurer.getWriters();
    }


    /**
     * 写出响应
     *
     * @param response
     * @param exchange
     * @return
     */
    public Mono<Void> write(ServerResponse response, ServerWebExchange exchange) {
        return writeResponse(response, exchange);
    }

    /**
     * 构建响应
     *
     * @param status
     * @param contentType
     * @param body
     * @return
     */
    public Mono<ServerResponse> buildResponse(HttpStatus status, MediaType contentType, Object body) {
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(fromObject(body));
    }


    public Mono<Void> buildAndWrite(HttpStatus status, MediaType contentType, Object body, ServerWebExchange exchange) {
        return buildResponse(status, contentType, body)
                .flatMap(response -> write(response, exchange));
    }

    public Mono<Void> buildAndWriteJson(HttpStatus status, Object body, ServerWebExchange exchange) {
        return buildResponse(status, MediaType.APPLICATION_JSON_UTF8, body)
                .flatMap(response -> write(response, exchange));
    }


    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
        return response.writeTo(exchange, new ResponseWriter.ResponseContext());
    }

    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return ResponseWriter.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return ResponseWriter.this.viewResolvers;
        }

    }

}
