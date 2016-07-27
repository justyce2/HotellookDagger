package com.hotellook.events;

import com.hotellook.filters.items.SortingItem;

public class FiltersSortingChangedEvent {
    public final SortingItem sortingItem;

    public FiltersSortingChangedEvent(SortingItem sortingItem) {
        this.sortingItem = sortingItem;
    }
}
