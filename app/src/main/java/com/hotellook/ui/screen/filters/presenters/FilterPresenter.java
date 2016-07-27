package com.hotellook.ui.screen.filters.presenters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public interface FilterPresenter {
    void addView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    void onFiltersUpdated();
}
