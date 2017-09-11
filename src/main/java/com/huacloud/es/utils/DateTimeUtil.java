package com.huacloud.es.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author: diaofan
 * @description:
 * @date: created in 16:17 2017/9/11
 */
public class DateTimeUtil {
    public static boolean isTime(String string) {
        boolean result = true;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss+08:00");
        try {
            sdf.setLenient(false);
            sdf.parse(string);
        } catch (ParseException e) {
            result = false;
        }
        return result;
    }
}
