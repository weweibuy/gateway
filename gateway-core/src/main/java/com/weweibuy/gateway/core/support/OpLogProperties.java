package com.weweibuy.gateway.core.support;

import com.weweibuy.framework.common.core.utils.HttpRequestUtils;
import lombok.Data;

import java.util.*;

/**
 * 操作日志配置
 *
 * @author durenhao
 * @date 2021/8/31 17:32
 **/
@Data
public class OpLogProperties {

    /**
     * HOST 下精确配置路径
     */
    private Map<String, Set<String>> hostExactPathSetMap = Collections.emptyMap();

    /**
     * Host 下匹配型路径
     */
    private Map<String, List<String>> hostPatternPathListMap = Collections.emptyMap();


    public boolean match(String host, String path) {
        return Optional.ofNullable(hostExactPathSetMap.get(host))
                .map(s -> s.contains(path))
                .orElseGet(() -> Optional.ofNullable(hostPatternPathListMap.get(host))
                        .map(l -> l.stream().anyMatch(s ->
                                HttpRequestUtils.isMatchPath(s, path)))
                        .orElse(false));
    }

}
