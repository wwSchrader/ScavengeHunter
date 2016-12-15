package com.wwschrader.android.scavengehunter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Warren on 12/14/2016.
 * For misc reusable methods
 */

public class Utilities {

    static long convertCalendarStringToLong(String calendarString){
        //convert string to time in milliseconds
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.US);
        try{
            Date d= format.parse(calendarString);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
