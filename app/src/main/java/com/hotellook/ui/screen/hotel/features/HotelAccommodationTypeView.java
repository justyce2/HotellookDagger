package com.hotellook.ui.screen.hotel.features;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import com.hotellook.databinding.HotelAccommodationTypeViewBinding;
import com.hotellook.utils.StringUtils;

public class HotelAccommodationTypeView extends LinearLayout {
    private HotelAccommodationTypeViewBinding binding;

    public HotelAccommodationTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelAccommodationTypeViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@NonNull String accommodationType, int adultsCount, int childrenCount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(StringUtils.capitalize(accommodationType));
        if (adultsCount > 0) {
            stringBuilder.append(", ").append(getResources().getQuantityString(C1178R.plurals.adults_count, adultsCount, new Object[]{Integer.valueOf(adultsCount)}));
        }
        if (childrenCount > 0) {
            stringBuilder.append(", ").append(getResources().getQuantityString(C1178R.plurals.children_count, childrenCount, new Object[]{Integer.valueOf(childrenCount)}));
        }
        this.binding.text.setText(stringBuilder.toString());
    }
}
