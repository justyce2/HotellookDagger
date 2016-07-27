package com.hotellook.ui.screen.filters.pois.listitems;

import android.support.v7.widget.RecyclerView.ViewHolder;
import com.hotellook.ui.screen.filters.pois.adapter.ListItemBinder;

public class TitleListItem implements ListItemBinder {
    private final String title;

    public TitleListItem(String title) {
        this.title = title;
    }

    public int getType() {
        return 0;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ((TitleHolder) holder).setTitle(this.title);
    }
}
