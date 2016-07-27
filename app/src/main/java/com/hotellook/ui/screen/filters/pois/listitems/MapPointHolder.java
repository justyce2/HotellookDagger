package com.hotellook.ui.screen.filters.pois.listitems;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.hotellook.C1178R;

public class MapPointHolder extends ViewHolder {
    public final View clickable;

    public MapPointHolder(View itemView, @StringRes int pinOnMapId) {
        super(itemView);
        this.clickable = itemView;
        ((TextView) itemView.findViewById(C1178R.id.name)).setText(pinOnMapId);
    }
}
