package com.niubility.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Describe：
 * @author：hgeson
 * @date：2018-12-19
 */
public class TimeUtils {
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = 0;
        if (date != null) {
            ts = date.getTime();
        }
        return String.valueOf(ts);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = Long.valueOf(s);
        Date date = new Date(lt);
        return simpleDateFormat.format(date);
    }
}
