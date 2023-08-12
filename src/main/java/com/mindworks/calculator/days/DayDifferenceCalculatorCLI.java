package com.mindworks.calculator.days;

import static com.mindworks.calculator.days.DayDifferenceCalculatorScanner.boldColored;
import static com.mindworks.calculator.days.DayDifferenceCalculatorScanner.textColor;
import static com.mindworks.calculator.days.DaysDifferenceCalculator.calculate;
import static com.mindworks.calculator.days.MyDate.DEFAULT_DATE_FORMAT;
import static com.mindworks.calculator.days.MyDate.FIRST_VALID_DATE;
import static com.mindworks.calculator.days.MyDate.LAST_VALID_DATE;
import static com.mindworks.calculator.days.MyDate.convert;
import static java.lang.String.format;
import static java.lang.System.out;

public class DayDifferenceCalculatorCLI {
    private static final String FIRST_DATE_PROMPT_MESSAGE_FORMAT = "%s%nEnter a valid date (%s) between %s and %s: ";
    private static final String SECOND_DATE_PROMPT_MESSAGE_FORMAT = "Enter another valid date (%s) between %s and %s: ";
    private static final String NUMBER_OF_DAYS_BETWEEN_MESSAGE_FORMAT = "%sNumber of days between the provided dates (excluding starting and ending day): %s";

    private DayDifferenceCalculatorCLI() {
        //added private constructor as this class contains only static methods and should be accessed via the Class Name...
    }

    public static void main(final String[] args) {
        final DayDifferenceCalculatorScanner dayDifferenceCalculatorScanner = new DayDifferenceCalculatorScanner();
        final DateFormatable dateFormat = dayDifferenceCalculatorScanner.getDateFormat();
        final String firstValidDate = convert(FIRST_VALID_DATE, DEFAULT_DATE_FORMAT, dateFormat);
        final String lastValidDate = convert(LAST_VALID_DATE, DEFAULT_DATE_FORMAT, dateFormat);

        do {
            final MyDate firstDate = dayDifferenceCalculatorScanner.getDate(dateFormat,
                    format(FIRST_DATE_PROMPT_MESSAGE_FORMAT, textColor(), dateFormat.getFormat(), firstValidDate, lastValidDate));
            final MyDate secondDate = dayDifferenceCalculatorScanner.getDate(dateFormat,
                    format(SECOND_DATE_PROMPT_MESSAGE_FORMAT, dateFormat.getFormat(), firstValidDate, lastValidDate));
            final long numberOfDaysInBetween = calculate(firstDate, secondDate);
            out.printf(NUMBER_OF_DAYS_BETWEEN_MESSAGE_FORMAT, boldColored(), numberOfDaysInBetween);
        } while (dayDifferenceCalculatorScanner.doYouWantToContinue());
    }
}
