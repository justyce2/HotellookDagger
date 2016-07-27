package com.hotellook.ui.screen.hotel.rating;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.hotellook.core.api.pojo.hoteldetail.ScoreData;
import com.hotellook.databinding.HotelRatingsViewBinding;
import com.hotellook.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

public class HotelRatingsView extends LinearLayout {
    private HotelRatingsViewBinding binding;

    public HotelRatingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelRatingsViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@Nullable List<ScoreData> ratings) {
        if (CollectionUtils.isEmpty(ratings)) {
            setVisibility(8);
            return;
        }
        List views = new ArrayList(ratings.size());
        for (ScoreData rating : ratings) {
            HotelRatingItemView itemView = HotelRatingItemView.create(this.binding.ratings);
            itemView.bindTo(rating);
            views.add(itemView);
        }
        this.binding.ratings.add(views);
    }
}
