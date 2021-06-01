package com.liangxiaolin.rdcchat.RDCChat1.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    public static final String dateformat = "yyyy-MM-dd";

    public static String sqlDateToString(java.sql.Date date) {
        DateFormat df = new SimpleDateFormat(dateformat);
        return df.format(date);
    }

    public static String utilDateToString(java.util.Date date) {
        DateFormat df = new SimpleDateFormat(dateformat);
        return df.format(date);
    }

    public static java.sql.Date stringToSqlDate(String date){
        DateFormat df = new SimpleDateFormat(dateformat);
        return java.sql.Date.valueOf(df.format(date));
    }

    public static Date stringToUtilDate(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat(dateformat);

        return df.parse(date);
    }


}
