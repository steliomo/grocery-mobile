package mz.co.commandline.grocery.util;

import com.google.android.material.textfield.TextInputLayout;

public class TextInputLayoutUtil {

    public static String getInpuText(TextInputLayout textInputLayout) {

        String input = textInputLayout.getEditText().getText().toString();

        if (input.isEmpty()) {
            return null;
        }

        return input;
    }
}
