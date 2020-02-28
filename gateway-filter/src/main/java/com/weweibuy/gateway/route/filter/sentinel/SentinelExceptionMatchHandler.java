package com.weweibuy.gateway.route.filter.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.weweibuy.gateway.common.model.dto.CommonCodeJsonResponse;
import com.weweibuy.gateway.core.advice.ExceptionMatchHandler;
import com.weweibuy.gateway.core.http.ReactorHttpHelper;
import com.weweibuy.gateway.core.utils.MediaTypeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author durenhao
 * @date 2020/2/23 18:47
 **/
public class SentinelExceptionMatchHandler implements ExceptionMatchHandler {



    @Override
    public boolean match(ServerWebExchange exchange, Throwable ex) {
        return BlockException.isBlockException(ex);
    }

    @Override
    public Mono<ServerResponse> handler(ServerWebExchange exchange, Throwable ex) {
        if (MediaTypeUtils.acceptsHtml(exchange)) {
            return htmlErrorResponse(ex);
        }
        return ReactorHttpHelper.buildResponse(HttpStatus.TOO_MANY_REQUESTS, MediaType.APPLICATION_JSON_UTF8, CommonCodeJsonResponse.requestLimit());
    }

    private Mono<ServerResponse> htmlErrorResponse(Throwable ex) {
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.TEXT_PLAIN)
                .syncBody("服务暂时不可用: " + ex.getClass().getSimpleName());
    }


}
