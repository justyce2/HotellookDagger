package com.hotellook.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.text.NumberFormat;

public final class StringUtils {
    @NonNull
    public static String toStringWithDelimiter(int value) {
        return NumberFormat.getNumberInstance().format((long) value);
    }

    @Nullable
    public static String capitalize(@Nullable String string) {
        if (isNullOrEmpty(string)) {
            return string;
        }
        char ch = string.charAt(0);
        return !Character.isTitleCase(ch) ? Character.toTitleCase(ch) + string.substring(1) : string;
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isBlank(@Nullable CharSequence string) {
        return !isNotBlank(string);
    }

    public static boolean isNotBlank(@Nullable CharSequence string) {
        if (string == null) {
            return false;
        }
        int n = string.length();
        for (int i = 0; i < n; i++) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private StringUtils() {
    }
}
