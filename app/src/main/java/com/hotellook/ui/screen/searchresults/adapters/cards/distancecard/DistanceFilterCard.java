package com.hotellook.ui.screen.searchresults.adapters.cards.distancecard;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.searchresults.adapters.cards.Card;
import com.hotellook.ui.screen.searchresults.adapters.cards.JustViewHolder;

public class DistanceFilterCard implements Card {
    private final Context context;
    private Filters filters;
    private final DistanceFilterItem localDistanceFilterItem;
    private DistanceFilterItem originalDistanceFilterItem;
    private DistanceCardPresenter presenter;

    public DistanceFilterCard(Context context) {
        this.localDistanceFilterItem = new DistanceFilterItem();
        this.context = context;
    }

    public void init(SearchData search, Filters filters, PersistentFilters persistentFilters) {
        SearchParams searchParams = search.searchParams();
        if (searchParams.isDestinationTypeUserLocation() || searchParams.isDestinationTypeMapPoint() || searchParams.isDestinationTypeNearbyCities()) {
            this.originalDistanceFilterItem = filters.getGeneralPage().getDistanceFilterItem();
            this.localDistanceFilterItem.setTargetSelector(new CardTargetSelector(this.context));
            this.localDistanceFilterItem.setUp(search, persistentFilters);
            this.filters = filters;
            new InitialDistanceSetter().setUpInitDistance(this.localDistanceFilterItem, search);
            this.originalDistanceFilterItem.applyFrom(this.localDistanceFilterItem);
            filters.getSortingCategory().getSortingItem().setDistanceTarget(this.localDistanceFilterItem.getDistanceTarget());
        }
    }

    public int position() {
        return 0;
    }

    public int type() {
        return 6;
    }

    public ViewHolder createViewHolder(ViewGroup parent) {
        updateLocalFilterItem();
        this.presenter = new DistanceCardPresenter(this.context, this.localDistanceFilterItem, new DistanceCardDelegate(this.localDistanceFilterItem, this.originalDistanceFilterItem, this.filters));
        return new JustViewHolder(this.presenter.inflateView(LayoutInflater.from(parent.getContext()), C1178R.layout.filter_distance_top_card, parent));
    }

    private void updateLocalFilterItem() {
        if (sameFilterItems()) {
            this.localDistanceFilterItem.applyFrom(this.originalDistanceFilterItem);
        }
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    public void notifyDataChanged() {
        if (this.presenter != null) {
            if (sameFilterItems() && !this.originalDistanceFilterItem.inDefaultState()) {
                this.localDistanceFilterItem.applyFrom(this.originalDistanceFilterItem);
            }
            this.presenter.onFiltersUpdated();
        }
    }

    private boolean sameFilterItems() {
        return this.originalDistanceFilterItem.getDistanceTarget().equals(this.localDistanceFilterItem.getDistanceTarget());
    }
}
