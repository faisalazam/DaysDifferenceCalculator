package com.mindworks.calculator.days;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.mindworks.calculator.days.DateFormat.DD_MM_YYYY;
import static com.mindworks.calculator.days.DateFormat.DD_SLASH_MM_SLASH_YYYY;
import static com.mindworks.calculator.days.DateFormat.MM_DD_YYYY;
import static com.mindworks.calculator.days.DateFormat.MM_SLASH_DD_SLASH_YYYY;
import static com.mindworks.calculator.days.DateFormat.YYYY_MM_DD;
import static com.mindworks.calculator.days.DateFormat.YYYY_SLASH_MM_SLASH_DD;
import static com.mindworks.calculator.days.Month.UNKNOWN_OR_INVALID;
import static com.mindworks.calculator.days.Month.fromNumber;
import static com.mindworks.calculator.days.MyDate.FIRST_VALID_DATE;
import static com.mindworks.calculator.days.MyDate.LAST_VALID_DATE;
import static com.mindworks.calculator.days.MyDate.convert;
import static com.mindworks.calculator.days.MyDate.parse;
import static com.mindworks.calculator.days.ReflectionHelper.invokeStaticPrivateMethod;
import static com.mindworks.calculator.days.TestUtils.randomInt;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class MyDateTest {

    @Test
    public void shouldParseSuccessfullyWithDefaultDateFormatWhenDateStringIsBetween19010101And29991231() {
        final List<String> validDateStrings = new ArrayList<>();
        validDateStrings.add("1901/01/01"); //first possible valid date
        validDateStrings.add("1901/01/02");
        validDateStrings.add("1945/11/21");
        validDateStrings.add("2000/09/14");
        validDateStrings.add("2049/04/19");
        validDateStrings.add("2345/011/21");
        validDateStrings.add("2345/3/021");
        validDateStrings.add("2999/12/30");
        validDateStrings.add("2999/12/31"); //last possible valid date

        verifyDateStringIsParsedSuccessfully(validDateStrings);
    }

    @Test
    public void shouldParseSuccessfullyWithSpecifiedDateFormatWhenDateStringIsBetween19010101And29991231() {
        final List<Pair<String, DateFormatable>> validDateStrings = new ArrayList<>();
        validDateStrings.add(new Pair<>("15-12-1901", DD_MM_YYYY));
        validDateStrings.add(new Pair<>("11-21-1945", MM_DD_YYYY));
        validDateStrings.add(new Pair<>("2000-09-14", YYYY_MM_DD));
        validDateStrings.add(new Pair<>("19/12/2049", DD_SLASH_MM_SLASH_YYYY));
        validDateStrings.add(new Pair<>("03/21/2345", MM_SLASH_DD_SLASH_YYYY));
        validDateStrings.add(new Pair<>("2999/12/30", YYYY_SLASH_MM_SLASH_DD));

        verifyDateStringIsParsedSuccessfullyWithSpecifiedFormat(validDateStrings);
    }

    @Test
    public void shouldThrowParseExceptionWhileParsingWhenDateIsNotBetween19010101And29991231() {
        final List<String> outOfRangeDateStrings = new ArrayList<>();
        outOfRangeDateStrings.add("1900/12/31");
        outOfRangeDateStrings.add("1845/11/21");
        outOfRangeDateStrings.add("3000/01/01");
        outOfRangeDateStrings.add("3333/11/21");

        verifyInvalidDateStringThrowsParseException(outOfRangeDateStrings);
    }

    @Test
    public void shouldThrowParseExceptionWhileParsingWhenSpecifiedDateStringIsInvalid() {
        final List<String> invalidDateStrings = new ArrayList<>();
        invalidDateStrings.add("");
        invalidDateStrings.add(null);
        invalidDateStrings.add("2222/11");
        invalidDateStrings.add("222211/12");
        invalidDateStrings.add("2222/11-12");
        invalidDateStrings.add("2345/01/41");
        invalidDateStrings.add("2345/01/41");
        invalidDateStrings.add("235/012/25");
        invalidDateStrings.add("2345/g1/11");
        invalidDateStrings.add("2345/11/h1");
        invalidDateStrings.add("drdr5566fg");

        verifyInvalidDateStringThrowsParseException(invalidDateStrings);
    }

    @Test
    public void shouldThrowParseExceptionWhenYearAndDayAreValidButMonthIsEitherLessThan1OrGreaterThan12() {
        final List<String> invalidDateStrings = new ArrayList<>();
        invalidDateStrings.add("1922/-1/31");
        invalidDateStrings.add("2045/00/21");
        invalidDateStrings.add("2777/13/1");

        verifyInvalidDateStringThrowsParseException(invalidDateStrings);
    }

    @Test
    public void shouldThrowParseExceptionWhenYearAndMonthAreValidButDayIsEitherLessThan1OrNotWithinMonthlyRange() throws Exception {
        for (int year = 1901; year < 3000; year++) {  //looping through all valid years to consider both leap and non leap years
            for (Month month : getValidMonths()) {
                final List<String> invalidDateStrings = new ArrayList<>();
                invalidDateStrings.add(format("%d/%02d/%02d", randomValidYear(), month.getMonthNumber(), -1));
                invalidDateStrings.add(format("%d/%02d/%02d", randomValidYear(), month.getMonthNumber(), 0));

                final boolean isLeapYear = (boolean) invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, year);
                final int day = month.getDays() + ((isLeapYear && month.getMonthNumber() == 2) ? 2 : 1);

                invalidDateStrings.add(format("%d/%02d/%02d", year, month.getMonthNumber(), day));

                verifyInvalidDateStringThrowsParseException(invalidDateStrings);
            }
        }
    }

    @Test
    public void shouldThrowParseExceptionForLeapYearWhenYearAndMonthAreValidButDayIsEitherLessThan1OrNotWithinMonthlyRange() {
        for (Month month : getValidMonths()) {
            final List<String> invalidDateStrings = new ArrayList<>();
            invalidDateStrings.add(format("%d/%02d/%02d", 2000, month.getMonthNumber(), -1));
            invalidDateStrings.add(format("%d/%02d/%02d", 2000, month.getMonthNumber(), 0));

            final int day = month.getDays() + (month.getMonthNumber() == 2 ? 2 : 1);
            invalidDateStrings.add(format("%d/%02d/%02d", 2000, month.getMonthNumber(), day));

            verifyInvalidDateStringThrowsParseException(invalidDateStrings);
        }
    }

    @Test
    public void shouldParseSuccessfullyWhenYearAndMonthAreValidAsWellAsDayIsWithinMonthlyRange() throws Exception {
        for (int year = 1901; year < 3000; year++) {  //looping through all valid years to consider both leap and non leap years
            for (Month month : getValidMonths()) {
                final List<String> validDateStrings = new ArrayList<>();
                validDateStrings.add(format("%d/%02d/%02d", year, month.getMonthNumber(), 1));

                final boolean isLeapYear = (boolean) invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, year);
                final int day = month.getDays() + ((isLeapYear && month.getMonthNumber() == 2) ? 1 : 0); // adding 1 day to make leap year's febrruary month days 29
                validDateStrings.add(format("%d/%02d/%02d", year, month.getMonthNumber(), day));

                verifyDateStringIsParsedSuccessfully(validDateStrings);
            }
        }
    }

    @Test
    public void shouldParseSuccessfullyFor29thOfFebruaryWhenYearIsALeapYear() {
        final List<String> validDateStrings = new ArrayList<>();
        validDateStrings.add("2000/02/29");
        validDateStrings.add("2828/02/29");
        validDateStrings.add("1904/02/29");

        verifyDateStringIsParsedSuccessfully(validDateStrings);
    }

    @Test
    public void shouldThrowParseExceptionFor29thOfFebruaryWhenYearIsNotALeapYear() {
        final List<String> invalidDateStrings = new ArrayList<>();
        invalidDateStrings.add("2051/02/29");
        invalidDateStrings.add("2829/02/29");
        invalidDateStrings.add("1907/02/29");

        verifyInvalidDateStringThrowsParseException(invalidDateStrings);
    }

    @Test
    public void shouldVerifyThatAnYearIsLeapYearWhenItIsDivisibleBy400AndWithinValidRange() throws Exception {
        assertThat(parse("2000/01/01").isLeapYear(), is(true));
        assertThat(parse("2400/01/01").isLeapYear(), is(true));
        assertThat(parse("2800/01/01").isLeapYear(), is(true));
    }

    @Test
    public void shouldVerifyThatAnYearIsNotLeapYearWhenItIsNotInValidRangeEvenWhenItIsDivisibleBy400() throws Exception {
        assertThat(invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, 1600), is(false));
        assertThat(invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, 6400), is(false));
    }

    @Test
    public void shouldVerifyThatAnYearIsLeapYearWhenItIsNeitherDivisibleBy400NorBy100ButDivisibleBy4AndWithinValidRange() throws Exception {
        assertThat(parse("1904/01/01").isLeapYear(), is(true));
        assertThat(parse("2000/01/01").isLeapYear(), is(true));
        assertThat(parse("2016/01/01").isLeapYear(), is(true));
        assertThat(parse("2564/01/01").isLeapYear(), is(true));
        assertThat(parse("2672/01/01").isLeapYear(), is(true));
        assertThat(parse("2996/01/01").isLeapYear(), is(true));
    }

    @Test
    public void shouldVerifyThatAnYearIsNotLeapYearWhenItIsNotInValidRangeEvenWhenItIsNeitherDivisibleBy400NorBy100ButDivisibleBy4() throws Exception {
        assertThat(invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, 1664), is(false));
        assertThat(invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, 4096), is(false));
        assertThat(invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, 6464), is(false));
    }

    @Test
    public void shouldVerifyThatAnYearIsNotLeapYearWhenItIsNeitherDivisibleBy400NorBy100NorBy4() throws Exception {
        assertThat(parse("1901/01/01").isLeapYear(), is(false));
        assertThat(parse("1975/01/01").isLeapYear(), is(false));
        assertThat(parse("2001/01/01").isLeapYear(), is(false));
        assertThat(parse("2107/01/01").isLeapYear(), is(false));
        assertThat(parse("2999/01/01").isLeapYear(), is(false));

        assertThat(invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, 1650), is(false));
        assertThat(invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, 1900), is(false));
        assertThat(invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, 3999), is(false));
        assertThat(invokeStaticPrivateMethod(MyDate.class, "isLeapYear", new Class[]{int.class}, 6407), is(false));
    }

    @Test
    public void shouldVerifyConversionOfDateFromOneFormatToAnother() {
        assertThat(convert("12-22-2001", MM_DD_YYYY, DD_MM_YYYY), is("22-12-2001"));
        assertThat(convert("1901/01/01", YYYY_SLASH_MM_SLASH_DD, DD_MM_YYYY), is("01-01-1901"));
        assertThat(convert("2901-11-21", YYYY_MM_DD, MM_SLASH_DD_SLASH_YYYY), is("11/21/2901"));
    }

    @Test
    public void shouldVerifyEqualsAndHasCodeMethods() throws ParseException {
        final MyDate myDate1 = parse("1983/12/22");
        final MyDate myDate2 = parse("1983/12/22");
        assertThat(myDate1.equals(myDate2), is(true));
        assertThat(myDate1.hashCode(), is(myDate2.hashCode()));

        assertThat(parse("1983/12/22").equals(parse("1983/12/22")), is(true));
        assertThat(parse("1983/12/22").hashCode(), is(parse("1983/12/22").hashCode()));

        assertThat(parse("1989/12/22").equals(parse("1989-12-22", YYYY_MM_DD)), is(true));
        assertThat(parse("1989/12/22").hashCode(), is(parse("1989-12-22", YYYY_MM_DD).hashCode()));

        //different date objects with same hashcode
        assertThat(parse("1989/01/22").equals(parse("1989/03/22")), is(false));
        assertThat(parse("1989/01/22").hashCode(), is(parse("1989/03/22").hashCode()));

        assertThat(parse("1983/12/22").equals(null), is(false));
        assertThat(parse("1983/12/22").toString().equals("1983/12/22"), is(true));
        assertThat(parse("1983/12/22").equals(parse("1984/12/22")), is(false));
        assertThat(parse("1983/12/22").equals(parse("1983/11/22")), is(false));
        assertThat(parse("1983/12/22").equals(parse("1983/12/21")), is(false));
    }

    private Set<Month> getValidMonths() {
        return complementOf(of(UNKNOWN_OR_INVALID));
    }

    private int randomValidYear() {
        return randomInt(1901, 2999);
    }

    private void verifyDateStringIsParsedSuccessfully(final List<String> validDateStrings) {
        validDateStrings
                .forEach(
                        validDateString -> {
                            try {
                                final MyDate myDate = parse(validDateString);
                                final String[] dateParts = validDateString.split("/");
                                assertThat(myDate.getYear(), is(parseInt(dateParts[0])));
                                assertThat(myDate.getMonth(), is(fromNumber(parseInt(dateParts[1]))));
                                assertThat(myDate.getDay(), is(parseInt(dateParts[2])));
                            } catch (ParseException e) {
                                fail("Should have parsed successfully without throwing ParseException.");
                            }
                        }
                );
    }

    private void verifyDateStringIsParsedSuccessfullyWithSpecifiedFormat(final List<Pair<String, DateFormatable>> validDateStrings) {
        validDateStrings
                .forEach(
                        validDateString -> {
                            try {
                                final String dateStr = validDateString.fst();
                                final DateFormatable dateFormat = validDateString.snd();
                                final MyDate myDate = parse(dateStr, dateFormat);
                                final String[] dateParts = dateStr.split(dateFormat.getSeparator());
                                assertThat(myDate.getYear(), is(parseInt(dateParts[dateFormat.getYearOffset()])));
                                assertThat(myDate.getMonth(), is(fromNumber(parseInt(dateParts[dateFormat.getMonthOffset()]))));
                                assertThat(myDate.getDay(), is(parseInt(dateParts[dateFormat.getDayOffset()])));
                                assertThat(myDate.toString(), is(dateStr));
                            } catch (ParseException e) {
                                fail("Should have parsed successfully without throwing ParseException.");
                            }
                        }
                );
    }

    private void verifyInvalidDateStringThrowsParseException(final List<String> invalidDateStrings) {
        invalidDateStrings
                .forEach(
                        invalidDateString -> {
                            try {
                                parse(invalidDateString);
                                fail("Should have thrown ParseException." + invalidDateString);
                            } catch (ParseException e) {
                                assertThat(e.getMessage(), is(format("Entered date is invalid, enter a valid date between %s and %s: ", FIRST_VALID_DATE, LAST_VALID_DATE)));
                            }
                        }
                );
    }
}