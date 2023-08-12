package com.mindworks.calculator.days;

import org.junit.jupiter.api.Test;

import static com.mindworks.calculator.days.Month.APRIL;
import static com.mindworks.calculator.days.Month.AUGUST;
import static com.mindworks.calculator.days.Month.DECEMBER;
import static com.mindworks.calculator.days.Month.FEBRUARY;
import static com.mindworks.calculator.days.Month.JANUARY;
import static com.mindworks.calculator.days.Month.JULY;
import static com.mindworks.calculator.days.Month.JUNE;
import static com.mindworks.calculator.days.Month.MARCH;
import static com.mindworks.calculator.days.Month.MAY;
import static com.mindworks.calculator.days.Month.NOVEMBER;
import static com.mindworks.calculator.days.Month.OCTOBER;
import static com.mindworks.calculator.days.Month.SEPTEMBER;
import static com.mindworks.calculator.days.Month.UNKNOWN_OR_INVALID;
import static com.mindworks.calculator.days.Month.fromNumber;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MonthTest {

    @Test
    public void shouldVerifyEnumItems() {
        assertThat(JANUARY.getDays(), is(31));
        assertThat(JANUARY.getMonthNumber(), is(1));

        assertThat(FEBRUARY.getDays(), is(28));
        assertThat(FEBRUARY.getMonthNumber(), is(2));

        assertThat(MARCH.getDays(), is(31));
        assertThat(MARCH.getMonthNumber(), is(3));

        assertThat(APRIL.getDays(), is(30));
        assertThat(APRIL.getMonthNumber(), is(4));

        assertThat(MAY.getDays(), is(31));
        assertThat(MAY.getMonthNumber(), is(5));

        assertThat(JUNE.getDays(), is(30));
        assertThat(JUNE.getMonthNumber(), is(6));

        assertThat(JULY.getDays(), is(31));
        assertThat(JULY.getMonthNumber(), is(7));

        assertThat(AUGUST.getDays(), is(31));
        assertThat(AUGUST.getMonthNumber(), is(8));

        assertThat(SEPTEMBER.getDays(), is(30));
        assertThat(SEPTEMBER.getMonthNumber(), is(9));

        assertThat(OCTOBER.getDays(), is(31));
        assertThat(OCTOBER.getMonthNumber(), is(10));

        assertThat(NOVEMBER.getDays(), is(30));
        assertThat(NOVEMBER.getMonthNumber(), is(11));

        assertThat(DECEMBER.getDays(), is(31));
        assertThat(DECEMBER.getMonthNumber(), is(12));

        assertThat(UNKNOWN_OR_INVALID.getDays(), is(0));
        assertThat(UNKNOWN_OR_INVALID.getMonthNumber(), is(0));
    }


    @Test
    public void shouldBeTrueWhenTheMonthInDateIsAfterFebruary() {
        assertThat(JANUARY.isAfterFebruary(), is(false));
        assertThat(FEBRUARY.isAfterFebruary(), is(false));
        assertThat(MARCH.isAfterFebruary(), is(true));
        assertThat(APRIL.isAfterFebruary(), is(true));
        assertThat(MAY.isAfterFebruary(), is(true));
        assertThat(JUNE.isAfterFebruary(), is(true));
        assertThat(JULY.isAfterFebruary(), is(true));
        assertThat(AUGUST.isAfterFebruary(), is(true));
        assertThat(SEPTEMBER.isAfterFebruary(), is(true));
        assertThat(OCTOBER.isAfterFebruary(), is(true));
        assertThat(NOVEMBER.isAfterFebruary(), is(true));
        assertThat(DECEMBER.isAfterFebruary(), is(true));
    }

    @Test
    public void shouldVerifyDaysUptoMethod() {
        assertThat(JANUARY.getDaysUpto(), is(0));
        assertThat(FEBRUARY.getDaysUpto(), is(31));
        assertThat(MARCH.getDaysUpto(), is(59));
        assertThat(APRIL.getDaysUpto(), is(90));
        assertThat(MAY.getDaysUpto(), is(120));
        assertThat(JUNE.getDaysUpto(), is(151));
        assertThat(JULY.getDaysUpto(), is(181));
        assertThat(AUGUST.getDaysUpto(), is(212));
        assertThat(SEPTEMBER.getDaysUpto(), is(243));
        assertThat(OCTOBER.getDaysUpto(), is(273));
        assertThat(NOVEMBER.getDaysUpto(), is(304));
        assertThat(DECEMBER.getDaysUpto(), is(334));
    }

    @Test
    public void shouldVerifyFromNumberMethod() {
        assertThat(fromNumber(1), is(JANUARY));
        assertThat(fromNumber(2), is(FEBRUARY));
        assertThat(fromNumber(3), is(MARCH));
        assertThat(fromNumber(4), is(APRIL));
        assertThat(fromNumber(5), is(MAY));
        assertThat(fromNumber(6), is(JUNE));
        assertThat(fromNumber(7), is(JULY));
        assertThat(fromNumber(8), is(AUGUST));
        assertThat(fromNumber(9), is(SEPTEMBER));
        assertThat(fromNumber(10), is(OCTOBER));
        assertThat(fromNumber(11), is(NOVEMBER));
        assertThat(fromNumber(12), is(DECEMBER));

        assertThat(fromNumber(-1), is(UNKNOWN_OR_INVALID));
        assertThat(fromNumber(0), is(UNKNOWN_OR_INVALID));
        assertThat(fromNumber(13), is(UNKNOWN_OR_INVALID));
    }
}