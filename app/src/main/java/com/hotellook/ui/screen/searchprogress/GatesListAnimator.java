package com.hotellook.ui.screen.searchprogress;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.hotellook.ui.screen.searchresults.adapters.animations.HotelListItemAnimator;

public class GatesListAnimator extends HotelListItemAnimator {
    public GatesListAnimator() {
        setAddDuration(250);
    }

    protected void preAnimateAddImpl(ViewHolder holder) {
        ViewCompat.setTranslationY(holder.itemView, (float) holder.itemView.getHeight());
        ViewCompat.setAlpha(holder.itemView, 0.0f);
    }
}
