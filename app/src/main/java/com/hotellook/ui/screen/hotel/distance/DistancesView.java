package com.hotellook.ui.screen.hotel.distance;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import com.hotellook.databinding.DistancesViewBinding;
import java.util.List;

public class DistancesView extends LinearLayout {
    private DistancesViewBinding binding;

    public DistancesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (DistancesViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@NonNull List<DistanceItem> items) {
        this.binding.distances.removeAllViews();
        for (DistanceItem item : items) {
            DistanceItemView itemView = DistanceItemView.create(this.binding.distances);
            itemView.bindTo(item);
            this.binding.distances.addView(itemView);
            if (this.binding.distances.getChildCount() == items.size()) {
                ((MarginLayoutParams) itemView.getLayoutParams()).bottomMargin = 0;
            }
        }
    }
}
