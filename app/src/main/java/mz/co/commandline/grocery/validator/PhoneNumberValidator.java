package mz.co.commandline.grocery.validator;

import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.util.TextInputLayoutUtil;

public class PhoneNumberValidator implements Validator {

    private TextInputLayout textInputLayout;
    private Validator validator;

    public PhoneNumberValidator(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
        validator = new DefaultValidator(textInputLayout);
    }


    @Override
    public boolean isValid() {

        if (!validator.isValid()) {
            return false;
        }

        String phoneNumber = TextInputLayoutUtil.getInpuText(textInputLayout);

        if (phoneNumber.length() != 9) {
            textInputLayout.setError(textInputLayout.getContext().getResources().getString(R.string.phone_number_cannot_be_less_than_9_digits));
            return false;
        }

        return true;
    }
}
