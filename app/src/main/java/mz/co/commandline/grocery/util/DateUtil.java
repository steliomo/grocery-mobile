package mz.co.commandline.grocery.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public static String getFirstDateOfTheYear() {

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, Calendar.JANUARY);
        instance.set(Calendar.DAY_OF_MONTH, 1);

        return format(instance.getTime(), NORMAL_PATTERN);
    }

    public static String getLastDateOfTheYear() {

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, Calendar.DECEMBER);
        instance.set(Calendar.DAY_OF_MONTH, 31);

        return format(instance.getTime(), NORMAL_PATTERN);
    }

    public static long daysBetween(String startDate, String enDate) {

        Date startDateParsed = DateUtil.parse(startDate, NORMAL_PATTERN);
        Date endDateParsed = DateUtil.parse(enDate, NORMAL_PATTERN);

        if (startDateParsed.after(endDateParsed)) {
            throw new IllegalArgumentException("The start date cannot be after the end date!");
        }

        long diffmillies = Math.abs(endDateParsed.getTime() - startDateParsed.getTime());
        long days = TimeUnit.DAYS.convert(diffmillies, TimeUnit.MILLISECONDS);

        return days;
    }
}
