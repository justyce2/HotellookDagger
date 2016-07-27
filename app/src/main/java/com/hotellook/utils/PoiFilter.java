package com.hotellook.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.common.Poi;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class PoiFilter {
    private PoiFilter() {
    }

    @NonNull
    public static List<Poi> filter(@Nullable List<Poi> pois, Set<String> categories) {
        List<Poi> filteredPoi = new LinkedList();
        if (!(CollectionUtils.isEmpty(pois) || categories.size() == 0)) {
            for (Poi poi : pois) {
                if (categories.contains(poi.getCategory())) {
                    filteredPoi.add(poi);
                }
            }
        }
        return filteredPoi;
    }

    @NonNull
    public static List<Poi> filter(@Nullable List<Poi> pois, String... categories) {
        return filter((List) pois, new HashSet(Arrays.asList(categories)));
    }
}
