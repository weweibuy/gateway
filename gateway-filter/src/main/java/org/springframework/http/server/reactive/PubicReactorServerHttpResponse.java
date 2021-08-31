package org.springframework.http.server.reactive;

import org.springframework.core.io.buffer.DataBufferFactory;
import reactor.netty.http.server.HttpServerResponse;

/**
 * @author durenhao
 * @date 2021/8/31 15:16
 **/
public class PubicReactorServerHttpResponse extends ReactorServerHttpResponse {

    public PubicReactorServerHttpResponse(HttpServerResponse response, DataBufferFactory bufferFactory) {
        super(response, bufferFactory);
    }

}
