package com.github.snowdream.gvi.lib.util;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yanghui.yangh on 2016/4/19.
 */
public final class TimeUtil {
    private static final SimpleDateFormat mFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z",
                                                                              Locale.ENGLISH);

    public static final Date StringToDate(@NonNull String time) throws ParseException {
        return mFormater.parse(time);
    }

    public static final long StringToLong(@NonNull String time) throws ParseException {
        Date date = StringToDate(time);
        return date.getTime();
    }

    public static final String Date2String(@NonNull  Date date) {
        return  mFormater.format(date);
    }

    public static final String LongToString(@NonNull long time) throws ParseException {
        Date date = new Date(time);
        return Date2String(date);
    }
}
