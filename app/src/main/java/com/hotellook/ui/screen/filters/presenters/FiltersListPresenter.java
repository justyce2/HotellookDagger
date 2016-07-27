package com.hotellook.ui.screen.filters.presenters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

public class FiltersListPresenter implements FilterPresenter {
    private final List<FilterPresenter> presenterList;

    public FiltersListPresenter(List<FilterPresenter> presenterList) {
        this.presenterList = presenterList;
    }

    public void onFiltersUpdated() {
        for (FilterPresenter presenter : this.presenterList) {
            presenter.onFiltersUpdated();
        }
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        for (FilterPresenter presenter : this.presenterList) {
            presenter.addView(inflater, container);
        }
    }
}
