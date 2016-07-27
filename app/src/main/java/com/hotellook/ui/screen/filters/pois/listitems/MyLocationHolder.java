package com.hotellook.ui.screen.filters.pois.listitems;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.hotellook.C1178R;

public class MyLocationHolder extends ViewHolder {
    public final View clickable;
    public final TextView message;

    public MyLocationHolder(View itemView) {
        super(itemView);
        this.clickable = itemView;
        this.message = (TextView) itemView.findViewById(C1178R.id.message);
    }
}
