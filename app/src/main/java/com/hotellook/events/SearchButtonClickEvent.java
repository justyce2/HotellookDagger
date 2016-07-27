package com.hotellook.events;

import com.hotellook.search.SearchParams;

public class SearchButtonClickEvent {
    public final SearchParams params;

    public SearchButtonClickEvent(SearchParams params) {
        this.params = params;
    }
}
