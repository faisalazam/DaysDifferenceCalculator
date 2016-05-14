package com.mindworks.calculator.days;

import static com.mindworks.calculator.days.MyDate.DAYS_IN_YEAR;
import static com.mindworks.calculator.days.MyDate.FIRST_VALID_YEAR;
import static com.mindworks.calculator.days.MyDate.isLeapYear;

public class DaysDifferenceCalculator {
    private DaysDifferenceCalculator() {
    }

    public static long calculate(final MyDate date1, final MyDate date2) {
        if (date1 == null || date2 == null || date1.equals(date2)) {
            return 0L;
        }

        final long numberOfDaysDate1 = convertDateToNumberOfDays(date1);
        final long numberOfDaysDate2 = convertDateToNumberOfDays(date2);

        if (numberOfDaysDate1 > numberOfDaysDate2) {
            return (numberOfDaysDate1 - numberOfDaysDate2 - 1) + additionalLeapYearDays(date2, date1);
        } else {
            return (numberOfDaysDate2 - numberOfDaysDate1 - 1) + additionalLeapYearDays(date1, date2);
        }
    }

    private static long convertDateToNumberOfDays(final MyDate date) {
        final long daysInYears = (date.getYear() - FIRST_VALID_YEAR) * DAYS_IN_YEAR;
        return daysInYears + date.getMonth().getDaysUpto() + date.getDay();
    }

    private static int additionalLeapYearDays(final MyDate earlierDate, final MyDate laterDate) {
        int numberOfLeapYears = 0;
        final int offsetStart = earlierDate.getMonth().isAfterFebruary() ? 1 : 0;
        final int offsetEnd = laterDate.getMonth().isAfterFebruary() ? 1 : 0;
        final int startYearValue = offsetStart + earlierDate.getYear();
        final int endYearValue = offsetEnd + laterDate.getYear();
        for (int year = startYearValue; year < endYearValue; year++) {
            numberOfLeapYears += (isLeapYear(year) ? 1 : 0);
        }
        return numberOfLeapYears;
    }
}
