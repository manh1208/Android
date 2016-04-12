package com.manhnv.fev.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nhattruong on 12/13/2015.
 */
public class DateUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String getCurrentDate(){
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDateTime(){
        Date date = new Date();
        return dateTimeFormat.format(date);
    }

    public static String getDateTimeString(Date date){
        return dateTimeFormat.format(date);
    }
    public static String getDateString(Date date){
        return dateFormat.format(date);
    }
}
