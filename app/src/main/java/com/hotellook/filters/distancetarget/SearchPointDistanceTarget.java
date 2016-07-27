package com.hotellook.filters.distancetarget;

import android.content.Context;
import android.location.Location;
import com.hotellook.C1178R;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;

public class SearchPointDistanceTarget extends LocationDistanceTarget {
    public SearchPointDistanceTarget(Context context, Location location) {
        super(location, context.getString(C1178R.string.search_point_distance_target), DestinationType.SEARCH_POINT);
    }
}
