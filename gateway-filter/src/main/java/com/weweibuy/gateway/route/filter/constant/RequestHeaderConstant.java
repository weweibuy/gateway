package com.weweibuy.gateway.route.filter.constant;

import com.weweibuy.gateway.route.filter.sign.SignTypeEum;

/**
 * @author durenhao
 * @date 2020/2/23 21:51
 **/
public interface RequestHeaderConstant {

    String X_CA_SIGNATURE = "X-Ca-Signature";

    String X_CA_TIMESTAMP = "X-Ca-Timestamp";

    String X_CA_NONCE = "X-Ca-Nonce";

    String X_CA_CLIENT_ID = "X-Ca-Client-Id";

    /**
     * 签名方式 MD5  或 HMAC-SHA256
     *
     * @see SignTypeEum
     */
    String X_CA_SIGN_TYPE = "X-Ca-Sign-Type";


}
