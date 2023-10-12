package services;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
}