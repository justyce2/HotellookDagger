package com.hotellook.utils;

public final class Preconditions {
    private Preconditions() {
    }

    public static <T> T checkNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException();
    }

    public static <T> T checkNotNull(T reference, String message) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(message);
    }
}
