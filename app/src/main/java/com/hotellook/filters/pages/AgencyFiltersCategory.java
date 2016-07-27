package com.hotellook.filters.pages;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.core.api.pojo.search.OptionsData;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.AgencyFilterItem;
import com.hotellook.filters.items.BoolFilterItem;
import com.hotellook.filters.items.FilterItem;
import com.hotellook.filters.items.HotelWebsiteAgencyFilterItem;
import com.hotellook.search.SearchData;
import com.hotellook.utils.CompareUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AgencyFiltersCategory extends ParallelRoomListFiltersCategory {

    private static class AgencyFilterItemComparator implements Comparator<FilterItem<Offer>> {
        private AgencyFilterItemComparator() {
        }

        public int compare(FilterItem<Offer> lhs, FilterItem<Offer> rhs) {
            return CompareUtils.compare(((BoolFilterItem) rhs).getCount(), ((BoolFilterItem) lhs).getCount());
        }
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        super.removePageFilters();
        boolean hasHotelWebsiteAgency = false;
        for (Offer offer : uniqueGates(searchData).values()) {
            if (isHotelWebsiteAgency(offer)) {
                hasHotelWebsiteAgency = true;
            } else {
                addFilter(new AgencyFilterItem(offer.getGateName()));
            }
        }
        super.setUp(searchData, persistentFilters);
        Collections.sort(getFilters(), new AgencyFilterItemComparator());
        if (hasHotelWebsiteAgency) {
            HotelWebsiteAgencyFilterItem hotelWebsiteAgencyFilterItem = new HotelWebsiteAgencyFilterItem();
            hotelWebsiteAgencyFilterItem.setUp(searchData, persistentFilters);
            addFilter(hotelWebsiteAgencyFilterItem);
        }
    }

    @NonNull
    private Map<String, Offer> uniqueGates(@NonNull SearchData searchData) {
        Map<String, Offer> uniqueGates = new LinkedHashMap();
        for (List<Offer> prices : searchData.offers().all().values()) {
            for (Offer price : prices) {
                uniqueGates.put(price.getGateName(), price);
            }
        }
        return uniqueGates;
    }

    private boolean isHotelWebsiteAgency(@NonNull Offer price) {
        OptionsData options = price.getOptions();
        return options != null && options.hasHotelWebsite();
    }
}
