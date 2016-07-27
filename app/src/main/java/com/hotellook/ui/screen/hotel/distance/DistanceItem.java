package com.hotellook.ui.screen.hotel.distance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.ui.screen.filters.SupportedSeasons;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.LocationUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DistanceItem implements Comparable<DistanceItem> {
    public static final int AIRPORT = 0;
    public static final int BEACH = 1;
    public static final int CITY_CENTER = 5;
    public static final int METRO_STATION = 2;
    public static final int MY_LOCATION = 6;
    public static final int PIN = 7;
    public static final int SKI_LIFT = 3;
    public static final int TRAIN_STATION = 4;
    private static final HashMap<String, Integer> poiTypeToDistanceItem;
    public final int distance;
    public final String poiName;
    public final int poiType;

    /* renamed from: com.hotellook.ui.screen.hotel.distance.DistanceItem.1 */
    static class C13141 extends HashMap<String, Integer> {
        C13141() {
            put(Poi.CATEGORY_AIRPORT, Integer.valueOf(DistanceItem.AIRPORT));
            put(SupportedSeasons.SEASON_BEACH, Integer.valueOf(DistanceItem.BEACH));
            put(Poi.CATEGORY_METRO_STATION, Integer.valueOf(DistanceItem.METRO_STATION));
            put(Poi.CATEGORY_SKI, Integer.valueOf(DistanceItem.SKI_LIFT));
            put(Poi.CATEGORY_TRAIN_STATION, Integer.valueOf(DistanceItem.TRAIN_STATION));
            put(Poi.CATEGORY_SIGHT, Integer.valueOf(DistanceItem.PIN));
        }
    }

    public static class Factory {
        public static DistanceItem cityCenter(int distance, @NonNull String cityName, @NonNull Context context) {
            return new DistanceItem(distance, context.getString(C1178R.string.poi_city_center) + " (" + cityName + ")", DistanceItem.CITY_CENTER);
        }

        public static DistanceItem myLocation(int distance, @NonNull Context context) {
            return new DistanceItem(distance, context.getString(C1178R.string.poi_my_location), DistanceItem.MY_LOCATION);
        }

        @NonNull
        public static List<DistanceItem> uniqueFrom(@Nullable List<Poi> pois, @Nullable Map<Integer, Integer> poiDistance) {
            List<DistanceItem> items = new LinkedList();
            if (!(CollectionUtils.isEmpty(pois) || poiDistance == null || poiDistance.isEmpty())) {
                for (Poi poi : pois) {
                    if (poiDistance.containsKey(Integer.valueOf(poi.getId()))) {
                        int distance = ((Integer) poiDistance.get(Integer.valueOf(poi.getId()))).intValue();
                        String category = poi.getCategory();
                        if (DistanceItem.poiTypeToDistanceItem.containsKey(category)) {
                            items.add(new DistanceItem(distance, poi.getName(), ((Integer) DistanceItem.poiTypeToDistanceItem.get(category)).intValue()));
                        }
                    }
                }
                saveOneNearestItemOfEachType(items);
            }
            return items;
        }

        private static void saveOneNearestItemOfEachType(List<DistanceItem> items) {
            Collections.sort(items);
            Map<Integer, DistanceItem> map = new HashMap();
            for (DistanceItem item : items) {
                if (!map.containsKey(Integer.valueOf(item.poiType))) {
                    map.put(Integer.valueOf(item.poiType), item);
                }
            }
            items.clear();
            items.addAll(map.values());
        }

        @Nullable
        public static DistanceItem from(Poi poi, Coordinates hotelLocation) {
            if (!DistanceItem.poiTypeToDistanceItem.containsKey(poi.getCategory())) {
                return null;
            }
            return new DistanceItem((int) LocationUtils.distanceBetween(poi.getLocation(), hotelLocation), poi.getName(), ((Integer) DistanceItem.poiTypeToDistanceItem.get(poi.getCategory())).intValue());
        }
    }

    static {
        poiTypeToDistanceItem = new C13141();
    }

    public DistanceItem(int distance, @NonNull String poiName, int poiType) {
        this.distance = distance;
        this.poiName = poiName;
        this.poiType = poiType;
    }

    public int compareTo(@NonNull DistanceItem item) {
        return this.distance - item.distance;
    }
}
