package com.hotellook.ui.screen.hotel.prices;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.hotellook.ui.view.SearchFormView;

class SearchFormItemHolder extends ViewHolder {
    public final SearchFormView searchForm;

    public SearchFormItemHolder(View searchFormContainer) {
        super(searchFormContainer);
        this.searchForm = (SearchFormView) searchFormContainer.findViewById(OffersFragment.SEARCH_FORM_ID);
    }
}
