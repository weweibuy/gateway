package com.weweibuy.gateway.common.utils;

import com.weweibuy.gateway.common.constant.CommonConstant;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author durenhao
 * @date 2020/2/15 19:29
 **/
public class DateUtils {

    public static String toStringDate(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.format(CommonConstant.DateConstant.STANDARD_DATE_TIME_FORMATTER);
    }

    public static String toDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstant.DateConstant.STANDARD_DATE_TIME_FORMAT_STR);
        return simpleDateFormat.format(date);
    }

    public static String toDateFormat(LocalDateTime date) {
        return date.format(CommonConstant.DateConstant.STANDARD_DATE_TIME_FORMATTER);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime systemTimestampToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    public static long localDateTimeToTimestampMilli(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of(CommonConstant.DateConstant.TIME_OFFSET_ID)).toEpochMilli();
    }

    public static long localDateTimeToTimestampSecond(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.of(CommonConstant.DateConstant.TIME_OFFSET_ID));
    }


    public static long toMils(Long duration, TimeUnit timeUnit) {
        return timeUnit.toMillis(duration);
    }

    public static boolean isCurrentTimeOverInterval(LocalDateTime localDateTime, long interval) {
        return System.currentTimeMillis() - localDateTimeToTimestampMilli(localDateTime) > interval;
    }


}
