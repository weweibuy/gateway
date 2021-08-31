package com.weweibuy.gateway.route.filter.support;

import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import io.netty.buffer.ByteBuf;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.PubicReactorServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerResponse;

public class CachedBodyResponse extends PubicReactorServerHttpResponse {

    private ServerWebExchange exchange;

    public CachedBodyResponse(HttpServerResponse response, DataBufferFactory bufferFactory, ServerWebExchange exchange) {
        super(response, bufferFactory);
        this.exchange = exchange;
    }

    @Override
    protected Mono<Void> writeWithInternal(Publisher<? extends DataBuffer> publisher) {
        HttpServerResponse nativeResponse = (HttpServerResponse) super.getNativeResponse();
        return nativeResponse.send(toByteBufs(publisher)).then();
    }

    private Publisher<ByteBuf> toByteBufs(Publisher<? extends DataBuffer> dataBuffers) {
        return DataBufferUtils.join(dataBuffers)
                .doOnNext(dataBuffer -> cacheResponseBody(dataBuffer))
                .map(NettyDataBufferFactory::toByteBuf);
    }

    private void cacheResponseBody(DataBuffer dataBuffer) {
        byte[] content = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(content);
        String responseStr = new String(content);
        exchange.getAttributes()
                .put(ExchangeAttributeConstant.CACHED_RESPONSE_BODY_ATTR, responseStr);
        dataBuffer.readPosition(0);
    }

}