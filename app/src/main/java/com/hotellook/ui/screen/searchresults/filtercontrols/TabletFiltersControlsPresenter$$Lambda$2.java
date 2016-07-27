package com.hotellook.ui.screen.searchresults.filtercontrols;

import com.hotellook.filters.items.SortingItem;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class TabletFiltersControlsPresenter$$Lambda$2 implements Runnable {
    private final TabletFiltersControlsPresenter arg$1;
    private final SortingItem arg$2;

    private TabletFiltersControlsPresenter$$Lambda$2(TabletFiltersControlsPresenter tabletFiltersControlsPresenter, SortingItem sortingItem) {
        this.arg$1 = tabletFiltersControlsPresenter;
        this.arg$2 = sortingItem;
    }

    public static Runnable lambdaFactory$(TabletFiltersControlsPresenter tabletFiltersControlsPresenter, SortingItem sortingItem) {
        return new TabletFiltersControlsPresenter$$Lambda$2(tabletFiltersControlsPresenter, sortingItem);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$setUpSortingSpinner$1(this.arg$2);
    }
}
