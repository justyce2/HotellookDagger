package com.hotellook.filters.distancetarget;

import android.support.annotation.NonNull;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;

public class DefaultTargetSelector implements TargetSelector {
    public DistanceTarget select(SearchData searchData) {
        SearchParams searchParams = searchData.searchParams();
        if (searchParams.isDestinationTypeUserLocation()) {
            return createMyLocation(searchData);
        }
        if (searchParams.isDestinationTypeMapPoint()) {
            return createLocationTarget(searchData);
        }
        if (searchParams.isDestinationTypeNearbyCities()) {
            return createNearbyCityTarget(searchData);
        }
        return createCityCenter(searchData);
    }

    @NonNull
    private LocationDistanceTarget createLocationTarget(SearchData searchData) {
        return new LocationDistanceTarget(searchData.searchParams().location(), HotellookApplication.getApp().getString(C1178R.string.search_type_name_location), DestinationType.MAP_POINT);
    }

    @NonNull
    private LocationDistanceTarget createCityCenter(SearchData searchData) {
        return new LocationDistanceTarget(searchData.searchParams().location(), HotellookApplication.getApp().getString(C1178R.string.city_center), DestinationType.CENTER);
    }

    @NonNull
    private LocationDistanceTarget createNearbyCityTarget(SearchData searchData) {
        return new LocationDistanceTarget(searchData.searchParams().location(), searchData.searchParams().destinationName(), DestinationType.CENTER);
    }

    @NonNull
    private LocationDistanceTarget createMyLocation(SearchData searchData) {
        return new MyLocationDistanceTarget(HotellookApplication.getApp(), searchData.searchParams().location());
    }
}
