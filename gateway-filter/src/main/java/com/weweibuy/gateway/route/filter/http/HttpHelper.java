package com.weweibuy.gateway.route.filter.http;

import com.weweibuy.gateway.core.mode.event.utils.JackJsonUtils;
import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * https://github.com/reactor/reactor-netty
 *
 * @author durenhao
 * @date 2020/2/24 22:06
 **/
@Slf4j
@Component
public class HttpHelper {

    private static HttpClient httpClient;

    @Autowired
    private HttpClient client;

    @PostConstruct
    public void init() {
        httpClient = client;
    }

    public static <T> Mono<ResponseEntity<T>> getForJson(String url, Map<String, String> queryMap, Class<? extends T> returnType) {
        HttpClient.ResponseReceiver<?> send = httpClient
                .get()
                .uri(url + toQueryString(queryMap));
        return responseEntity(send, returnType);
    }


    public static <T> Mono<ResponseEntity<T>> executeForJson(HttpMethod method, String url, Map<String, String> queryMap, Object body, Class<? extends T> returnType) {
        HttpClient.ResponseReceiver<?> send = httpClient
                .request(method)
                .uri(url + toQueryString(queryMap))
                .send((req, nettyOutbound) -> {
                    req.addHeader("Context-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
                    return nettyOutbound.send(ByteBufFlux.fromString(Mono.just(JackJsonUtils.write(body))));
                });
        return responseEntity(send, returnType);
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

    private static String toQueryString(Map<String, String> map) {
        if (CollectionUtils.isEmpty(map)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("?");
        map.forEach((k, v) -> builder.append(k).append("=").append(v).append("&"));
        return builder.substring(0, builder.length() - 1);
    }


}
