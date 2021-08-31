package com.weweibuy.gateway.core.constant;

/**
 * 请求属性常量
 *
 * @author durenhao
 * @date 2019/7/7 11:18
 **/
public interface ExchangeAttributeConstant {

    /**
     * 请求ip
     */
    String REQUEST_IP_ATTR = "request_ip";

    /**
     * LB url
     */
    String LB_URL_ATTR = "lb_url";

    /**
     *
     */
    String SERVICE_URL_ATTR = "service_url";

    /**
     * 请求地址
     */
    String REQUEST_URL_ATTR = "request_url";

    /**
     * App信息
     */
    String APP_INFO_ATTR = "app_info";

    String SING_TIMESTAMP_ATTR = "sing_timestamp";

    String SING_NONCE_ATTR = "sing_nonce";

    String SING_SIGNATURE_ATTR = "sing_signature";

    String SING_SIGN_TYPE_ATTR = "sing_sign_type";

    String SYSTEM_REQUEST_PARAM = "system_request_param";

    /**
     * app_name
     */
    String APP_NAME_ATTR = "app_name";

    /**
     * trace_id
     */
    String TRACE_ID_ATTR = "trace_id";

    String USER_ID_ATTR = "user_id";

    String REQUEST_TIMESTAMP = "request_timestamp";

    /**
     * 缓存响应 body string
     */
    String CACHED_RESPONSE_BODY_ATTR = "cached_response_body_attr";

    /**
     * 输出OpLog
     */
    String OP_LOG_OUTPUT_ATTR = "op_log_output_attr";



}
