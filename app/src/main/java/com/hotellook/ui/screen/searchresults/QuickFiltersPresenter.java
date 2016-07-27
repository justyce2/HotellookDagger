package com.hotellook.ui.screen.searchresults;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.events.FiltersApplyEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.items.PriceFilterItem;
import com.hotellook.ui.screen.filters.FilterApplySource;
import com.hotellook.ui.screen.filters.FilterOpenSource;
import com.hotellook.ui.screen.filters.presenters.FilterPresenter;
import com.hotellook.ui.screen.filters.presenters.PriceFilterPresenter;

public class QuickFiltersPresenter implements FilterPresenter {
    private final FilterPresenter priceFilterPresenter;

    /* renamed from: com.hotellook.ui.screen.searchresults.QuickFiltersPresenter.1 */
    class C13571 extends PriceFilterPresenter {
        final /* synthetic */ Filters val$filters;

        C13571(PriceFilterItem filterItem, Filters filters) {
            this.val$filters = filters;
            super(filterItem);
        }

        public void onStopTrackingTouch() {
            super.onStopTrackingTouch();
            HotellookApplication.eventBus().post(new FiltersApplyEvent(this.val$filters, FilterOpenSource.MAP, FilterApplySource.QUICK));
        }
    }

    public QuickFiltersPresenter(Filters filters) {
        this.priceFilterPresenter = new C13571(filters.getPriceFilterFilterItem(), filters);
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        this.priceFilterPresenter.addView(inflater, container);
        container.findViewById(C1178R.id.filter_price_root).setBackgroundColor(container.getResources().getColor(17170445));
    }

    public void onFiltersUpdated() {
        this.priceFilterPresenter.onFiltersUpdated();
    }
}
