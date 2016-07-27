package com.hotellook.ui.screen.filters.presenters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.events.FiltersSortingChangedEvent;
import com.hotellook.filters.items.SortingItem;
import com.hotellook.filters.pages.SortingFiltersCategory;
import com.hotellook.ui.screen.filters.radiogroup.DistanceSortingFilterRadioButton;
import com.hotellook.ui.screen.filters.radiogroup.FiltersRadioGroup;
import com.hotellook.ui.screen.filters.radiogroup.FiltersRadioGroup.OnCheckedChangeListener;

public class SortingPresenter implements FilterPresenter, OnCheckedChangeListener {
    private DistanceSortingFilterRadioButton distanceSortingFilterRadioButton;
    private FiltersRadioGroup radioGroup;
    private final SortingItem sortingItem;

    public SortingPresenter(SortingFiltersCategory sortingPage) {
        this.sortingItem = sortingPage.getSortingItem();
    }

    public void onFiltersUpdated() {
        updateCheckGroup();
        this.distanceSortingFilterRadioButton.onFiltersUpdated();
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        container.addView(createView(inflater, container));
    }

    public View createView(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(C1178R.layout.filters_sorting, container, false);
        this.radioGroup = (FiltersRadioGroup) v.findViewById(C1178R.id.sorting_group_algo);
        this.distanceSortingFilterRadioButton = (DistanceSortingFilterRadioButton) v.findViewById(C1178R.id.sorting_distance);
        if (!this.sortingItem.hasDiscounts()) {
            v.findViewById(C1178R.id.sorting_discount).setVisibility(8);
        }
        updateCheckGroup();
        return v;
    }

    private void updateCheckGroup() {
        this.radioGroup.setOnClickListener(null);
        this.radioGroup.setOnCheckedChangeListener(null);
        this.radioGroup.setChecked(this.sortingItem.getAlgoId());
        this.radioGroup.setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(FiltersRadioGroup group, int checkedId) {
        this.sortingItem.setAlgoId(checkedId);
        HotellookApplication.eventBus().post(new FiltersSortingChangedEvent(this.sortingItem));
    }
}
