package com.hotellook.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import com.hotellook.C1178R;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

public final class ValueFormat {
    private static final String KM_MI_FORMAT = "%.1f %s";
    private static final String METERS_FORMAT = "%d %s";
    private static final DecimalFormat RATING_FORMAT;
    private static final String REVIEW_DATE_FORMAT_PATTERN = "MMMM yyyy";
    private static final int SECOND_IN_MILLISECONDS = 1000;

    static {
        RATING_FORMAT = new DecimalFormat("0.0");
    }

    @NonNull
    public static String ratingToString(int rating) {
        return rating < 100 ? RATING_FORMAT.format((double) (((float) rating) / 10.0f)) : "10";
    }

    public static String reviewDateToString(@NonNull Context context, long dateInSeconds) {
        return DateUtils.getSimpleDateFormatWithNominativeRuMonths(context, REVIEW_DATE_FORMAT_PATTERN, AndroidUtils.getLocale()).format(new Date(1000 * dateInSeconds)).toLowerCase();
    }

    @NonNull
    public static String distanceToString(@NonNull Context context, int distance) {
        if (!AndroidUtils.isMetricUnits()) {
            return milesToString(context, distance);
        }
        int meters = Distances.roundToTenMeters(distance);
        return ((float) meters) < Distances.KILOMETER ? metersToString(context, meters) : kilometersToString(context, distance);
    }

    @NonNull
    private static String metersToString(@NonNull Context context, int distance) {
        return String.format(Locale.getDefault(), METERS_FORMAT, new Object[]{Integer.valueOf(distance), context.getString(C1178R.string.meters)});
    }

    @NonNull
    private static String kilometersToString(@NonNull Context context, int distance) {
        if (((float) distance) < Distances.KILOMETER) {
            distance = SECOND_IN_MILLISECONDS;
        }
        float kilometers = Distances.roundToKilometers(distance);
        return String.format(Locale.getDefault(), KM_MI_FORMAT, new Object[]{Float.valueOf(kilometers), context.getString(C1178R.string.kilometers)});
    }

    @NonNull
    private static String milesToString(@NonNull Context context, int distance) {
        float miles = Distances.roundToMiles(distance);
        return String.format(Locale.getDefault(), KM_MI_FORMAT, new Object[]{Float.valueOf(miles), context.getString(C1178R.string.miles)});
    }

    private ValueFormat() {
    }
}
