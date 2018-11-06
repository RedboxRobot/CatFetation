package com.unlimiteduniverse.common.utils;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Irvin
 * on 2017/11/20 0020.
 */

public class TimeUtils {

    /**
     * @param tsStr String的类型必须形如：
     *              yyyy-mm-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错
     * @return Timestamp
     */
    public static Timestamp str2Timestamp(String tsStr) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        try {
            ts = Timestamp.valueOf(tsStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts;
    }

    @SuppressLint("SimpleDateFormat")
    public static long str2TimeSeconds(String str) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date == null ? 0L : date.getTime() / 1000;
    }

    public static String timestamp2String(long timestamp, String format) {
        if (timestamp == 0) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(timestamp));
    }

    @SuppressLint("SimpleDateFormat")
    public static Date str2Data(String str) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
