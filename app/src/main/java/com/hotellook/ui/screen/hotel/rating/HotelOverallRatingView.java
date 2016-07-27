package com.hotellook.ui.screen.hotel.rating;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.hoteldetail.TrustYouData;
import com.hotellook.databinding.HotelOverallRatingViewBinding;
import com.hotellook.ui.screen.hotel.ratingcolorizer.BackgroundColorApplier;
import com.hotellook.ui.screen.hotel.ratingcolorizer.RatingColorizer;
import com.hotellook.ui.screen.hotel.ratingcolorizer.TextColorApplier;
import com.hotellook.utils.StringUtils;
import com.hotellook.utils.ValueFormat;

public class HotelOverallRatingView extends LinearLayout {
    private HotelOverallRatingViewBinding binding;

    public HotelOverallRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelOverallRatingViewBinding) DataBindingUtil.bind(this);
        }
    }

    @SuppressLint({"SetTextI18n"})
    public void bindTo(@NonNull TrustYouData data) {
        int rating = data.getHotelSummary().getScore();
        this.binding.rating.setText(ValueFormat.ratingToString(rating));
        this.binding.text.setText(data.getHotelSummary().getText());
        int reviewsCount = data.getReviewsCount();
        String reviewsCountStr = StringUtils.toStringWithDelimiter(reviewsCount);
        this.binding.title.setText(data.getHotelSummary().getScoreDescription() + " (" + getResources().getQuantityString(C1178R.plurals.reviews_count, reviewsCount, new Object[]{reviewsCountStr}) + ")");
        new RatingColorizer(new TextColorApplier(), getResources()).apply(this.binding.title, rating);
        new RatingColorizer(new BackgroundColorApplier(), getResources()).apply(this.binding.rating, rating);
    }
}
