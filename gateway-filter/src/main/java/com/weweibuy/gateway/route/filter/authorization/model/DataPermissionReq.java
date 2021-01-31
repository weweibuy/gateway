package com.weweibuy.gateway.route.filter.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据级权限请求
 *
 * @author durenhao
 * @date 2021/1/26 21:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataPermissionReq {

    @NotBlank
    private String service;

    @NotBlank
    private String path;

    @NotNull
    private HttpMethod httpMethod;

    @NotBlank
    private String username;




}
