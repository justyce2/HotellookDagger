package com.hotellook.ui.screen.searchresults.adapters.cards;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.SearchData;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Card {
    public static final int EN_GATES = 4;
    public static final int FILTER_DISTANCE = 5;
    public static final int FILTER_DISTANCE_TOP = 6;
    public static final int FILTER_PRICE = 2;
    public static final int FILTER_RATING = 3;
    public static final int FILTER_RESET = 7;
    public static final int HOTEL = 1;
    public static final int NEARBY_CITIES_SEARCH = 8;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    ViewHolder createViewHolder(ViewGroup viewGroup);

    void init(SearchData searchData, Filters filters, PersistentFilters persistentFilters);

    void notifyDataChanged();

    void onBindViewHolder(ViewHolder viewHolder, int i);

    int position();

    int type();
}
