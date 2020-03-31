package mz.co.commandline.grocery.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class FormatterUtilTest {

    @Test
    public void shouldFormatToString() {
        String mtFormated = FormatterUtil.mtFormat(new BigDecimal("1130.50"));
        assertEquals("1,130.5 MT", mtFormated);
    }
}
