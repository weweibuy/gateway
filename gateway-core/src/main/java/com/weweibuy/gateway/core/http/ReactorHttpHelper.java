package com.weweibuy.gateway.core.http;

import com.fasterxml.jackson.databind.JavaType;
import com.weweibuy.framework.common.core.utils.JackJsonUtils;
import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

/**
 * https://github.com/reactor/reactor-netty
 *
 * @author durenhao
 * @date 2020/2/24 22:06
 **/
@Slf4j
@Component
public class ReactorHttpHelper {

    @Autowired
    private HttpClient client;

    @Autowired
    private ObjectProvider<List<ViewResolver>> viewResolversProvider;

    @Autowired
    private ServerCodecConfigurer serverCodecConfigurer;

    private static HttpClient httpClient;

    private static List<ViewResolver> viewResolvers;

    private static List<HttpMessageWriter<?>> messageWriters;


    @PostConstruct
    public void init() {
        httpClient = client;
        viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        messageWriters = serverCodecConfigurer.getWriters();
    }

    /**
     * GET 请求  Rest 接口
     *
     * @param url
     * @param queryMap
     * @param returnType
     * @param <T>
     * @return
     */
    public static <T> Mono<ResponseEntity<T>> getForJson(String url, Map<String, String> queryMap, Class<? extends T> returnType) {
        HttpClient.ResponseReceiver<?> send = httpClient
                .get()
                .uri(url + toQueryString(queryMap));
        return responseEntity(send, returnType);
    }

    public static <T> Mono<ResponseEntity<T>> getForJson(String url, Map<String, String> queryMap, JavaType javaType) {
        HttpClient.ResponseReceiver<?> send = httpClient
                .get()
                .uri(url + toQueryString(queryMap));
        return responseEntity(send, javaType);
    }

    /**
     * 处理请求  Rest 接口
     *
     * @param method
     * @param url
     * @param queryMap
     * @param body
     * @param returnType
     * @param <T>
     * @return
     */
    public static <T> Mono<ResponseEntity<T>> executeForJson(HttpMethod method, String url, Map<String, String> queryMap, Object body, Class<? extends T> returnType) {
        HttpClient.ResponseReceiver<?> send = httpClient
                .request(method)
                .uri(url + toQueryString(queryMap))
                .send((req, nettyOutbound) -> {
                    req.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    return nettyOutbound.send(ByteBufFlux.fromString(Mono.just(JackJsonUtils.write(body))));
                });
        return responseEntity(send, returnType);
    }

    public static <T> Mono<ResponseEntity<T>> executeForJson(HttpMethod method, String url, Map<String, String> queryMap, Object body, JavaType javaType) {
        HttpClient.ResponseReceiver<?> send = httpClient
                .request(method)
                .uri(url + toQueryString(queryMap))
                .send((req, nettyOutbound) -> {
                    req.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    return nettyOutbound.send(ByteBufFlux.fromString(Mono.just(JackJsonUtils.write(body))));
                });
        return responseEntity(send, javaType);
    }

    /**
     * 写出响应
     *
     * @param response
     * @param exchange
     * @return
     */
    public static Mono<Void> write(ServerResponse response, ServerWebExchange exchange) {
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
    public static Mono<ServerResponse> buildResponse(HttpStatus status, MediaType contentType, Object body) {
        return ServerResponse.status(status)
                .contentType(contentType)
                .body(fromValue(body));
    }

    /**
     * 根据 contentType 写出响应
     *
     * @param status
     * @param contentType
     * @param body
     * @param exchange
     * @return
     */
    public static Mono<Void> buildAndWrite(HttpStatus status, MediaType contentType, Object body, ServerWebExchange exchange) {
        return buildResponse(status, contentType, body)
                .flatMap(response -> write(response, exchange));
    }

    /**
     * 构建并写出 json 响应
     *
     * @param status
     * @param body
     * @param exchange
     * @return
     */
    public static Mono<Void> buildAndWriteJson(HttpStatus status, Object body, ServerWebExchange exchange) {
        return buildResponse(status, MediaType.APPLICATION_JSON, body)
                .flatMap(response -> write(response, exchange));
    }


    private static Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
        return response.writeTo(exchange, new ResponseContext());
    }


    private static <T> Mono<ResponseEntity<T>> responseEntity(HttpClient.ResponseReceiver<?> send, Class<? extends T> returnType) {
        return send.responseSingle((res, receiver) -> {
            HttpHeaders headers = new HttpHeaders();
            res.responseHeaders().forEach(
                    entry -> headers.add(entry.getKey(), entry.getValue()));
            return receiver.asByteArray()
                    .map(s -> JackJsonUtils.readValue(s, returnType))
                    .map(s -> new ResponseEntity(s, headers, HttpStatus.valueOf(res.status().hashCode())));
        });
    }

    private static <T> Mono<ResponseEntity<T>> responseEntity(HttpClient.ResponseReceiver<?> send, JavaType javaType) {
        return send.responseSingle((res, receiver) -> {
            HttpHeaders headers = new HttpHeaders();
            res.responseHeaders().forEach(
                    entry -> headers.add(entry.getKey(), entry.getValue()));
            return receiver.asByteArray()
                    .map(s -> JackJsonUtils.<T>readValue(s, javaType))
                    .map(s -> new ResponseEntity(s, headers, HttpStatus.valueOf(res.status().hashCode())));
        });
    }


    private static String toQueryString(Map<String, String> map) {
        if (CollectionUtils.isEmpty(map)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("?");
        map.forEach((k, v) -> builder.append(k).append("=").append(v).append("&"));
        return builder.substring(0, builder.length() - 1);
    }

    private static class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return ReactorHttpHelper.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return ReactorHttpHelper.viewResolvers;
        }

    }

}
