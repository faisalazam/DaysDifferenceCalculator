package com.mindworks.calculator.days;

import java.io.PrintStream;
import java.text.ParseException;
import java.util.Scanner;

import static com.mindworks.calculator.days.DateFormat.fromFormat;
import static com.mindworks.calculator.days.DateFormat.values;
import static com.mindworks.calculator.days.MyDate.DEFAULT_DATE_FORMAT;
import static com.mindworks.calculator.days.MyDate.parse;
import static java.lang.System.in;
import static java.lang.System.out;
import static java.util.stream.Stream.of;

public class DayDifferenceCalculatorScanner {
    private static int colorIndex = 31;

    private static final String WELCOME_TO_CALCULATOR_MESSAGE_FORMAT = "%s%nWelcome to days difference calculator%n%n";
    private static final String LIST_DATE_FORMATS_MESSAGE_FORMAT = "%sFollowing are the date formats to choose from: ";
    private static final String THANKS_MESSAGE_FORMAT = "%s%nThank you for using the days difference calculator. Have a lovely day!%s%n%n";
    private static final String DO_YOU_WANT_TO_CONTINUE_PROMPT_MESSAGE_FORMAT = "%s%n%nDo you want to continue? Enter 'Y[es]' to continue: ";
    private static final String ENTER_DESIRED_FORMAT_PROMPT_MESSAGE_FORMAT = "%s%nEnter the desired format to use (else default 'yyyy/MM/dd' format will be used): ";

    private final Scanner scanner;
    private final PrintStream printStream;

    public DayDifferenceCalculatorScanner() {
        this.printStream = out;
        this.scanner = new Scanner(in);

        this.printStream.printf(WELCOME_TO_CALCULATOR_MESSAGE_FORMAT, boldColored());
    }

    public MyDate getDate(final DateFormat dateFormat, final String promptMessage) {
        printStream.printf(promptMessage);
        MyDate myDate = null;
        boolean isInvalidDateProvided;
        do {
            try {
                myDate = parse(scanner.next(), dateFormat);
                isInvalidDateProvided = false;
            } catch (ParseException e) {
                isInvalidDateProvided = true;
                printStream.printf(e.getMessage());
            }
        } while (isInvalidDateProvided);
        return myDate;
    }

    public boolean doYouWantToContinue() {
        printStream.printf(DO_YOU_WANT_TO_CONTINUE_PROMPT_MESSAGE_FORMAT, textColor());
        final String desireToContinue = scanner.next();
        final boolean doYouWantToContinue = "Yes".equalsIgnoreCase(desireToContinue) || "Y".equalsIgnoreCase(desireToContinue);
        if (!doYouWantToContinue) {
            printStream.printf(THANKS_MESSAGE_FORMAT, boldColored(), (char) 27 + "[0m");
        }
        return doYouWantToContinue;
    }

    public DateFormat getDateFormat() {
        printStream.printf(LIST_DATE_FORMATS_MESSAGE_FORMAT, textColor());
        of(values()).forEach(dateFormat -> printStream.printf("%n%s", dateFormat.getFormat()));
        printStream.printf(ENTER_DESIRED_FORMAT_PROMPT_MESSAGE_FORMAT, textColor());

        final String dateFormatStr = scanner.next();
        final DateFormat dateFormat = fromFormat(dateFormatStr);
        return dateFormat == null ? DEFAULT_DATE_FORMAT : dateFormat;
    }

    public static String textColor() {
        colorIndex = colorIndex < 36 ? ++colorIndex : 31;
        return (char) 27 + "[" + colorIndex + "m";
    }

    public static String boldColored() {
        return "\033[0;1m" + textColor();
    }
}
