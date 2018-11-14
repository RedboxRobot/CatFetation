package com.unlimiteduniverse.common.utils;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        return sdf.format(new Date(timestamp * 1000));
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

    public static String getAgeByBirthday(long timestamp) {
        return getAgeByBirthday(timestamp2String(timestamp, null));
    }

    /**
     * 调用此方法输入所要转换的字符串输入例如"2014-06-14  16:09:00"输出Date类型的数据
     *
     * @param time
     * @return
     */
    public static Date getDate(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = null;
        try {
            date = sdr.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 根据用户生日Date数据计算年龄
     */
    public static String getAgeByBirthday(String birthTimeString) {
        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.trim().split("-");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = Integer.parseInt(strs[2]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        int month = 0;

        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
                month = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                    month = 0;
                } else if (dayMinus >= 0) {
                    age = 0;
                    month = 1;
                }
            } else if (monthMinus > 0) {
                age = 0;
                month = monthMinus;
            }
        } else if (yearMinus > 0) {
            //17年11月23日
            //18年11月13日
            if (monthMinus < 0) {// 当前月 < 生日月
                age --;
                month = monthNow + (12 - selectMonth);
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                    month = 0;
                } else if (dayMinus >= 0) {
                    month = 1;
                }
            } else if (monthMinus > 0) {
                month = monthMinus;
            }
        }
        if (month == 0) {
            return age + "岁";
        } else {
            return age + "岁" + month + "个月";
        }
    }
}
