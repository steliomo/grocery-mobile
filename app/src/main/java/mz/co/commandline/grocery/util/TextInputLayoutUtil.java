package mz.co.commandline.grocery.util;

import com.google.android.material.textfield.TextInputLayout;

public class TextInputLayoutUtil {

    public static String getInpuText(TextInputLayout textInputLayout) {
        return textInputLayout.getEditText().getText().toString();
    }
}
