package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.search.SearchData;
import com.hotellook.utils.CollectionUtils;
import java.util.List;
import java.util.Map;

public abstract class RoomBoolFilterItem extends BoolFilterItem<Offer> {
    public RoomBoolFilterItem(@NonNull Criterion<Offer> criterion) {
        super(criterion);
    }

    public RoomBoolFilterItem(@NonNull Criterion<Offer> criterion, boolean defaultState) {
        super(criterion, defaultState);
    }

    protected int calculateCount(@NonNull SearchData data) {
        int counter = 0;
        Criterion<Offer> criterion = getCriterion();
        List<HotelData> hotels = data.hotels().all();
        Map<Long, List<Offer>> offersForHotels = data.offers().all();
        if (CollectionUtils.isNotEmpty(hotels)) {
            for (HotelData hotel : hotels) {
                List<Offer> offers = (List) offersForHotels.get(Long.valueOf(hotel.getId()));
                if (CollectionUtils.isNotEmpty(offers)) {
                    for (Offer offer : offers) {
                        if (criterion.passes(offer)) {
                            counter++;
                            break;
                        }
                    }
                }
            }
        }
        return counter;
    }
}
