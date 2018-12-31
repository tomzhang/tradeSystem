package com.bernard.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static String getTimeString() {
        Calendar calendar = Calendar.getInstance();
        int offset = calendar.get(Calendar.ZONE_OFFSET);
        calendar.add(Calendar.MILLISECOND, -offset);
        Date now = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return formatter.format(now);
    }


    public static String getTime() {

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(now);
    }


    public static String getClearTime() {
        //Date now = new Date();

        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //年份减1
        Date clearTime = ca.getTime(); //结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(clearTime);
    }

    public static void main(String[] args) {
        System.out.println(getTimeString());
    }
}
