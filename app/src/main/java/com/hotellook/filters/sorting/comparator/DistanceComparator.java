package com.hotellook.filters.sorting.comparator;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.utils.LocationUtils;
import java.util.Comparator;

public class DistanceComparator implements Comparator<HotelData> {
    private final DistanceTarget distanceTarget;

    public DistanceComparator(DistanceTarget distanceTarget) {
        this.distanceTarget = distanceTarget;
    }

    public int compare(HotelData lhs, HotelData rhs) {
        return Double.compare(this.distanceTarget.distanceTo(LocationUtils.from(lhs.getLocation())), this.distanceTarget.distanceTo(LocationUtils.from(rhs.getLocation())));
    }
}
