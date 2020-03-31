package mz.co.commandline.grocery.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FormatterUtil {

    public static String mtFormat(BigDecimal value) {
        DecimalFormat formatter = new DecimalFormat("#,###.## MT");
        return formatter.format(value);
    }
}
