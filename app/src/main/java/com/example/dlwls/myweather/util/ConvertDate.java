package com.example.dlwls.myweather.util;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

public class ConvertDate {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd(E) HH:00");
    public  static SimpleDateFormat date = new SimpleDateFormat("dd");
    public static SimpleDateFormat time = new SimpleDateFormat("HH");
    public static String CONVERT_DATE(long time){
        Date d = new Date();
        d.setTime(time * 1000);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return sdf.format(d);
    }
    public static String CONVERT_DAY(long time){
        Date d = new Date();
        d.setTime(time * 1000);
        return date.format(d);
    }

    public static  String CONVERT_TIME(long times){
        Date d = new Date();
        d.setTime(times * 1000);
        return time.format(d);
    }
}
