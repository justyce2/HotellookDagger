package com.hotellook.events;

import com.hotellook.filters.pages.SortingFiltersCategory;

public class OpenSortingMenuEvent {
    public final SortingFiltersCategory sortingCategory;

    public OpenSortingMenuEvent(SortingFiltersCategory sortingCategory) {
        this.sortingCategory = sortingCategory;
    }
}
