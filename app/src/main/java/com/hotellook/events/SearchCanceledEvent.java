package com.hotellook.events;

import com.hotellook.search.SearchParams;

public class SearchCanceledEvent {
    public final SearchParams searchParams;

    public SearchCanceledEvent(SearchParams searchParams) {
        this.searchParams = searchParams;
    }
}
