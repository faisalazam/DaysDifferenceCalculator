package com.mindworks.calculator.days;

import org.junit.Test;

import static com.mindworks.calculator.days.DateFormat.DD_MM_YYYY;
import static com.mindworks.calculator.days.DateFormat.DD_SLASH_MM_SLASH_YYYY;
import static com.mindworks.calculator.days.DateFormat.MM_DD_YYYY;
import static com.mindworks.calculator.days.DateFormat.MM_SLASH_DD_SLASH_YYYY;
import static com.mindworks.calculator.days.DateFormat.YYYY_MM_DD;
import static com.mindworks.calculator.days.DateFormat.YYYY_SLASH_MM_SLASH_DD;
import static com.mindworks.calculator.days.DateFormat.fromFormat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateFormatTest {

    @Test
    public void shouldVerifyEnumItems() {
        assertThat(DD_MM_YYYY.getFormat(), is("dd-MM-yyyy"));
        assertThat(MM_DD_YYYY.getFormat(), is("MM-dd-yyyy"));
        assertThat(YYYY_MM_DD.getFormat(), is("yyyy-MM-dd"));
        assertThat(DD_SLASH_MM_SLASH_YYYY.getFormat(), is("dd/MM/yyyy"));
        assertThat(MM_SLASH_DD_SLASH_YYYY.getFormat(), is("MM/dd/yyyy"));
        assertThat(YYYY_SLASH_MM_SLASH_DD.getFormat(), is("yyyy/MM/dd"));

        assertThat(DD_MM_YYYY.getSeparator(), is("-"));
        assertThat(MM_DD_YYYY.getSeparator(), is("-"));
        assertThat(YYYY_MM_DD.getSeparator(), is("-"));
        assertThat(DD_SLASH_MM_SLASH_YYYY.getSeparator(), is("/"));
        assertThat(MM_SLASH_DD_SLASH_YYYY.getSeparator(), is("/"));
        assertThat(YYYY_SLASH_MM_SLASH_DD.getSeparator(), is("/"));

        assertThat(DD_MM_YYYY.getDayOffset(), is(0));
        assertThat(MM_DD_YYYY.getDayOffset(), is(1));
        assertThat(YYYY_MM_DD.getDayOffset(), is(2));
        assertThat(DD_SLASH_MM_SLASH_YYYY.getDayOffset(), is(0));
        assertThat(MM_SLASH_DD_SLASH_YYYY.getDayOffset(), is(1));
        assertThat(YYYY_SLASH_MM_SLASH_DD.getDayOffset(), is(2));

        assertThat(DD_MM_YYYY.getMonthOffset(), is(1));
        assertThat(MM_DD_YYYY.getMonthOffset(), is(0));
        assertThat(YYYY_MM_DD.getMonthOffset(), is(1));
        assertThat(DD_SLASH_MM_SLASH_YYYY.getMonthOffset(), is(1));
        assertThat(MM_SLASH_DD_SLASH_YYYY.getMonthOffset(), is(0));
        assertThat(YYYY_SLASH_MM_SLASH_DD.getMonthOffset(), is(1));

        assertThat(DD_MM_YYYY.getYearOffset(), is(2));
        assertThat(MM_DD_YYYY.getYearOffset(), is(2));
        assertThat(YYYY_MM_DD.getYearOffset(), is(0));
        assertThat(DD_SLASH_MM_SLASH_YYYY.getYearOffset(), is(2));
        assertThat(MM_SLASH_DD_SLASH_YYYY.getYearOffset(), is(2));
        assertThat(YYYY_SLASH_MM_SLASH_DD.getYearOffset(), is(0));
    }

    @Test
    public void shouldVerifyFromFormatMethod() {
        assertThat(fromFormat("dd-MM-yyyy"), is(DD_MM_YYYY));
        assertThat(fromFormat("MM-dd-yyyy"), is(MM_DD_YYYY));
        assertThat(fromFormat("yyyy-MM-dd"), is(YYYY_MM_DD));
        assertThat(fromFormat("dd/MM/yyyy"), is(DD_SLASH_MM_SLASH_YYYY));
        assertThat(fromFormat("MM/dd/yyyy"), is(MM_SLASH_DD_SLASH_YYYY));
        assertThat(fromFormat("yyyy/MM/dd"), is(YYYY_SLASH_MM_SLASH_DD));
    }
}