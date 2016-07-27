package com.hotellook.filters.pages;

import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.items.FilterItem;
import com.hotellook.filters.items.criterion.CriterionSet;
import com.hotellook.filters.items.criterion.SequentialCriterionSet;
import com.hotellook.filters.task.FilterTask;

public class SequentalRoomListFiltersCategory extends ListFiltersCategory<Offer> {
    public void addFilterLogic(FilterTask logic) {
        CriterionSet<Offer> criteriaSet = new SequentialCriterionSet();
        for (FilterItem<Offer> filter : getFilters()) {
            if (filter.enabled()) {
                criteriaSet.add(filter.newCriterion());
            }
        }
        if (criteriaSet.hasCriteria()) {
            logic.addRoomFilter(criteriaSet);
        }
    }
}
