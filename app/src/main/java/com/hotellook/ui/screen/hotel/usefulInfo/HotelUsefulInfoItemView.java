package com.hotellook.ui.screen.hotel.usefulInfo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.hoteldetail.GoodToKnowData;
import com.hotellook.databinding.HotelUsefulInfoItemViewBinding;
import com.j256.ormlite.field.DatabaseField;
import pl.charmas.android.reactivelocation.C1822R;

public class HotelUsefulInfoItemView extends LinearLayout {
    private HotelUsefulInfoItemViewBinding binding;

    public HotelUsefulInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    public static HotelUsefulInfoItemView create(@NonNull ViewGroup parent) {
        return (HotelUsefulInfoItemView) LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.hotel_useful_info_item_view, parent, false);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelUsefulInfoItemViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@NonNull GoodToKnowData data) {
        this.binding.name.setText(data.getCategoryName());
        this.binding.indicator.setImageResource(indicatorDrawableResId(data.getRatioType()));
    }

    private int indicatorDrawableResId(int ratioType) {
        switch (ratioType) {
            case DatabaseField.NO_MAX_FOREIGN_AUTO_REFRESH_LEVEL_SPECIFIED /*-1*/:
                return C1178R.drawable.hotel_good_to_know_point_bad_drawable;
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                return C1178R.drawable.hotel_good_to_know_point_neutral_drawable;
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                return C1178R.drawable.hotel_good_to_know_point_good_drawable;
            default:
                throw new IllegalArgumentException("Ration type is not supported: " + ratioType);
        }
    }
}
