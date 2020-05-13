package mz.co.commandline.grocery.util;

import android.support.design.widget.TextInputLayout;

public class TextInputLayoutUtil {

    public static String getInpuText(TextInputLayout textInputLayout) {
        return textInputLayout.getEditText().getText().toString();
    }
}
