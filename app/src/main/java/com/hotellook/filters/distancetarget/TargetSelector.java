package com.hotellook.filters.distancetarget;

import com.hotellook.search.SearchData;

public interface TargetSelector {
    DistanceTarget select(SearchData searchData);
}
