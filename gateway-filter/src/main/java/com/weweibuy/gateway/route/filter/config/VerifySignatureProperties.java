package com.weweibuy.gateway.route.filter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author durenhao
 * @date 2020/2/29 23:21
 **/
@Data
@ConfigurationProperties(prefix = "gw.filter.sign")
public class VerifySignatureProperties {

    /**
     * 请求时间最大 间隔
     */
    private Long timestampIntervalSecond = 12000000L;


}
