package com.hotellook.filters.pages;

import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.items.FilterItem;
import com.hotellook.filters.items.criterion.CriterionSet;
import com.hotellook.filters.items.criterion.ParallelCriterionSet;
import com.hotellook.filters.task.FilterTask;

public class ParallelRoomListFiltersCategory extends ListFiltersCategory<Offer> {
    public void addFilterLogic(FilterTask logic) {
        CriterionSet<Offer> criteriaSet = new ParallelCriterionSet();
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
