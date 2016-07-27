package com.hotellook.ui.screen.hotel.features;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.hotellook.badges.Badge;
import com.hotellook.badges.badgeviewgenerator.BadgeViewGenerator;
import com.hotellook.databinding.HotelBadgesViewBinding;
import java.util.List;

public class HotelBadgesView extends LinearLayout {
    private HotelBadgesViewBinding binding;

    public HotelBadgesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelBadgesViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@NonNull List<Badge> badges) {
        BadgeViewGenerator generator = BadgeViewGenerator.withLargeView(getContext());
        for (Badge badge : badges) {
            this.binding.badges.addView(generator.generateView(badge.text, badge.color));
        }
    }
}
