package mz.co.commandline.grocery.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import static mz.co.commandline.grocery.util.DatePickerUtil.setDate;
import static mz.co.commandline.grocery.util.KeyboardUtil.hideKeyboard;

public class DateValidator implements Validator, View.OnClickListener {

    private Context context;

    private TextInputLayout textInputLayout;

    public DateValidator(final Context context, TextInputLayout textInputLayout) {
        this.context = context;
        this.textInputLayout = textInputLayout;
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
        setDate(context, textInputLayout.getEditText());
    }
}
