package com.hotellook.ui.screen.filters.pois.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import java.util.List;

public class FixedTypeAdapter extends Adapter {
    private final List<ListItemBinder> listItemBinders;
    private final ViewHolderCreator viewHolderCreator;

    public FixedTypeAdapter(ViewHolderCreator viewHolderCreator, List<ListItemBinder> listItemBinders) {
        this.viewHolderCreator = viewHolderCreator;
        this.listItemBinders = listItemBinders;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return this.viewHolderCreator.onCreateViewHolder(parent, viewType);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ((ListItemBinder) this.listItemBinders.get(position)).onBindViewHolder(holder, position);
    }

    public int getItemViewType(int position) {
        return ((ListItemBinder) this.listItemBinders.get(position)).getType();
    }

    public int getItemCount() {
        return this.listItemBinders.size();
    }
}
