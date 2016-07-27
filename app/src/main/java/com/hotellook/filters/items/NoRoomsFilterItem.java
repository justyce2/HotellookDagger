package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.criterion.HasPriceCriterion;
import com.hotellook.search.SearchData;
import com.hotellook.utils.CollectionUtils;
import java.util.List;
import java.util.Map;

public class NoRoomsFilterItem extends RoomBoolFilterItem {
    public NoRoomsFilterItem() {
        super(new HasPriceCriterion(), false);
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        super.setUp(searchData, persistentFilters);
    }

    @NonNull
    protected String saveTag() {
        return NoRoomsFilterItem.class.getSimpleName();
    }

    protected int calculateCount(@NonNull SearchData searchData) {
        int counter = 0;
        List<HotelData> hotels = searchData.hotels().all();
        Map<Long, List<Offer>> offersForHotels = searchData.offers().all();
        if (CollectionUtils.isNotEmpty(hotels)) {
            for (HotelData hotel : hotels) {
                if (CollectionUtils.isEmpty((List) offersForHotels.get(Long.valueOf(hotel.getId())))) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
