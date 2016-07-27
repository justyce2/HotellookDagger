package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.filters.items.criterion.HostelOrGuesthouseCriterion;
import com.hotellook.filters.items.criterion.NoHostelAndNoGuesthouseCriterion;
import com.hotellook.search.SearchData;
import com.hotellook.utils.CollectionUtils;
import java.util.List;

public class HostelsAndGuesthousesFilterItem extends HotelBoolFilterItem {
    public HostelsAndGuesthousesFilterItem() {
        super(new NoHostelAndNoGuesthouseCriterion(), false);
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        super.setUp(searchData, persistentFilters);
        setChecked(persistentFilters.hostelFilterIteml().enabled());
    }

    @NonNull
    protected String saveTag() {
        return HostelsAndGuesthousesFilterItem.class.getSimpleName();
    }

    protected int calculateCount(@NonNull SearchData searchData) {
        int counter = 0;
        List<HotelData> hotels = searchData.hotels().all();
        Criterion<HotelData> criterion = new HostelOrGuesthouseCriterion();
        if (CollectionUtils.isNotEmpty(hotels)) {
            for (HotelData hotel : hotels) {
                if (criterion.passes(hotel)) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
