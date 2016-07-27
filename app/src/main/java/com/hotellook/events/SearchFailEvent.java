package com.hotellook.events;

import com.hotellook.search.SearchParams;
import retrofit.RetrofitError;

public class SearchFailEvent extends NetworkFailEvent {
    public final SearchParams searchParams;

    public SearchFailEvent(SearchParams searchParams, RetrofitError error) {
        super(error);
        this.searchParams = searchParams;
    }
}
