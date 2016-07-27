package com.hotellook.filters.pages;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.items.FilterItem;
import com.hotellook.filters.items.criterion.CriterionSet;
import com.hotellook.filters.items.criterion.SequentialCriterionSet;
import com.hotellook.filters.task.FilterTask;

public class SequentalHotelListFiltersCategory extends ListFiltersCategory<HotelData> {
    public void addFilterLogic(FilterTask logic) {
        CriterionSet<HotelData> criteriaSet = new SequentialCriterionSet();
        for (FilterItem<HotelData> filter : getFilters()) {
            if (filter.enabled()) {
                criteriaSet.add(filter.newCriterion());
            }
        }
        if (criteriaSet.hasCriteria()) {
            logic.addHotelFilter(criteriaSet);
        }
    }
}
