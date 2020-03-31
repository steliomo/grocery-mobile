package mz.co.commandline.grocery.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String NORMAL_PATTERN = "dd-MM-yyyy";

    public static final String HOURS_PATTERN = "dd-MM-yyyy HH:mm:ss";

    private static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat(HOURS_PATTERN);
    }

    public static String format(Date date) {
        return dateFormat.format(date);
    }

    public static Date parse(String date) {
        Date finalDate = null;

        try {
            finalDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalDate;
    }

    public static Date parse(String date, String pattern) {
        Date finalDate = null;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            finalDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalDate;
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String format(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = null;
        try {
            Date parse = format.parse(date);
            dateFormatted = format(parse, NORMAL_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateFormatted;
    }
}
