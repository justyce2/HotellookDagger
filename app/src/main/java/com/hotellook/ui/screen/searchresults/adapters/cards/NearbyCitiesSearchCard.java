package com.hotellook.ui.screen.searchresults.adapters.cards;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.NearbyCitiesSearchRequestEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.SearchData;

public class NearbyCitiesSearchCard implements Card {
    private static final int MAX_HOTELS_FOR_NEARBY_CITIES_SEARCH = 25;
    private int position;
    private boolean showCard;

    /* renamed from: com.hotellook.ui.screen.searchresults.adapters.cards.NearbyCitiesSearchCard.1 */
    class C13771 extends MonkeySafeClickListener {
        C13771() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new NearbyCitiesSearchRequestEvent());
        }
    }

    public int position() {
        return this.position;
    }

    public int type() {
        return 8;
    }

    public ViewHolder createViewHolder(ViewGroup parent) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.nearby_cities_search_card, parent, false);
        layout.findViewById(C1178R.id.search_btn).setOnClickListener(new C13771());
        return new JustViewHolder(layout);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    public void notifyDataChanged() {
    }

    public void init(SearchData search, Filters filters, PersistentFilters persistentFilters) {
        int hotelsWithOffers = search.offers().all().size();
        if (search.searchParams().isDestinationTypeCity() && fitConditions(search, hotelsWithOffers)) {
            this.showCard = true;
        }
    }

    private boolean fitConditions(SearchData search, int hotelsWithOffers) {
        return search.nearbyCitiesAvailable() && fitQuantityBounds(search, hotelsWithOffers);
    }

    private boolean fitQuantityBounds(SearchData search, int hotelsWithOffers) {
        return offersInBounds(hotelsWithOffers) || hotelsInBounds(search, hotelsWithOffers);
    }

    private boolean hotelsInBounds(SearchData search, int hotelsWithOffers) {
        return hotelsWithOffers == 0 && search.hotels().all().size() < MAX_HOTELS_FOR_NEARBY_CITIES_SEARCH;
    }

    private boolean offersInBounds(int hotelsWithOffers) {
        return hotelsWithOffers > 0 && hotelsWithOffers < MAX_HOTELS_FOR_NEARBY_CITIES_SEARCH;
    }

    public void position(int position) {
        this.position = position;
    }

    public boolean isVisible() {
        return this.showCard;
    }
}
