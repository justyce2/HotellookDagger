package com.hotellook.ui.screen.hotel.rating;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.hoteldetail.ScoreData;
import com.hotellook.databinding.HotelRatingItemViewBinding;
import com.hotellook.ui.screen.hotel.ratingcolorizer.BackgroundColorApplier;
import com.hotellook.ui.screen.hotel.ratingcolorizer.RatingColorizer;
import com.hotellook.utils.ValueFormat;

public class HotelRatingItemView extends LinearLayout {
    private HotelRatingItemViewBinding binding;

    public HotelRatingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    public static HotelRatingItemView create(@NonNull ViewGroup parent) {
        return (HotelRatingItemView) LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.hotel_rating_item_view, parent, false);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelRatingItemViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@NonNull ScoreData data) {
        int rating = data.getScore();
        this.binding.rating.setText(ValueFormat.ratingToString(rating));
        new RatingColorizer(new BackgroundColorApplier(), getResources()).apply(this.binding.rating, rating);
        this.binding.text.setText(data.getName());
    }
}
