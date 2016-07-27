package com.hotellook.ui.screen.searchresults.adapters.animations;

import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

public abstract class AnimateViewHolder extends ViewHolder {
    public abstract void animateAddImpl(ViewPropertyAnimatorListener viewPropertyAnimatorListener);

    public abstract void animateRemoveImpl(ViewPropertyAnimatorListener viewPropertyAnimatorListener);

    public AnimateViewHolder(View itemView) {
        super(itemView);
    }

    public void preAnimateAddImpl() {
    }

    public void preAnimateRemoveImpl() {
    }
}
