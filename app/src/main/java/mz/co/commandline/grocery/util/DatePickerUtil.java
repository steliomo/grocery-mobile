package mz.co.commandline.grocery.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

public class DatePickerUtil {

    public static void setDate(final Context context, final EditText editText, Boolean disableFuture, Boolean disablePast) {
        Calendar instance = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = StringUtils.leftPad((dayOfMonth) + "", 2, "0") + "-" +
                        StringUtils.leftPad((month + 1) + "", 2, "0") + "-" +
                        year;

                editText.setText(date);

            }
        }, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH));

        if (disableFuture) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }

        if (disablePast) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }

        datePickerDialog.show();
    }
}
