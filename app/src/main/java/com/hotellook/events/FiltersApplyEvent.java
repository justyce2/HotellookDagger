package com.hotellook.events;

import com.hotellook.filters.Filters;
import com.hotellook.ui.screen.filters.FilterApplySource;
import com.hotellook.ui.screen.filters.FilterOpenSource;

public class FiltersApplyEvent {
    public final FilterApplySource filterSource;
    public final FilterOpenSource filterType;
    public final Filters filters;

    public FiltersApplyEvent(Filters filters, FilterOpenSource filterType, FilterApplySource filterSource) {
        this.filters = filters;
        this.filterType = filterType;
        this.filterSource = filterSource;
    }
}
