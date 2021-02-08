package com.weweibuy.gateway.route.filter.sign;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * @author durenhao
 * @date 2020/2/27 20:06
 **/
@Builder
@Data
public class SystemRequestParam {

    private String path;

    private HttpMethod method;

    private Long timestamp;

    private String nonce;

    private String signature;

    private SignTypeEum signType;

    private String accessToken;

}
