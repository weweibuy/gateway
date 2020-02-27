package com.weweibuy.gateway.common.constant;

import java.time.format.DateTimeFormatter;

/**
 * @author durenhao
 * @date 2020/2/27 20:56
 **/
public interface CommonConstant {


    public interface DateConstant{

        String TIME_OFFSET_ID = "+8";

        String STANDARD_DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

        DateTimeFormatter STANDARD_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(STANDARD_DATE_TIME_FORMAT_STR);

    }


    public interface CharsetConstant{

          String UTF8_STR = "UTF-8";


    }

    public interface SignConstant{

        public static final String HMAC_SHA256 = "HmacSHA256";


    }


}
