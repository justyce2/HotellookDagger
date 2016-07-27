package com.hotellook.ui.screen.filters.pois.adapter;

import android.support.v7.widget.RecyclerView.ViewHolder;

public interface ListItemBinder {
    int getType();

    void onBindViewHolder(ViewHolder viewHolder, int i);
}
