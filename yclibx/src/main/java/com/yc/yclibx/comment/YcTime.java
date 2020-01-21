package com.yc.yclibx.comment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间相关帮助类
 */
@SuppressLint("SimpleDateFormat")
public class YcTime {

    public final static String FORMAT_TIME = "yyyy-MM-dd";
    public final static String FORMAT_TIME_YEAR = "yyyy";
    public final static String FORMAT_TIME_MONTH = "yyyy-MM";
    public final static String FORMAT_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将Date类型格式化成String yyyy-MM-dd
     *
     * @param date 时间
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, FORMAT_TIME);
    }

    /**
     * 将Date类型格式化成String yyyy-MM-dd
     *
     * @param date 时间
     * @return
     */
    public static String dateToString(Date date, String formatTime) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat(formatTime).format(date);
        }
    }

    /**
     * 将Calendar类型格式化成String yyyy-MM-dd
     *
     * @param date 时间
     * @return
     */
    public static String calendarToString(Calendar date) {
        return calendarToString(date, FORMAT_TIME);
    }

    public static String calendarToString(Calendar date, String format) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat(format, Locale.getDefault()).format(date.getTime());
        }
    }

    public static Date stringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        Date timeDate = null;
        try {
            timeDate = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeDate;
    }

    public static Calendar stringToCalendar(String time) {
        return stringToCalendar(time, FORMAT_TIME);
    }

    public static Calendar stringToCalendar(String time, String formatTime) {
        if (TextUtils.isEmpty(time)) {
            Log.e("FormatUtils", "String转Calender失败，String为空");
            return Calendar.getInstance();
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(formatTime);
            Date date = df.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            Log.e("FormatUtils", "String转成Calender失败，" + time + "的格式不是" + FORMAT_TIME);
            return Calendar.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return Calendar.getInstance();
        }
    }

    /**
     * 将Date类型格式化成Calendar
     *
     * @param date 时间
     * @return
     */
    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String getMonthFirstDay(Calendar calendar) {
        //获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        return calendarToString(calendar);
    }

    public static String getMonthLastDay(Calendar calendar) {
        //获取某月最小天数
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        return calendarToString(calendar);
    }
}
