package com.hotellook.filters.distancetarget;

import android.content.Context;
import android.location.Location;
import com.hotellook.C1178R;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;

public class MyLocationDistanceTarget extends LocationDistanceTarget {
    public MyLocationDistanceTarget(Context context, Location location) {
        super(location, context.getString(C1178R.string.my_location), DestinationType.USER);
    }
}
