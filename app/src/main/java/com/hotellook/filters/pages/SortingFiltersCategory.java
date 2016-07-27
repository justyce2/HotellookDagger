package com.hotellook.filters.pages;

import com.hotellook.C1178R;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.SortingItem;
import com.hotellook.filters.task.FilterTask;
import com.hotellook.search.SearchData;

public class SortingFiltersCategory implements FiltersCategory {
    private final SortingItem sortingItem;

    public SortingFiltersCategory() {
        this.sortingItem = new SortingItem();
    }

    public int getAlgoId() {
        return this.sortingItem.getAlgoId();
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        this.sortingItem.setUp(searchData, persistentFilters);
    }

    public void reset() {
        this.sortingItem.reset();
    }

    public void addFilterLogic(FilterTask logic) {
        logic.setComparatorProducer(this.sortingItem);
    }

    public boolean inDefaultState() {
        return this.sortingItem.inDefaultState();
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        return this.sortingItem._hashCode();
    }

    public void saveState(FiltersSnapshot snapshot) {
        this.sortingItem.saveState(snapshot);
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.sortingItem.restoreState(snapshot);
    }

    public void release() {
        this.sortingItem.release();
    }

    public boolean isPriceSorting() {
        return this.sortingItem.getAlgoId() == C1178R.id.sorting_price;
    }

    public SortingItem getSortingItem() {
        return this.sortingItem;
    }
}
