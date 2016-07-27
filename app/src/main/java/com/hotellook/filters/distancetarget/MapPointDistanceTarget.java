package com.hotellook.filters.distancetarget;

import android.content.Context;
import android.location.Location;
import com.hotellook.C1178R;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;

public class MapPointDistanceTarget extends LocationDistanceTarget {
    public MapPointDistanceTarget(Context context, Location location) {
        super(location, context.getString(C1178R.string.search_type_name_location), DestinationType.MAP_POINT);
    }
}
