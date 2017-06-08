package com.example.newapp.util;

import java.security.PublicKey;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/6/7.
 */

public class DateUtil {
    public static SimpleDateFormat format12 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制
    public static SimpleDateFormat format24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制

    public static String toTime(Long time, SimpleDateFormat format) {
        return format.format(time);
    }
}
