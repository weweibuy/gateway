package com.weweibuy.gateway.router.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpLogConfig {
    /**
     * id
     */
    private Long id;

    /**
     * 请求Host
     */
    private String reqHost;

    /**
     * 请求Path(支持路径匹配)
     */
    private String reqPath;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}