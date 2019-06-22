package com.weweibuy.gateway.manager.model.eum;

import com.weweibuy.gateway.common.model.ResponseCodeAndMsg;
import com.weweibuy.gateway.manager.model.constant.SystemCodePrefix;

/**
 * @author durenhao
 * @date 2019/5/19 15:26
 **/
public enum GatewayManagerErrorCode implements ResponseCodeAndMsg {

    ROUTER_EXISTED("001001", "路由已经存在"),
    ROUTER_NOT_EXISTED("001002", "路由不存在"),

    PREDICATE_ADD_FAIL("002001", "断言新增失败"),
    PREDICATE_NOT_EXISTED("002002", "断言不存在,操作失败"),

    PREDICATE_ARGS_ADD_FAIL("003001", "断言参数新增失败"),
    PREDICATE_ARGS_NOT_EXISTED("003002", "断言参数不存在,操作失败"),

    FILTER_ADD_FAIL("004001", "过滤器新增失败"),
    FILTER_NOT_EXISTED("004002", "过滤器不存在,操作失败"),

    FILTER_ARGS_ADD_FAIL("005001", "过滤器参数新增失败"),
    FILTER_ARGS_NOT_EXISTED("005002", "过滤器参数不存在,操作失败"),

    DICT_ADD_FAIL("006001", "字典新增失败"),
    DICT_NOT_EXISTED("006002", "字典不存在,操作失败"),
    DICT_CHILD_EXISTED_CANNOT_DELETE("006003", "存在子节点,不允许删除"),
    DICT_IN_USED_CANNOT_DELETE("006004", "字典正在使用,不允许删除"),

    ACCESS_SYSTEM_ADD_FAIL("007001", "接入系统新增失败"),
    ACCESS_SYSTEM_NOT_EXISTED("007002", "接入系统不存在,操作失败"),
    ROUTER_EXISTED_CANNOT_DELETE_ACCESS_SYSTEM("007003", "接入系统存在路由,不能删除"),



    OBJECT_CONVERT_ERROR("101001", "对象值转化错误"),

    ;


    private String code;

    private String msg;


    GatewayManagerErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return SystemCodePrefix.SYSTEM_CODE_PREFIX + this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
