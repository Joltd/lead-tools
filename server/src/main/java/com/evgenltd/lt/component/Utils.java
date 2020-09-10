package com.evgenltd.lt.component;

public class Utils {

    public static boolean isBlank(final String value) {
        return value == null || value.isBlank();
    }

    public static boolean isNotBlank(final String value) {
        return !isBlank(value);
    }

}
