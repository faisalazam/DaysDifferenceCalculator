package com.mindworks.calculator.days;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static com.mindworks.calculator.days.DateFormat.values;
import static com.mindworks.calculator.days.DayDifferenceCalculatorScanner.boldColored;
import static com.mindworks.calculator.days.DayDifferenceCalculatorScanner.textColor;
import static com.mindworks.calculator.days.MyDate.DEFAULT_DATE_FORMAT;
import static com.mindworks.calculator.days.MyDate.FIRST_VALID_DATE;
import static com.mindworks.calculator.days.MyDate.LAST_VALID_DATE;
import static com.mindworks.calculator.days.MyDate.convert;
import static com.mindworks.calculator.days.ReflectionHelper.setField;
import static com.mindworks.calculator.days.TestUtils.chooseOneOf;
import static java.lang.String.format;
import static java.lang.System.setOut;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/*
    Don't forget to add the following to VM option if running this class from IDE
    --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED
*/
public class DayDifferenceCalculatorScannerTest {
    private OutputStream outputStream;
    private DayDifferenceCalculatorScanner dayDifferenceCalculatorScanner;

    @BeforeEach
    public void setup() {
        outputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(outputStream);
        setOut(printStream);
        dayDifferenceCalculatorScanner = new DayDifferenceCalculatorScanner();
    }

    @Test
    public void shouldVerifySystemDoesNotAskUserAgainToEnterValidDateWhenAValidDateIsAlreadyEntered() throws Exception {
        final String promptMessage = "Enter date: ";
        final DateFormat dateFormat = chooseOneOf(values());
        final String enteredDate = convert("2000/12/21", DEFAULT_DATE_FORMAT, dateFormat);
        setField(dayDifferenceCalculatorScanner, "scanner", new Scanner(enteredDate));
        assertThat(dayDifferenceCalculatorScanner.getDate(dateFormat, promptMessage).toString(), is(enteredDate));

        assertThat(outputStream.toString(), containsString("Welcome to days difference calculator"));
        assertThat(outputStream.toString(), endsWith("Enter date: "));
    }

    @Test
    public void shouldVerifySystemKeepsAskingUserToEnterValidDateUntilAValidDateIsEntered() throws Exception {
        final String promptMessage = "Enter date: ";
        final DateFormat dateFormat = chooseOneOf(values());
        final String enteredDate = convert("2000/12/21", DEFAULT_DATE_FORMAT, dateFormat);
        setField(dayDifferenceCalculatorScanner, "scanner", new Scanner("invalid_date\nanother_invalid_date\n" + enteredDate));
        assertThat(dayDifferenceCalculatorScanner.getDate(dateFormat, promptMessage).toString(), is(enteredDate));

        final String firstValidDate = convert(FIRST_VALID_DATE, DEFAULT_DATE_FORMAT, dateFormat);
        final String lastValidDate = convert(LAST_VALID_DATE, DEFAULT_DATE_FORMAT, dateFormat);
        final String expectedOutput = "Enter date: " +
                "Entered date is invalid, enter a valid date between " + firstValidDate + " and " + lastValidDate + ": " +
                "Entered date is invalid, enter a valid date between " + firstValidDate + " and " + lastValidDate + ": ";
        assertThat(outputStream.toString(), containsString("Welcome to days difference calculator"));
        assertThat(outputStream.toString(), containsString(expectedOutput));
    }

    @Test
    public void shouldReturnTrueFromDoYouWantToContinueAndDoesNotPrintThankYouMessageWhenInputIsEitherYesOrY() throws Exception {
        final String[] inputs = {"Yes", "Y", "yes", "y", "yEs", "yeS", "YeS", "YEs"};
        for (final String input : inputs) {
            setField(dayDifferenceCalculatorScanner, "scanner", new Scanner(input));
            assertThat(dayDifferenceCalculatorScanner.doYouWantToContinue(), is(true));

            final String expectedOutput = "Do you want to continue? Enter 'Y[es]' to continue: ";
            assertThat(outputStream.toString(), endsWith(expectedOutput));
        }
    }

    @Test
    public void shouldReturnFalseFromDoYouWantToContinueAndPrintThankYouMessageWhenInputIsNeitherYesNorY() throws Exception {
        setField(dayDifferenceCalculatorScanner, "scanner", new Scanner("any thing"));
        assertThat(dayDifferenceCalculatorScanner.doYouWantToContinue(), is(false));

        assertThat(outputStream.toString(), containsString("Do you want to continue? Enter 'Y[es]' to continue: "));
        assertThat(outputStream.toString(), containsString("Thank you for using the days difference calculator. Have a lovely day!"));
    }

    @Test
    public void shouldReturnTheSpecifiedDateFormatWhenItIsValid() throws Exception {
        for (DateFormat dateFormat : values()) {
            setField(dayDifferenceCalculatorScanner, "scanner", new Scanner(dateFormat.getFormat()));
            assertThat(dayDifferenceCalculatorScanner.getDateFormat().getFormat(), is(dateFormat.getFormat()));

            assertThat(outputStream.toString(), containsString("Following are the date formats to choose from: "));
            for (DateFormat dateFormat1 : values()) {
                assertThat(outputStream.toString(), containsString(dateFormat1.getFormat()));
            }
            assertThat(outputStream.toString(), containsString("Enter the desired format to use (else default 'yyyy/MM/dd' format will be used): "));
        }
    }

    @Test
    public void shouldReturnTheDefaultDateFormatWhenProvidedDateFormatIsInvalid() throws Exception {
        setField(dayDifferenceCalculatorScanner, "scanner", new Scanner("any thing"));
        assertThat(dayDifferenceCalculatorScanner.getDateFormat().getFormat(), is(DEFAULT_DATE_FORMAT.getFormat()));

        assertThat(outputStream.toString(), containsString("Following are the date formats to choose from: "));
        for (DateFormat dateFormat1 : values()) {
            assertThat(outputStream.toString(), containsString(dateFormat1.getFormat()));
        }
        assertThat(outputStream.toString(), containsString("Enter the desired format to use (else default 'yyyy/MM/dd' format will be used): "));
    }

    @Test
    public void shouldVerifyThatColorIndexIsAlwaysGreaterThan30ButLessThan37() {
        assertThat(textColor(), is(format("%s[%sm", (char) 27, 36)));
        assertThat(textColor(), is(format("%s[%sm", (char) 27, 31)));
        assertThat(boldColored(), is(format("\033[0;1m%s[%sm", (char) 27, 32)));
        assertThat(textColor(), is(format("%s[%sm", (char) 27, 33)));
        assertThat(textColor(), is(format("%s[%sm", (char) 27, 34)));
        assertThat(boldColored(), is(format("\033[0;1m%s[%sm", (char) 27, 35)));
        assertThat(textColor(), is(format("%s[%sm", (char) 27, 36)));
        assertThat(textColor(), is(format("%s[%sm", (char) 27, 31)));
        assertThat(boldColored(), is(format("\033[0;1m%s[%sm", (char) 27, 32)));
        assertThat(textColor(), is(format("%s[%sm", (char) 27, 33)));
        assertThat(textColor(), is(format("%s[%sm", (char) 27, 34)));
        assertThat(boldColored(), is(format("\033[0;1m%s[%sm", (char) 27, 35)));
    }
}