package mz.co.commandline.grocery.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateUtilTest {

    @Test
    public void shouldFindDaysBetweenDates() {
        long days = DateUtil.daysBetween("19-07-2021", "20-07-2021");
        assertEquals(1, days);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindDaysBetweenDatesWhereStartDateIsAfterEndDate() {
        long days = DateUtil.daysBetween("20-07-2021", "19-07-2021");
        assertEquals(1, days);
    }
}
