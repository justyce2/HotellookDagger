package com.hotellook.events;

import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.distancetarget.PoiDistanceTarget;

public class DistanceTargetSelectedEvent {
    public final DistanceTarget distanceTarget;

    public DistanceTargetSelectedEvent(int cityId, Poi poi) {
        this.distanceTarget = new PoiDistanceTarget(cityId, poi);
    }

    public DistanceTargetSelectedEvent(DistanceTarget distanceTarget) {
        this.distanceTarget = distanceTarget;
    }
}
