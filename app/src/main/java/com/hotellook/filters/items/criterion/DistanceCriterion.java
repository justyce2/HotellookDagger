package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.utils.LocationUtils;

public class DistanceCriterion implements Criterion<HotelData> {
    private final DistanceTarget distanceTarget;
    private final double maxDistance;

    public DistanceCriterion(double maxDistance, DistanceTarget distanceTarget) {
        this.maxDistance = maxDistance;
        this.distanceTarget = distanceTarget;
    }

    public boolean passes(HotelData value) {
        return roundToSeekbarRounding(this.distanceTarget.distanceTo(LocationUtils.from(value.getLocation()))) <= this.maxDistance;
    }

    private double roundToSeekbarRounding(double distance) {
        return ((double) Math.round(distance * 100.0d)) / 100.0d;
    }
}
