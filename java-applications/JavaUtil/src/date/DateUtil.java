package date;

import pack1.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * Title: </p>
 * <p>
 * Description: </p>
 * <p>
 * Copyright: Copyright (c) 2009</p>
 * <p>
 * </p>
 */
public class DateUtil {

    public static void main(String[] args) {
        unixTimeToDate(1397952000);//2015-03-29 03:00:00 EEST: Salim including some contributions
        unixTimeToDate(1428796800);//2015-04-05 03:00:00 EEST latest week of salim NO contributions: zero
    }
    
    
    /*
     it converts from Unix time (in second )to Date
    http://en.wikipedia.org/wiki/Unix_time 
    Param: unixTime: in second
    */

    public static Date unixTimeToDate(long unixTime) {
        //long unixSeconds = 1372339860;
        Date date = new Date(unixTime * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT+3")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        return date;
    }

    public static Date dateTimeToDate(int date, double time) {
        return dateTimeToDate(Calendar.getInstance(), date, time);
    }

    public static Date dateTimeToDate(Calendar cal, int date, double time) {
        int year = date / 10000;
        int remainder = date - (year * 10000);
        int month = remainder / 100;
        int day = remainder - (month * 100);
        int hour = (int) (time / 3600);
        remainder = (int) (time - (hour * 3600));
        int minute = remainder / 60;
        int second = remainder - (minute * 60);
        // we need to add ms at some point
        cal.set(year, month - 1, day, hour, minute, second); // month is zero based
        return cal.getTime();
    }

    public static void elseTest(boolean val) {

        if (val) {
            System.out.println("True");
        } else {
            System.out.println("1-----else");
        }
        System.out.println("2-----else");
    }
}
