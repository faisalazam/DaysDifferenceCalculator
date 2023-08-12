package com.mindworks.calculator.days;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.mindworks.calculator.days.DateFormat.YYYY_SLASH_MM_SLASH_DD;
import static com.mindworks.calculator.days.Month.UNKNOWN_OR_INVALID;
import static com.mindworks.calculator.days.Month.fromNumber;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.IntStream.rangeClosed;

public class MyDate {
    public static final int DAYS_IN_YEAR = 365;
    public static final int FIRST_VALID_YEAR = 1901;
    public static final String FIRST_VALID_DATE = "1901/01/01";
    public static final String LAST_VALID_DATE = "2999/12/31";
    public static final DateFormatable DEFAULT_DATE_FORMAT = YYYY_SLASH_MM_SLASH_DD;

    private final int day;
    private final int year;
    private final Month month;
    private final String dateStr;

    // don't really need to use this static collection, as the calculation can happen on the fly
    // as well (because it is not an expensive calculation), but in case of expensive operations,
    //this static collection idea can be used.
    private static final List<Integer> LEAP_YEARS = new ArrayList<>();

    static {
        rangeClosed(1900, 3000).forEach(year -> {
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                LEAP_YEARS.add(year);
            }
        });
    }

    private MyDate(final String dateStr, final DateFormatable dateFormat) throws ParseException {
        final String[] splitDate = dateStr.split(dateFormat.getSeparator());
        this.year = parseInt(splitDate[dateFormat.getYearOffset()]);
        this.month = fromNumber(parseInt(splitDate[dateFormat.getMonthOffset()]));
        this.day = parseInt(splitDate[dateFormat.getDayOffset()]);
        this.dateStr = dateFormat.getFormat()
                .replace("dd", valueOf(format("%02d", day)))
                .replace("MM", valueOf(format("%02d", month.getMonthNumber())))
                .replace("yyyy", valueOf(year));

        if (!isValid()) {
            throw new ParseException("", 0);
        }
    }

    public static MyDate parse(final String dateStr) throws ParseException {
        return parse(dateStr, DEFAULT_DATE_FORMAT);
    }

    public static MyDate parse(final String dateStr, final DateFormatable dateFormat) throws ParseException {
        try {
            return new MyDate(dateStr, dateFormat);
        } catch (Exception e) {
            throw new ParseException(
                    format("Entered date is invalid, enter a valid date between %s and %s: ",
                            convert(FIRST_VALID_DATE, DEFAULT_DATE_FORMAT, dateFormat),
                            convert(LAST_VALID_DATE, DEFAULT_DATE_FORMAT, dateFormat)
                    ), 0);
        }
    }

    public static String convert(final String dateStr, final DateFormatable sourceDateFormat, final DateFormatable targetDateFormat) {
        final String[] splitDate = dateStr.split(sourceDateFormat.getSeparator());
        return targetDateFormat.getFormat()
                .replace("dd", splitDate[sourceDateFormat.getDayOffset()])
                .replace("MM", splitDate[sourceDateFormat.getMonthOffset()])
                .replace("yyyy", splitDate[sourceDateFormat.getYearOffset()]);
    }

    public boolean isLeapYear() {
        return LEAP_YEARS.contains(year);
    }

    private boolean isValid() {
        return year >= FIRST_VALID_YEAR
                && year <= 2999
                && !UNKNOWN_OR_INVALID.equals(month)
                && isValidDay();
    }

    private boolean isValidDay() {
        return day > 0 && (day <= month.getDays() || (isLeapYear() && day == 29));
    }

    public static boolean isLeapYear(final int year) {
        return LEAP_YEARS.contains(year);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof MyDate)) {
            return false;
        }
        final MyDate that = (MyDate) obj;
        return this.year == that.year && this.month == that.month && this.day == that.day;
    }

    @Override
    public int hashCode() {
        return year * day * month.getDays();
    }

    @Override
    public String toString() {
        return dateStr;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }
}
