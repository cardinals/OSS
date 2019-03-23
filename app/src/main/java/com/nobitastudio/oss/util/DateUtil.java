package com.nobitastudio.oss.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class DateUtil {

    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    public static final String SIMPLE_DATE_FORMAT_2 = "yyyy-MM-dd";

    // 将20190101121212 按照指定格式进行转换
    // 由string  类型时间 格式化为指定格式
    public static String convertDate(String dateStr, String sourceFormat, String targetFormat) {
        if (StringUtils.isBlank(targetFormat)) {
            return null;
        }
        if (StringUtils.isBlank(sourceFormat)) {
            sourceFormat = STANDARD_DATE_FORMAT;
        }
        SimpleDateFormat sourceSdf = new SimpleDateFormat(sourceFormat);
        SimpleDateFormat targetSdf = new SimpleDateFormat(targetFormat);
        try {
            Date sourceDate = sourceSdf.parse(dateStr);
            if (sourceDate == null) {
                return null;
            }
            return targetSdf.format(sourceDate);
        } catch (ParseException ex) {
            return null;
        }
    }

    // 将时间戳转换为指定格式时间
    public static String convertTimeStamp(Timestamp timestamp, String targetFormat) {
        if (StringUtils.isBlank(targetFormat)) {
            return null;
        }
        SimpleDateFormat targetSdf = new SimpleDateFormat(targetFormat);
        Date date = new Date(timestamp.getTime());
        if (date == null) {
            return null;
        }
        return targetSdf.format(date);
    }

    public static Date formatDate(String dateStr, String format) {
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        try {

            SimpleDateFormat targetSdf = new SimpleDateFormat(format);
            return targetSdf.parse(dateStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 格式化 LocalDateTime 为String类型 "yyyyMMdd"，未传入时，返回调用时间
     *
     * @param localDateTime
     * @return
     */
    public static String formatLocalDateTimeToStandardString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DateUtil.STANDARD_DATE_FORMAT));
    }

    /**
     * 格式化 LocalDateTime 为String类型 "yyyyMMdd"，未传入时，返回调用时间
     *
     * @param localDateTime
     * @return
     */
    public static String formatLocalDateTimeToSimpleString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DateUtil.SIMPLE_DATE_FORMAT));
    }

    /**
     * 格式化 LocalDateTime 为String类型 "yyyyMMdd"，未传入时，返回调用时间
     *
     * @param localDateTime
     * @return
     */
    public static String formatLocalDateTimeToSimpleString2(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DateUtil.SIMPLE_DATE_FORMAT_2));
    }

    /**
     * 格式化LocalDateTime 为 Date 类型
     *
     * @param localDateTime
     * @return
     */
    public static Date formatLocalDateTimeToDate(LocalDateTime localDateTime) {
        return new Date(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    public static void main(String[] args) {
        String str = convertDate("2018-07-21", "yyyy-MM-dd", "yyyyMMdd");
        System.out.println(str);

        System.out.println("201701".compareTo("201702"));
        System.out.println("20170101".compareTo("201701"));
        System.out.println("201702".compareTo("20170101"));
    }


}
