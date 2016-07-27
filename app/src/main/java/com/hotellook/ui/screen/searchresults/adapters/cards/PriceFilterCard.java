package com.hotellook.ui.screen.searchresults.adapters.cards;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.events.FiltersApplyEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.PriceFilterItem;
import com.hotellook.search.SearchData;
import com.hotellook.ui.screen.filters.FilterApplySource;
import com.hotellook.ui.screen.filters.FilterOpenSource;
import com.hotellook.ui.screen.filters.presenters.PriceFilterPresenter;

public class PriceFilterCard implements Card {
    private PriceFilterPresenter presenter;

    private static class PriceFilterPresenterWithStatistics extends PriceFilterPresenter {
        private final Filters filters;

        public PriceFilterPresenterWithStatistics(PriceFilterItem filterItem, Filters filters) {
            super(filterItem);
            this.filters = filters;
        }

        public void onStopTrackingTouch() {
            super.onStopTrackingTouch();
            HotellookApplication.eventBus().post(new FiltersApplyEvent(this.filters, FilterOpenSource.LIST, FilterApplySource.RESULTS));
        }
    }

    public int position() {
        return 0;
    }

    public int type() {
        return 2;
    }

    public ViewHolder createViewHolder(ViewGroup parent) {
        return new JustViewHolder(this.presenter.inflateView(LayoutInflater.from(parent.getContext()), C1178R.layout.filter_price_card, parent));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    public void notifyDataChanged() {
        this.presenter.onFiltersUpdated();
    }

    public void init(SearchData search, Filters filters, PersistentFilters persistentFilters) {
        this.presenter = new PriceFilterPresenterWithStatistics(filters.getGeneralPage().getPriceFilterItem(), filters);
    }
}
