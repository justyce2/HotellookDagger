package com.hotellook.ui.screen.searchresults.adapters.animations;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class HotelListItemAnimator extends BaseItemAnimator {
    private Interpolator inInterpolator;
    private Interpolator outInterpolator;

    public HotelListItemAnimator() {
        this.outInterpolator = new AccelerateInterpolator();
        this.inInterpolator = new DecelerateInterpolator();
        setRemoveDuration(100);
        setAddDuration(200);
    }

    protected void animateRemoveImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).alpha(0.0f).setDuration(getRemoveDuration()).setInterpolator(this.outInterpolator).setListener(new DefaultRemoveVpaListener(holder)).setStartDelay(getRemoveDelay(holder)).start();
    }

    protected void preAnimateAddImpl(ViewHolder holder) {
        ViewCompat.setTranslationY(holder.itemView, ((float) holder.itemView.getHeight()) * 0.3f);
        ViewCompat.setAlpha(holder.itemView, 0.0f);
    }

    protected void animateAddImpl(ViewHolder holder) {
        ViewCompat.animate(holder.itemView).translationY(0.0f).alpha(1.0f).setDuration(getAddDuration()).setInterpolator(this.inInterpolator).setListener(new DefaultAddVpaListener(holder)).setStartDelay(getAddDelay(holder)).start();
    }
}
