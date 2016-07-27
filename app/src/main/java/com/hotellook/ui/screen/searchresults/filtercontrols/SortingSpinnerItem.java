package com.hotellook.ui.screen.searchresults.filtercontrols;

import android.support.annotation.IdRes;

public class SortingSpinnerItem {
    @IdRes
    public final int id;
    public final String title;

    SortingSpinnerItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String toString() {
        return this.title;
    }
}
