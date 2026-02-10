package utils.date;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import utils.log.Log;

public class DateUtils {

    // Different date formats in every place
    public enum DateFormatType {
        TEXT, // Format: Feb 21, 2026
        NUMERIC // Format: 21/02/2026
    }

    public static String dateInDaysAndroidFormat(String days) {
        Log.log(Level.FINE, "Starts: Turns days in date");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(days));
        Log.log(Level.FINE, "Date to format: " + gregorianCalendar.getTime());
        String dateAfterDays = formatInt(gregorianCalendar.get(Calendar.DAY_OF_MONTH))
                + " " + getNameMonth(gregorianCalendar.get(Calendar.MONTH))
                + " " + gregorianCalendar.get(Calendar.YEAR);
        Log.log(Level.FINE, "Date formatted: " + dateAfterDays);
        return dateAfterDays;
    }

    public static String dateInDaysShareRequestFormat(String days) {
        Log.log(Level.FINE, "Starts: Turns days in date fot Share request");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(days));
        Log.log(Level.FINE, "Date to format: " + gregorianCalendar.getTime());
        String dateAfterDays = gregorianCalendar.get(Calendar.YEAR) + "-"
                + "-" + gregorianCalendar.get(Calendar.MONTH) + 1
                + "-" + formatInt(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
        Log.log(Level.FINE, "Date formatted: " + dateAfterDays);
        return dateAfterDays;
    }

    public static String dateInDaysWithServerFormat(String days) {
        Log.log(Level.FINE, "Starts: Turns days in date with server response format");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(days));
        Log.log(Level.FINE, "Date to format: " + gregorianCalendar.getTime());
        String dateAfterDays = gregorianCalendar.get(Calendar.YEAR)
                + "-" + formatInt(gregorianCalendar.get(Calendar.MONTH) + 1)
                + "-" + formatInt(gregorianCalendar.get(Calendar.DAY_OF_MONTH))
                + " 23:59:59";
        Log.log(Level.FINE, "Date formatted: " + dateAfterDays);
        return dateAfterDays;
    }

    public static String formatDate(String days, DateFormatType format) {
        Log.log(Level.FINE, "Starts: Build shortDate string");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(days));
        Log.log(Level.FINE, "Date: " + gregorianCalendar.getTime());
        String date2return = "";
        if (format == DateFormatType.TEXT) {
            date2return = getNameMonth(gregorianCalendar.get(Calendar.MONTH)).substring(0, 3)
                    + " " + gregorianCalendar.get(Calendar.DAY_OF_MONTH)
                    + ", " + gregorianCalendar.get(Calendar.YEAR);
            Log.log(Level.FINE, "Short Date: " + date2return);
        } else if (format == DateFormatType.NUMERIC) {
            date2return = String.format("%02d", gregorianCalendar.get(Calendar.DAY_OF_MONTH)) + "/" +
                    String.format("%02d", gregorianCalendar.get(Calendar.MONTH) + 1) + "/" +
                    gregorianCalendar.get(Calendar.YEAR) + " 23:59";
            Log.log(Level.FINE, "Short Date: " + date2return);
        }
        return date2return;
    }

    private static String getNameMonth(int numMonth) {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        String[] months = dateFormatSymbols.getMonths();
        if (numMonth >= 0 && numMonth <= 11) {
            return months[numMonth];
        } else
            return "";
    }

    private static String formatInt(int dateNumber) {
        String day;
        if (dateNumber < 10) {
            day = "0" + dateNumber;
        } else {
            day = String.valueOf(dateNumber);
        }
        return day;
    }

    public static int minExpirationDate(int a, int b) {
        if (a == 0 && b > 0) {
            return b;
        }
        if (a > 0 && b == 0) {
            return a;
        }
        return a <= b ? a : b;
    }
}
