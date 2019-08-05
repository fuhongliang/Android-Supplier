package cn.ifhu.supplier.utils;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author tommy
 * @date 18/1/2
 */

public class DateUtil {
    public static final String YEAR_FORMAT = "yyyy";
    public static final String YEAR_MONTH_FORMAT = "yyyy-MM";
    public static final String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_MINUTE = "yyyy-MM-dd hh:mm";

    public static String getCurDateYear(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(YEAR_FORMAT);
    }

    public static String getCurDateYear() {
        DateTime dateTime = new DateTime(new Date());
        return dateTime.toString(YEAR_FORMAT);
    }

    public static String getCurDateMonth() {
        DateTime dateTime = new DateTime(new Date());
        return dateTime.toString(YEAR_MONTH_FORMAT);
    }

    public static String getCurDateMonth(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(YEAR_MONTH_FORMAT);
    }

    public static String getDateToString(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(YEAR_MONTH_DAY_MINUTE);
    }

    public static String getLongToStringMinute(Long date) {
        DateTime dateTime = new DateTime(date * 1000L);
        return dateTime.toString(YEAR_MONTH_DAY_MINUTE);
    }

    public static String getLongToString(Long date) {
        DateTime dateTime = new DateTime(date * 1000L);
        return dateTime.toString(YEAR_MONTH_DAY_FORMAT);
    }


    public static boolean isRecentDay(long time) {
        Calendar meetingTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        meetingTime.setTimeInMillis(time);
        Calendar currentDay = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        long currentTime = System.currentTimeMillis();
        currentDay.setTimeInMillis(currentTime);

        if (meetingTime.getTimeInMillis() < currentDay.getTimeInMillis()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        return stampToDate(Long.valueOf(s), " ");
    }

    /*
     * 将时间戳转换为时间
     * 单位秒
     */
    public static String stampToDate(long s, String join){
        String res;
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd"+join+"HH:mm:ss");
        Date date = new Date(s*1000);
        res = simpleDateFormat.format(date);
        return res;
    }
}
