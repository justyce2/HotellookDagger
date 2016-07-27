package com.hotellook.filters.pages;

import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.FilterItem;
import com.hotellook.search.SearchData;
import java.util.ArrayList;
import java.util.List;

public abstract class ListFiltersCategory<T> implements FiltersCategory {
    private final List<FilterItem<T>> filters;

    public ListFiltersCategory() {
        this.filters = new ArrayList();
    }

    public void addFilter(FilterItem<T> filterItem) {
        this.filters.add(filterItem);
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        for (FilterItem<T> filter : this.filters) {
            filter.setUp(searchData, persistentFilters);
        }
    }

    public void reset() {
        for (FilterItem<T> filter : this.filters) {
            filter.reset();
        }
    }

    public boolean inDefaultState() {
        for (FilterItem<T> filter : this.filters) {
            if (!filter.inDefaultState()) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        return this.filters.hashCode();
    }

    public List<FilterItem<T>> getFilters() {
        return this.filters;
    }

    public void saveState(FiltersSnapshot snapshot) {
        for (FilterItem<T> item : this.filters) {
            item.saveState(snapshot);
        }
    }

    public void restoreState(FiltersSnapshot snapshot) {
        for (FilterItem<T> item : this.filters) {
            item.restoreState(snapshot);
        }
    }

    public void removePageFilters() {
        this.filters.clear();
    }

    public void release() {
        for (FilterItem<T> filter : this.filters) {
            filter.release();
        }
    }
}
