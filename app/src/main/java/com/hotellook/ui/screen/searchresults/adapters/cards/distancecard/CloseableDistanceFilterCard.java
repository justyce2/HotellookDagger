package com.hotellook.ui.screen.searchresults.adapters.cards.distancecard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.search.SearchData;
import com.hotellook.ui.screen.searchresults.adapters.cards.Card;
import com.hotellook.ui.screen.searchresults.adapters.cards.JustViewHolder;

public class CloseableDistanceFilterCard implements Card {
    private final Context context;
    private boolean enabled;
    private Filters filters;
    private final DistanceFilterItem localDistanceFilterItem;
    private DistanceFilterItem originalDistanceFilterItem;
    private int position;
    @Nullable
    private DistanceCardPresenter presenter;

    public CloseableDistanceFilterCard(Context context) {
        this.localDistanceFilterItem = new DistanceFilterItem();
        this.enabled = true;
        this.context = context;
    }

    public void init(SearchData search, Filters filters, PersistentFilters persistentFilters) {
        this.originalDistanceFilterItem = filters.getGeneralPage().getDistanceFilterItem();
        this.localDistanceFilterItem.setTargetSelector(new CardTargetSelector(this.context));
        this.localDistanceFilterItem.setUp(search, persistentFilters);
        this.filters = filters;
        new InitialDistanceSetter().setUpInitDistance(this.localDistanceFilterItem, search);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int position() {
        return this.position;
    }

    public int type() {
        return 5;
    }

    public ViewHolder createViewHolder(ViewGroup parent) {
        updateLocalFilterItem();
        this.presenter = new DistanceCardPresenter(this.context, this.localDistanceFilterItem, new CloseableDistanceCardDelegate(this, this.localDistanceFilterItem, this.originalDistanceFilterItem, this.filters));
        return new JustViewHolder(this.presenter.inflateView(LayoutInflater.from(parent.getContext()), C1178R.layout.filter_distance_card, parent));
    }

    private void updateLocalFilterItem() {
        if (sameFilterItems() && !this.originalDistanceFilterItem.inDefaultState()) {
            this.localDistanceFilterItem.applyFrom(this.originalDistanceFilterItem);
        }
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    public void notifyDataChanged() {
        updateLocalFilterItem();
        if (this.presenter != null) {
            this.presenter.onFiltersUpdated();
        }
    }

    private boolean sameFilterItems() {
        return this.originalDistanceFilterItem.getDistanceTarget().equals(this.localDistanceFilterItem.getDistanceTarget());
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
