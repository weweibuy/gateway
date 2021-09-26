package com.weweibuy.gateway.route.filter.log;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;

/**
 * 日志的一些参数
 *
 * @author durenhao
 * @date 2021/9/1 15:49
 **/
@Data
public class LogParam {

    private String username;

    private String trace;

    private String ip;

    private HttpMethod httpMethod;

    private Integer httpStatus;

    private String path;

    private String host;

    private String param;

    private String systemId;

    private String routerToUri;

    private LocalDateTime reqTime;

    private String req;

    private String resp;


}
