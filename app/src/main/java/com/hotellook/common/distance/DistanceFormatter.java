package com.hotellook.common.distance;

import android.content.res.Resources;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.LocationUtils;

public class DistanceFormatter {
    private static final int KM_RES_ID = 2131165456;
    private static final float KM_TO_MILES_FACTOR = 0.621f;
    private static final int MILES_RES_ID = 2131165478;
    private final String mFormat;
    private final String mMetric;

    public DistanceFormatter(Resources res, int formatStringId) {
        this.mMetric = AndroidUtils.isMetricUnits() ? res.getString(KM_RES_ID) : res.getString(MILES_RES_ID);
        this.mFormat = res.getString(formatStringId);
    }

    public String getDistanceTxt(Coordinates loc1, Coordinates loc2) {
        return format((double) LocationUtils.distanceBetween(loc1, loc2));
    }

    private String format(double distanceInMeters) {
        double distanceInLocalMetric = getDistanceInLocalMetric(distanceInMeters / 1000.0d);
        return String.format(this.mFormat, new Object[]{Double.valueOf(distanceInLocalMetric), this.mMetric});
    }

    private double getDistanceInLocalMetric(double distanceInKm) {
        return AndroidUtils.isMetricUnits() ? distanceInKm : distanceInKm * 0.6209999918937683d;
    }
}
