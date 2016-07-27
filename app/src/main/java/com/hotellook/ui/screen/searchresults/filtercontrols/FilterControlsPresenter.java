package com.hotellook.ui.screen.searchresults.filtercontrols;

import android.view.View;

public interface FilterControlsPresenter {
    int additionalTopOffsetForMap();

    void attachView(View view);

    void onDetachFromView();

    void onFiltersChanged();

    void restoreState(Object obj);

    Object saveState();

    void switchToListControls(boolean z);

    void switchToMapControls(boolean z);
}
