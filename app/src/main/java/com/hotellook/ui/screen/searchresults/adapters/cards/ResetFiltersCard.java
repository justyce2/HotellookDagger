package com.hotellook.ui.screen.searchresults.adapters.cards;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.SearchData;

public class ResetFiltersCard implements Card {
    private static final int POSITION = 1;
    private Filters filters;

    /* renamed from: com.hotellook.ui.screen.searchresults.adapters.cards.ResetFiltersCard.1 */
    class C13781 extends MonkeySafeClickListener {
        C13781() {
        }

        public void onSafeClick(View v) {
            ResetFiltersCard.this.filters.reset();
        }
    }

    public int position() {
        return POSITION;
    }

    public int type() {
        return 7;
    }

    public ViewHolder createViewHolder(ViewGroup parent) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.filter_reset_card, parent, false);
        layout.findViewById(C1178R.id.reset_btn).setOnClickListener(new C13781());
        return new JustViewHolder(layout);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    public void notifyDataChanged() {
    }

    public void init(SearchData search, Filters filters, PersistentFilters persistentFilters) {
        this.filters = filters;
    }
}
