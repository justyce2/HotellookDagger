package com.hotellook.ui.screen.searchresults.filtercontrols;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class TabletFiltersControlsPresenter$$Lambda$1 implements Runnable {
    private final TabletFiltersControlsPresenter arg$1;

    private TabletFiltersControlsPresenter$$Lambda$1(TabletFiltersControlsPresenter tabletFiltersControlsPresenter) {
        this.arg$1 = tabletFiltersControlsPresenter;
    }

    public static Runnable lambdaFactory$(TabletFiltersControlsPresenter tabletFiltersControlsPresenter) {
        return new TabletFiltersControlsPresenter$$Lambda$1(tabletFiltersControlsPresenter);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onOpenDestinationPicker$0();
    }
}
