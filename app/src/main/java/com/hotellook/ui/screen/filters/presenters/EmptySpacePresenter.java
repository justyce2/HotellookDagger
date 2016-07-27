package com.hotellook.ui.screen.filters.presenters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hotellook.C1178R;

public class EmptySpacePresenter implements FilterPresenter {
    public void onFiltersUpdated() {
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(C1178R.layout.filter_item_space_separator, container, true);
    }
}
