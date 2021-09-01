package com.weweibuy.gateway.route.filter.log;

import com.weweibuy.framework.common.core.utils.JackJsonUtils;
import com.weweibuy.gateway.core.constant.ExchangeAttributeConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author durenhao
 * @date 2021/8/31 22:48
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpLogLogger {


    /**
     * 输出操作日志
     *
     * @param logParam
     * @param exchange
     */
    public static void opLog(LogParam logParam, ServerWebExchange exchange) {

        String responseBodyStr = Optional.ofNullable((String) exchange.getAttribute(ExchangeAttributeConstant.CACHED_RESPONSE_BODY_ATTR))
                .orElse(StringUtils.EMPTY);

        String body = Optional.ofNullable((DataBuffer) exchange.getAttributes().get(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR))
                .map(DataBuffer::asByteBuffer)
                .map(StandardCharsets.UTF_8::decode)
                .map(Object::toString)
                .orElse(StringUtils.EMPTY);
        log.info("操作日志: {}, req: {}, resp: {}", JackJsonUtils.writeCamelCase(logParam), body, responseBodyStr);
    }


}
