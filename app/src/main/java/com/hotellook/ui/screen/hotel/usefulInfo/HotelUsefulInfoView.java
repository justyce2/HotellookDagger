package com.hotellook.ui.screen.hotel.usefulInfo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.hotellook.core.api.pojo.hoteldetail.GoodToKnowData;
import com.hotellook.databinding.HotelUsefulInfoViewBinding;
import com.hotellook.utils.CollectionUtils;
import java.util.Collections;
import java.util.List;

public class HotelUsefulInfoView extends LinearLayout {
    private HotelUsefulInfoViewBinding binding;

    public HotelUsefulInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelUsefulInfoViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@Nullable List<GoodToKnowData> goodToKnowDataList) {
        if (CollectionUtils.isEmpty(goodToKnowDataList)) {
            setVisibility(8);
            return;
        }
        Collections.sort(goodToKnowDataList, HotelUsefulInfoView$$Lambda$1.lambdaFactory$());
        for (GoodToKnowData goodToKnowData : goodToKnowDataList) {
            HotelUsefulInfoItemView itemView = HotelUsefulInfoItemView.create(this.binding.usefulInfo);
            itemView.bindTo(goodToKnowData);
            this.binding.usefulInfo.addView(itemView);
        }
    }
}
