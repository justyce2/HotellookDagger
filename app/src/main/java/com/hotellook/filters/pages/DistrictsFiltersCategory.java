package com.hotellook.filters.pages;

import com.hotellook.core.api.pojo.common.District;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.DistrictFilterItem;
import com.hotellook.filters.items.FilterItem;
import com.hotellook.search.SearchData;
import com.hotellook.utils.CollectionUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DistrictsFiltersCategory extends ParallelHotelListFiltersCategory {

    private static class DistrictFilterItemComparator implements Comparator<FilterItem<HotelData>> {
        private DistrictFilterItemComparator() {
        }

        public int compare(FilterItem<HotelData> lhs, FilterItem<HotelData> rhs) {
            return ((DistrictFilterItem) lhs).getDistrictName().compareToIgnoreCase(((DistrictFilterItem) rhs).getDistrictName());
        }
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        removePageFilters();
        addDistrictsFilters(searchData);
        super.setUp(searchData, persistentFilters);
        removeEmptyFilters();
        Collections.sort(getFilters(), new DistrictFilterItemComparator());
    }

    private void addDistrictsFilters(SearchData searchData) {
        List<District> districts = searchData.districts().all();
        if (CollectionUtils.isNotEmpty(districts)) {
            for (District district : districts) {
                addFilter(new DistrictFilterItem(district.getNameInfo().getName(), district.getId()));
            }
        }
    }

    private void removeEmptyFilters() {
        Iterator<FilterItem<HotelData>> iterator = getFilters().iterator();
        while (iterator.hasNext()) {
            if (((DistrictFilterItem) ((FilterItem) iterator.next())).getCount() == 0) {
                iterator.remove();
            }
        }
    }
}
