package com.hotellook.ui.screen.hotel.reviews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.hotellook.core.api.pojo.hoteldetail.FoursquareData;
import com.hotellook.core.api.pojo.hoteldetail.TipData;
import com.hotellook.databinding.HotelReviewsViewBinding;
import com.hotellook.utils.CollectionUtils;

public class HotelReviewsView extends LinearLayout {
    private HotelReviewsViewBinding binding;

    public HotelReviewsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelReviewsViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@Nullable FoursquareData foursquareData) {
        if (foursquareData == null || CollectionUtils.isEmpty(foursquareData.getTips())) {
            setVisibility(8);
            return;
        }
        for (TipData review : foursquareData.getTips()) {
            HotelReviewsItemView itemView = HotelReviewsItemView.create(this.binding.reviews);
            itemView.bindTo(review);
            this.binding.reviews.addView(itemView);
        }
    }
}
