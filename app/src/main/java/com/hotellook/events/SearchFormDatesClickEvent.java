package com.hotellook.events;

import com.hotellook.api.data.SearchFormData;

public class SearchFormDatesClickEvent {
    public final SearchFormData searchFormData;

    public SearchFormDatesClickEvent(SearchFormData searchFormData) {
        this.searchFormData = searchFormData;
    }
}
