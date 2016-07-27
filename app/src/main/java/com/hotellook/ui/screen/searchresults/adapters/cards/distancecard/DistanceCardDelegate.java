package com.hotellook.ui.screen.searchresults.adapters.cards.distancecard;

import com.hotellook.HotellookApplication;
import com.hotellook.events.FiltaerDistanceCardClosedEvent;
import com.hotellook.events.FiltersApplyEvent;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.ui.screen.filters.FilterApplySource;
import com.hotellook.ui.screen.filters.FilterOpenSource;

public class DistanceCardDelegate implements DistanceCardUserActionDelegate {
    private final DistanceFilterItem distanceFilterItem;
    private final Filters filters;
    private final DistanceFilterItem targetFilterItem;

    public DistanceCardDelegate(DistanceFilterItem distanceFilterItem, DistanceFilterItem targetFilterItem, Filters filters) {
        this.distanceFilterItem = distanceFilterItem;
        this.targetFilterItem = targetFilterItem;
        this.filters = filters;
    }

    public void onStopTrackingTouch(double selectedValue) {
        this.distanceFilterItem.setValue(selectedValue);
        this.targetFilterItem.applyFrom(this.distanceFilterItem);
        this.filters.getSortingCategory().getSortingItem().setDistanceTarget(this.distanceFilterItem.getDistanceTarget());
        HotellookApplication.eventBus().post(new FiltersChangedEvent());
        HotellookApplication.eventBus().post(new FiltersApplyEvent(this.filters, FilterOpenSource.LIST, FilterApplySource.RESULTS));
    }

    public void onApplyClick() {
        HotellookApplication.eventBus().post(new FiltersApplyEvent(this.filters, FilterOpenSource.LIST, FilterApplySource.RESULTS));
    }

    public void onCloseClick() {
        HotellookApplication.eventBus().post(new FiltaerDistanceCardClosedEvent());
    }
}
