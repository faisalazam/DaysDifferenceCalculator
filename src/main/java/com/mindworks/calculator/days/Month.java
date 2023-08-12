package com.mindworks.calculator.days;

public enum Month {
    JANUARY(1, 31, 0, false),
    FEBRUARY(2, 28, 31, false),
    MARCH(3, 31, 59, true),
    APRIL(4, 30, 90, true),
    MAY(5, 31, 120, true),
    JUNE(6, 30, 151, true),
    JULY(7, 31, 181, true),
    AUGUST(8, 31, 212, true),
    SEPTEMBER(9, 30, 243, true),
    OCTOBER(10, 31, 273, true),
    NOVEMBER(11, 30, 304, true),
    DECEMBER(12, 31, 334, true),
    UNKNOWN_OR_INVALID(0, 0, 0, false);

    private final int days;
    private final int daysUpto;
    private final int monthNumber;
    private final boolean isAfterFebruary;

    Month(final int monthNumber, final int days, final int daysUpto, final boolean isAfterFebruary) {
        this.days = days;
        this.daysUpto = daysUpto;
        this.monthNumber = monthNumber;
        this.isAfterFebruary = isAfterFebruary;
    }

    public int getDays() {
        return days;
    }

    public int getDaysUpto() {
        return daysUpto;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public boolean isAfterFebruary() {
        return isAfterFebruary;
    }

    public static Month fromNumber(final int monthNumber) {
        for (Month month : values()) {
            if (month.getMonthNumber() == monthNumber) {
                return month;
            }
        }
        return UNKNOWN_OR_INVALID;
    }
}
