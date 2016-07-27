package com.hotellook.ui.screen.searchresults.adapters.cards.distancecard;

import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Distances;

public class InitialDistanceSetter {
    private static final int INIT_DISTANCE_CENTER_KM = 5;
    private static final int INIT_DISTANCE_CENTER_MILES = 3;
    private static final int INIT_DISTANCE_OTHER_KM = 1;
    private static final int INIT_DISTANCE_OTHER_MILES = 1;

    public void setUpInitDistance(DistanceFilterItem localDistanceFilterItem, SearchData search) {
        double minDistance = localDistanceFilterItem.getMinDistance();
        double maxDistance = localDistanceFilterItem.getMaxDistance();
        int initDistance = getInitDistanceConstant(localDistanceFilterItem.getDistanceType(), search.searchParams(), maxDistance);
        if (((double) initDistance) < minDistance || ((double) initDistance) > maxDistance) {
            localDistanceFilterItem.setValue(maxDistance);
        } else {
            localDistanceFilterItem.setInterpolatedValue(initDistance);
        }
    }

    public int getInitDistanceConstant(DestinationType destType, SearchParams searchParams, double maxDistance) {
        boolean metricUnits = AndroidUtils.isMetricUnits();
        if (DestinationType.CENTER.equals(destType) && searchParams.isDestinationTypeNearbyCities()) {
            return (int) maxDistance;
        }
        if (DestinationType.CENTER.equals(destType) || DestinationType.USER.equals(destType) || DestinationType.SEARCH_POINT.equals(destType)) {
            if (metricUnits) {
                return (int) Distances.toMeters(INIT_DISTANCE_CENTER_KM);
            }
            return (int) Distances.milesToMeters(INIT_DISTANCE_CENTER_MILES);
        } else if (metricUnits) {
            return (int) Distances.toMeters(INIT_DISTANCE_OTHER_MILES);
        } else {
            return (int) Distances.milesToMeters(INIT_DISTANCE_OTHER_MILES);
        }
    }
}
