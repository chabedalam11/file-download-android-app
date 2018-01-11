package com.example.fajlehrabbi.appmcci.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by User on 8/29/2017.
 */

public class MyTimeUtils {

    /** "Fri 2009-10-09 08:37:22 GMT+0800" // TimeZone is Taipei */
//  private static SimpleDateFormat LocalTimeFormatter = new SimpleDateFormat(
//      "EEE yyyy-MM-dd HH:mm:ss zzz", Locale.US);

    /** "Fri Oct 09 08:37:22 CST 2009" // TimeZone is Taipei, zzz=[GMT+0800 in Android | CST in Java] */
    public SimpleDateFormat LocalTimeFormatter = new SimpleDateFormat(
            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());

    private SimpleDateFormat GMTTimeFormatter = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'");
    {
        GMTTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public  boolean isGMTTimeFormat(String strGMTTime) {
        boolean isValid = false;
        try {
            GMTTimeFormatter.parse(strGMTTime);
            isValid = true;
        } catch (ParseException e) {
        }
        if (strGMTTime.length() != 20)
            isValid = false;
        return isValid;
    }

    public  long getGMTTime(String strGMTTime) {
        long millisecond = 0;
        try {
            millisecond = GMTTimeFormatter.parse(strGMTTime).getTime();
        } catch (ParseException e) {
            if (strGMTTime.length() > 0)
                e.printStackTrace();
            else
                System.err.println(e.getMessage());
        }
        return millisecond;
    }

    public  long getLocalTime(String strLocalTime) {
        long millisecond = 0;
        try {
            LocalTimeFormatter.setTimeZone(TimeZone.getDefault());
            millisecond = LocalTimeFormatter.parse(strLocalTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millisecond;
    }

    public String getLocalTimeString(String strGMTTime) {
        String strLocalTime = null;
        try {
            GMTTimeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = GMTTimeFormatter.parse(strGMTTime);
            LocalTimeFormatter.setTimeZone(TimeZone.getDefault());
            strLocalTime = LocalTimeFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strLocalTime;
    }

    public String getLocalTimeString(long millisecond) {
        LocalTimeFormatter.setTimeZone(TimeZone.getDefault());
        String strLocalTime = LocalTimeFormatter.format(millisecond);
        return strLocalTime;
    }

    public String getGMTTimeString(long millisecond) {
        Calendar cal = Calendar.getInstance();
        int zoneOffsetTime = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
        long millisecondsUTC = millisecond - zoneOffsetTime;
        GMTTimeFormatter.setTimeZone(TimeZone.getDefault());
        String strGMTTime = GMTTimeFormatter.format(millisecondsUTC);
        return strGMTTime;
    }

    public String getTimeString(long millisecond) {
        String timeString = "";

        int totalSeconds = (int) millisecond / 1000;
        int hours = totalSeconds / 3600;
        int remainder = totalSeconds % 3600;
        int minutes = remainder / 60;
        int seconds = remainder % 60;

        if (hours == 0) {
            timeString = minutes + ":" + seconds;
        } else {
            timeString = hours + ":" + minutes + ":" + seconds;
        }
        return timeString;
    }

    public MyTimeUtils() {
    }

}
