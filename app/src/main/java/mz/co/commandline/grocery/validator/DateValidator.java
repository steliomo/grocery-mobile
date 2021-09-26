package mz.co.commandline.grocery.validator;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import android.view.View;

import static mz.co.commandline.grocery.util.DatePickerUtil.setDate;
import static mz.co.commandline.grocery.util.KeyboardUtil.hideKeyboard;

public class DateValidator implements Validator, View.OnClickListener {

    private Context context;

    private TextInputLayout textInputLayout;

    private final Boolean disableFuture;

    private final Boolean disablePast;

    public DateValidator(final Context context, TextInputLayout textInputLayout, Boolean disableFuture, Boolean disablePast) {
        this.context = context;
        this.textInputLayout = textInputLayout;
        this.disableFuture = disableFuture;
        this.disablePast = disablePast;
        this.textInputLayout.getEditText().setOnClickListener(this);
    }

    @Override
    public boolean isValid() {
        Validator validator = new DefaultValidator(textInputLayout);
        return validator.isValid();
    }

    @Override
    public void onClick(View view) {
        hideKeyboard(context, view);
        setDate(context, textInputLayout.getEditText(), disableFuture, disablePast);
    }
}
