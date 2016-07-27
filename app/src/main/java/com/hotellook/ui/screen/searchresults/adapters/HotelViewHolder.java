package com.hotellook.ui.screen.searchresults.adapters;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.searchresults.SearchResultsItemView;

public class HotelViewHolder extends ViewHolder {
    public final View discountContainer;
    public final TextView discountTitle;
    public final SearchResultsItemView searchResultsItemView;

    public HotelViewHolder(View v) {
        super(v);
        this.searchResultsItemView = (SearchResultsItemView) v.findViewById(C1178R.id.sr_item_content);
        this.discountTitle = (TextView) v.findViewById(C1178R.id.discount_title);
        this.discountContainer = v.findViewById(C1178R.id.discount_container);
    }
}
