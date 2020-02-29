package com.weweibuy.gateway.route.filter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author durenhao
 * @date 2020/2/29 13:49
 **/
@Data
@ConfigurationProperties(prefix = "gw.filter.auth")
public class AuthenticationProperties {

    private String authUrl = "lb://webuy-auth/gw/authorize";

}

