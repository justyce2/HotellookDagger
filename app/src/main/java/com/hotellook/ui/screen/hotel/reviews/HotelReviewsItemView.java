package com.hotellook.ui.screen.hotel.reviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.hoteldetail.TipData;
import com.hotellook.databinding.HotelReviewsItemViewBinding;
import com.hotellook.utils.ValueFormat;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class HotelReviewsItemView extends RelativeLayout {
    private HotelReviewsItemViewBinding binding;

    public HotelReviewsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    public static HotelReviewsItemView create(@NonNull ViewGroup parent) {
        return (HotelReviewsItemView) LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.hotel_reviews_item_view, parent, false);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelReviewsItemViewBinding) DataBindingUtil.bind(this);
        }
    }

    @SuppressLint({"SetTextI18n"})
    public void bindTo(@NonNull TipData review) {
        this.binding.name.setText(review.getUsername() + (TextUtils.isEmpty(review.getUserlastname()) ? BuildConfig.FLAVOR : " " + review.getUserlastname()) + ",");
        this.binding.date.setText(ValueFormat.reviewDateToString(getContext(), review.getCreatedAt()));
        this.binding.text.setText(review.getText());
    }
}
