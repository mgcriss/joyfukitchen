package edu.ayd.joyfukitchen.util;


import android.net.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tangtang on 2017/4/10 14:23.
 */

public class DateUtil {

    public static int yearDateDiff(String startDate,String endDate){
        Calendar calBegin = Calendar.getInstance(); //获取日历实例
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(stringTodate(startDate,"yyyy")); //字符串按照指定格式转化为日期
        calEnd.setTime(stringTodate(endDate,"yyyy"));
        return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
    }
    public static Date stringTodate(String dateStr, String formatStr) {
        // 如果时间为空则默认当前时间
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        if (dateStr != null && !dateStr.equals("")) {
            String time = "";
            Date dateTwo = null;
            try {
                dateTwo = format.parse(dateStr);
                time = format.format(dateTwo);
                date = format.parse(time);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

        } else {
            String timeTwo = format.format(new Date());
            try {
                date = format.parse(timeTwo);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}
