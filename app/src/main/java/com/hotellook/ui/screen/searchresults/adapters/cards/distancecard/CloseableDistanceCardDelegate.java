package com.hotellook.ui.screen.searchresults.adapters.cards.distancecard;

import com.hotellook.HotellookApplication;
import com.hotellook.events.FiltaerDistanceCardClosedEvent;
import com.hotellook.events.FiltersApplyEvent;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.events.RemoveCardEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.ui.screen.filters.FilterApplySource;
import com.hotellook.ui.screen.filters.FilterOpenSource;

public class CloseableDistanceCardDelegate implements DistanceCardUserActionDelegate {
    private final CloseableDistanceFilterCard closeableDistanceFilterCard;
    private final DistanceFilterItem distanceFilterItem;
    private final Filters filters;
    private final DistanceFilterItem targetFilterItem;

    public CloseableDistanceCardDelegate(CloseableDistanceFilterCard closeableDistanceFilterCard, DistanceFilterItem distanceFilterItem, DistanceFilterItem targetFilterItem, Filters filters) {
        this.closeableDistanceFilterCard = closeableDistanceFilterCard;
        this.distanceFilterItem = distanceFilterItem;
        this.targetFilterItem = targetFilterItem;
        this.filters = filters;
    }

    public void onApplyClick() {
        this.closeableDistanceFilterCard.setEnabled(false);
        this.targetFilterItem.applyFrom(this.distanceFilterItem);
        this.filters.getSortingCategory().getSortingItem().setDistanceTarget(this.distanceFilterItem.getDistanceTarget());
        HotellookApplication.eventBus().post(new RemoveCardEvent(this.closeableDistanceFilterCard.position()));
        HotellookApplication.eventBus().post(new FiltersChangedEvent());
        HotellookApplication.eventBus().post(new FiltersApplyEvent(this.filters, FilterOpenSource.LIST, FilterApplySource.RESULTS));
    }

    public void onCloseClick() {
        this.closeableDistanceFilterCard.setEnabled(false);
        HotellookApplication.eventBus().post(new FiltaerDistanceCardClosedEvent());
        HotellookApplication.eventBus().post(new RemoveCardEvent(this.closeableDistanceFilterCard.position()));
    }

    public void onStopTrackingTouch(double selectedMaxValue) {
    }
}
