package mz.co.commandline.grocery.validator;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import mz.co.commandline.grocery.R;

public class DefaultValidator implements Validator {

    private TextInputLayout textInputLayout;

    public DefaultValidator(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public boolean isValid() {

        EditText editText = textInputLayout.getEditText();

        if (editText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(textInputLayout.getContext().getResources().getString(R.string.required_field));
            return false;
        }

        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);

        return true;
    }
}
