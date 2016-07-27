package com.hotellook.ui.screen.information;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.BoolFilterItem;
import com.hotellook.filters.persistant.PersistentCheckboxFilterPresenter;

public class PersistentFiltersGroupPresenter {
    @Nullable
    private final Filters filters;
    private final PersistentFilters persistentFilters;

    private static class NullBoolFilterItem extends BoolFilterItem {
        public NullBoolFilterItem() {
            super(PersistentFiltersGroupPresenter$NullBoolFilterItem$$Lambda$1.lambdaFactory$());
        }

        @NonNull
        protected String saveTag() {
            return null;
        }
    }

    public PersistentFiltersGroupPresenter(@Nullable Filters filters, PersistentFilters persistentFilters) {
        this.filters = filters;
        this.persistentFilters = persistentFilters;
    }

    public void addViews(ViewGroup container) {
        createHostelsPresenter().addView(LayoutInflater.from(container.getContext()), container);
    }

    private PersistentCheckboxFilterPresenter createHostelsPresenter() {
        return new PersistentCheckboxFilterPresenter(this.persistentFilters.hostelFilterIteml(), this.filters == null ? new NullBoolFilterItem() : this.filters.getGeneralPage().getHostelsAndGuesthousesFilterItem(), C1178R.string.allways_hide_hostels_and_guesthouses);
    }
}
