package com.hotellook.filters.items;

import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.SearchData;

public interface BaseLogicItem {
    int _hashCode();

    boolean inDefaultState();

    void release();

    void reset();

    void restoreState(FiltersSnapshot filtersSnapshot);

    void saveState(FiltersSnapshot filtersSnapshot);

    void setUp(SearchData searchData, PersistentFilters persistentFilters);
}
