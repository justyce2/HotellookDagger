package com.hotellook.ui.screen.hotel.ratingcolorizer;

import android.content.res.Resources;
import android.view.View;
import com.hotellook.C1178R;

public class RatingColorizer {
    private static final int HIGH_VALUE = 75;
    private static final int MID_VALUE = 65;
    private final int mHighRes;
    private final int mLowRes;
    private final int mMidRes;
    private final ResourceApplier mResourceApplier;

    public RatingColorizer(ResourceApplier resourceApplier, int lowRes, int midRes, int highRes) {
        this.mResourceApplier = resourceApplier;
        this.mLowRes = lowRes;
        this.mMidRes = midRes;
        this.mHighRes = highRes;
    }

    public RatingColorizer(ResourceApplier resourceApplier, Resources res) {
        this.mResourceApplier = resourceApplier;
        this.mLowRes = res.getColor(C1178R.color.sr_rate_bad);
        this.mMidRes = res.getColor(C1178R.color.sr_rate_mid);
        this.mHighRes = res.getColor(C1178R.color.sr_rate_good);
    }

    public void apply(View view, int rating) {
        int res = this.mHighRes;
        if (rating < MID_VALUE) {
            res = this.mLowRes;
        } else if (rating < HIGH_VALUE) {
            res = this.mMidRes;
        } else {
            res = this.mHighRes;
        }
        this.mResourceApplier.apply(res, view);
    }
}
