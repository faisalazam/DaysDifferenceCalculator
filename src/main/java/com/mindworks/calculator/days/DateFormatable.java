package com.mindworks.calculator.days;

public interface DateFormatable {
    static DateFormatable fromFormat(String format) {
        for (DateFormatable dateFormat : DateFormat.values()) {
            if (dateFormat.getFormat().equals(format)) {
                return dateFormat;
            }
        }
        return null;
    }

    String getFormat();

    String getSeparator();

    int getDayOffset();

    int getMonthOffset();

    int getYearOffset();
}
