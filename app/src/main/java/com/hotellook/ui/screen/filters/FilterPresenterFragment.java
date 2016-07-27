package com.hotellook.ui.screen.filters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.events.FiltersResetEvent;
import com.hotellook.ui.screen.filters.presenters.FilterPresenter;
import com.hotellook.ui.screen.searchresults.ViewHorizontalAligner;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.ViewUtils;
import com.squareup.otto.Subscribe;

public class FilterPresenterFragment extends Fragment {
    private FilterPresenter presenter;
    private int tabsOffset;

    public static FilterPresenterFragment create(FilterPresenter presenter, int tabsHeight) {
        FilterPresenterFragment fragment = new FilterPresenterFragment();
        fragment.setFilterPresenter(presenter);
        fragment.setTabsOffset(tabsHeight);
        return fragment;
    }

    public void setFilterPresenter(FilterPresenter filterPresenter) {
        this.presenter = filterPresenter;
    }

    public void setTabsOffset(int tabsOffset) {
        this.tabsOffset = tabsOffset;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(C1178R.layout.fragment_filter_page, container, false);
        ViewGroup filtersContainer = (ViewGroup) v.findViewById(C1178R.id.filter_items_container);
        adjustOffsets(filtersContainer);
        this.presenter.addView(inflater, filtersContainer);
        if (AndroidUtils.isPhone(getContext())) {
            new ViewHorizontalAligner(getContext()).alignWithPaddings(filtersContainer);
        }
        HotellookApplication.eventBus().register(this);
        return v;
    }

    private void adjustOffsets(View layout) {
        ViewUtils.addTopPadding(layout, this.tabsOffset);
        if (AndroidUtils.isPhone(getContext())) {
            ViewUtils.addBottomPadding(layout, getResources().getDimensionPixelSize(C1178R.dimen.apply_filters_button_height));
        } else if (AndroidUtils.isPortrait(getContext())) {
            AndroidUtils.addPaddingToOffsetNavBarBottom(layout);
        }
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onFiltersReset(FiltersResetEvent event) {
        this.presenter.onFiltersUpdated();
    }

    @Subscribe
    public void onFiltersUpdated(FiltersChangedEvent event) {
        this.presenter.onFiltersUpdated();
    }
}
