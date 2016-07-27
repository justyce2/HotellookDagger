package com.hotellook.events;

import com.hotellook.ui.screen.filters.FilterOpenSource;

public class OpenFiltersEvent {
    public final FilterOpenSource source;

    public OpenFiltersEvent(FilterOpenSource source) {
        this.source = source;
    }
}
