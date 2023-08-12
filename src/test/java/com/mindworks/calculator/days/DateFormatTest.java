package com.mindworks.calculator.days;

import org.junit.jupiter.api.Test;

import static com.mindworks.calculator.days.DateFormat.DD_MM_YYYY;
import static com.mindworks.calculator.days.DateFormat.DD_SLASH_MM_SLASH_YYYY;
import static com.mindworks.calculator.days.DateFormat.MM_DD_YYYY;
import static com.mindworks.calculator.days.DateFormat.MM_SLASH_DD_SLASH_YYYY;
import static com.mindworks.calculator.days.DateFormat.YYYY_MM_DD;
import static com.mindworks.calculator.days.DateFormat.YYYY_SLASH_MM_SLASH_DD;
import static com.mindworks.calculator.days.DateFormat.fromFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateFormatTest {

    @Test
    public void shouldVerifyEnumItems() {
        assertEquals("dd-MM-yyyy", DD_MM_YYYY.getFormat());
        assertEquals("MM-dd-yyyy", MM_DD_YYYY.getFormat());
        assertEquals("yyyy-MM-dd", YYYY_MM_DD.getFormat());
        assertEquals("dd/MM/yyyy", DD_SLASH_MM_SLASH_YYYY.getFormat());
        assertEquals("MM/dd/yyyy", MM_SLASH_DD_SLASH_YYYY.getFormat());
        assertEquals("yyyy/MM/dd", YYYY_SLASH_MM_SLASH_DD.getFormat());

        assertEquals("-", DD_MM_YYYY.getSeparator());
        assertEquals("-", MM_DD_YYYY.getSeparator());
        assertEquals("-", YYYY_MM_DD.getSeparator());
        assertEquals("/", DD_SLASH_MM_SLASH_YYYY.getSeparator());
        assertEquals("/", MM_SLASH_DD_SLASH_YYYY.getSeparator());
        assertEquals("/", YYYY_SLASH_MM_SLASH_DD.getSeparator());

        assertEquals(0, DD_MM_YYYY.getDayOffset());
        assertEquals(1, MM_DD_YYYY.getDayOffset());
        assertEquals(2, YYYY_MM_DD.getDayOffset());
        assertEquals(0, DD_SLASH_MM_SLASH_YYYY.getDayOffset());
        assertEquals(1, MM_SLASH_DD_SLASH_YYYY.getDayOffset());
        assertEquals(2, YYYY_SLASH_MM_SLASH_DD.getDayOffset());

        assertEquals(1, DD_MM_YYYY.getMonthOffset());
        assertEquals(0, MM_DD_YYYY.getMonthOffset());
        assertEquals(1, YYYY_MM_DD.getMonthOffset());
        assertEquals(1, DD_SLASH_MM_SLASH_YYYY.getMonthOffset());
        assertEquals(0, MM_SLASH_DD_SLASH_YYYY.getMonthOffset());
        assertEquals(1, YYYY_SLASH_MM_SLASH_DD.getMonthOffset());

        assertEquals(2, DD_MM_YYYY.getYearOffset());
        assertEquals(2, MM_DD_YYYY.getYearOffset());
        assertEquals(0, YYYY_MM_DD.getYearOffset());
        assertEquals(2, DD_SLASH_MM_SLASH_YYYY.getYearOffset());
        assertEquals(2, MM_SLASH_DD_SLASH_YYYY.getYearOffset());
        assertEquals(0, YYYY_SLASH_MM_SLASH_DD.getYearOffset());
    }

    @Test
    public void shouldVerifyFromFormatMethod() {
        assertEquals(DD_MM_YYYY, fromFormat("dd-MM-yyyy"));
        assertEquals(MM_DD_YYYY, fromFormat("MM-dd-yyyy"));
        assertEquals(YYYY_MM_DD, fromFormat("yyyy-MM-dd"));
        assertEquals(DD_SLASH_MM_SLASH_YYYY, fromFormat("dd/MM/yyyy"));
        assertEquals(MM_SLASH_DD_SLASH_YYYY, fromFormat("MM/dd/yyyy"));
        assertEquals(YYYY_SLASH_MM_SLASH_DD, fromFormat("yyyy/MM/dd"));
    }
}