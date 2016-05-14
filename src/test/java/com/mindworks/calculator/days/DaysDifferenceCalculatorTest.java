package com.mindworks.calculator.days;

import org.junit.Test;

import java.text.ParseException;

import static com.mindworks.calculator.days.DateFormat.DD_SLASH_MM_SLASH_YYYY;
import static com.mindworks.calculator.days.DaysDifferenceCalculator.calculate;
import static com.mindworks.calculator.days.MyDate.parse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DaysDifferenceCalculatorTest {
    @Test
    public void shouldReturnZeroWhenBothOrEitherOfTheDatesAreNull() throws ParseException {
        final MyDate startDate = parse("1979/12/08");
        final MyDate endDate = parse("1979/12/08");

        assertThat(calculate(null, null), is(0L));
        assertThat(calculate(null, endDate), is(0L));
        assertThat(calculate(startDate, null), is(0L));
    }

    @Test
    public void shouldReturnZeroWhenBothStartAndEndDatesHaveSameYearAndMonth() throws ParseException {
        final MyDate startDate = parse("1979/12/08");
        final MyDate endDate = parse("1979/12/08");

        assertThat(calculate(startDate, endDate), is(0L));
        assertThat(calculate(startDate, startDate), is(0L));
        assertThat(calculate(endDate, endDate), is(0L));
    }

    @Test
    public void shouldReturnZeroWhenStartAndEndDatesHaveNoDaysInBetween() throws ParseException {
        assertThat(calculate(parse("1972/11/07"), parse("1972/11/08")), is(0L));
        assertThat(calculate(parse("01/01/1983", DD_SLASH_MM_SLASH_YYYY), parse("02/01/1983", DD_SLASH_MM_SLASH_YYYY)), is(0L));
    }

    @Test
    public void shouldExcludeStartingAndEndingDayAndReturnOneWhenStartAndEndDatesHaveOnlyOneDayInBetween() throws ParseException {
        assertThat(calculate(parse("2001/02/27"), parse("2001/03/01")), is(1L));
        assertThat(calculate(parse("30/10/2015", DD_SLASH_MM_SLASH_YYYY), parse("01/11/2015", DD_SLASH_MM_SLASH_YYYY)), is(1L));
        assertThat(calculate(parse("27/02/2015", DD_SLASH_MM_SLASH_YYYY), parse("01/03/2015", DD_SLASH_MM_SLASH_YYYY)), is(1L));
        assertThat(calculate(parse("01/03/2015", DD_SLASH_MM_SLASH_YYYY), parse("27/02/2015", DD_SLASH_MM_SLASH_YYYY)), is(1L));

        // leap year scenario
        assertThat(calculate(parse("2000/02/28"), parse("2000/03/01")), is(1L));
        assertThat(calculate(parse("2000/01/01"), parse("2000/01/03")), is(1L));
    }

    @Test
    public void shouldExcludeStartingAndEndingDayAndReturnDaysDifferenceWhenStartAndEndDatesHaveMoreThanOneDaysInBetween() throws ParseException {
        assertThat(calculate(parse("1983/06/02"), parse("1983/06/22")), is(19L));
        assertThat(calculate(parse("1983/06/22"), parse("1983/06/02")), is(19L));
        assertThat(calculate(parse("1989/01/03"), parse("1983/08/03")), is(1979L));
        assertThat(calculate(parse("01/01/1901", DD_SLASH_MM_SLASH_YYYY), parse("31/12/1999", DD_SLASH_MM_SLASH_YYYY)), is(36157L));
        assertThat(calculate(parse("01/01/1901", DD_SLASH_MM_SLASH_YYYY), parse("31/12/2999", DD_SLASH_MM_SLASH_YYYY)), is(401400L));
        assertThat(calculate(parse("01/01/1901", DD_SLASH_MM_SLASH_YYYY), parse("31/12/2999", DD_SLASH_MM_SLASH_YYYY)), is(401400L));
        assertThat(calculate(parse("31/12/2999", DD_SLASH_MM_SLASH_YYYY), parse("01/01/1901", DD_SLASH_MM_SLASH_YYYY)), is(401400L));

        // leap year scenarios
        assertThat(calculate(parse("1984/07/04"), parse("1984/12/25")), is(173L));
        assertThat(calculate(parse("1984/12/25"), parse("1984/07/04")), is(173L));
        assertThat(calculate(parse("2000/02/25"), parse("2000/03/03")), is(6L));

        // same dates for non leap year scenario
        assertThat(calculate(parse("2001/02/25"), parse("2001/03/03")), is(5L));
    }
}