package com.hotellook.ui.screen.filters.pois.listitems;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.hotellook.C1178R;

public class SimpleItemHolder extends ViewHolder {
    public final View bottomDivider;
    public final View clickable;
    public final TextView title;

    public SimpleItemHolder(View itemView) {
        super(itemView);
        this.clickable = itemView;
        this.title = (TextView) itemView.findViewById(C1178R.id.name);
        this.bottomDivider = itemView.findViewById(C1178R.id.divider);
    }
}
