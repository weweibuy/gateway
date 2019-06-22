package com.weweibuy.gateway.manager.mananger;

/**
 * @author durenhao
 * @date 2019/5/18 13:47
 **/
public interface RouterManager {

    /**
     * 删除路由相关的 断言,断言参数,过滤器, 过滤器参数
     *
     * @param id
     */
    void deleteRouter(Long id);

    void deletePredicateById(Long id);

    void deletePredicateByRouterId(String routerId);

    void deleteFilterById(Long id);

    void deleteFiltersByRouterId(String routerId);
}
