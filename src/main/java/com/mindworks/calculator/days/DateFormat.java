package com.mindworks.calculator.days;

public enum DateFormat {
    DD_MM_YYYY("dd-MM-yyyy", "-", 0, 1, 2),
    MM_DD_YYYY("MM-dd-yyyy", "-", 1, 0, 2),
    YYYY_MM_DD("yyyy-MM-dd", "-", 2, 1, 0),
    DD_SLASH_MM_SLASH_YYYY("dd/MM/yyyy", "/", 0, 1, 2),
    MM_SLASH_DD_SLASH_YYYY("MM/dd/yyyy", "/", 1, 0, 2),
    YYYY_SLASH_MM_SLASH_DD("yyyy/MM/dd", "/", 2, 1, 0);

    private final String format;
    private final String separator;
    private final int dayOffset;
    private final int yearOffset;
    private final int monthOffset;

    DateFormat(final String format,
               final String separator,
               final int dayOffset,
               final int monthOffset,
               final int yearOffset) {

        this.format = format;
        this.separator = separator;
        this.dayOffset = dayOffset;
        this.monthOffset = monthOffset;
        this.yearOffset = yearOffset;
    }

    public static DateFormat fromFormat(final String format) {
        for (DateFormat dateFormat : values()) {
            if (dateFormat.getFormat().equals(format)) {
                return dateFormat;
            }
        }
        return null;
    }

    public String getFormat() {
        return format;
    }

    public String getSeparator() {
        return separator;
    }

    public int getDayOffset() {
        return dayOffset;
    }

    public int getMonthOffset() {
        return monthOffset;
    }

    public int getYearOffset() {
        return yearOffset;
    }
}
