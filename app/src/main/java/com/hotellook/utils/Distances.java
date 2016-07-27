package com.hotellook.utils;

public final class Distances {
    public static final float KILOMETER = 1000.0f;
    public static final float MILE = 1609.344f;
    private static final float TEN_METERS = 10.0f;

    private Distances() {
    }

    public static int roundToTenMeters(int meters) {
        return (int) (Math.ceil((double) (((float) meters) / TEN_METERS)) * 10.0d);
    }

    public static float roundToKilometers(int meters) {
        return round(((float) meters) / KILOMETER, 1);
    }

    public static float roundToMiles(int meters) {
        return round(((float) meters) / MILE, 1);
    }

    private static float round(float value, int scale) {
        float pow = (float) Math.pow(10.0d, (double) scale);
        return ((float) Math.round(value * pow)) / pow;
    }

    public static float toMeters(int km) {
        return ((float) km) * KILOMETER;
    }

    public static float milesToMeters(int miles) {
        return ((float) miles) * MILE;
    }
}
