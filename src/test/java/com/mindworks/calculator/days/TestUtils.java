package com.mindworks.calculator.days;

import java.security.SecureRandom;

public class TestUtils {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static int randomInt(final int minValue, final int maxValue) {
        return minValue + RANDOM.nextInt(maxValue - minValue);
    }

    public static <T> T chooseOneOf(final T[] values) {
        return values[randomInt(0, values.length)];
    }
}
