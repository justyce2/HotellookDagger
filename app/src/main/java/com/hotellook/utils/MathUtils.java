package com.hotellook.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {
    public static double round(double value, int places) {
        if (places >= 0) {
            return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
        }
        throw new IllegalArgumentException();
    }
}
