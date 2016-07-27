package com.hotellook.ui.screen.searchprogress;

import android.animation.TimeInterpolator;

public class SearchProgressInterpolator implements TimeInterpolator {
    private static final float SEARCH_PROGRESS_FRACTION = 0.75f;
    private final float searchAnimationFraction;

    public SearchProgressInterpolator(long searchTime, long fullTime) {
        this.searchAnimationFraction = ((float) searchTime) / ((float) fullTime);
    }

    public float getInterpolation(float input) {
        if (input < this.searchAnimationFraction) {
            return (SEARCH_PROGRESS_FRACTION * input) / this.searchAnimationFraction;
        }
        return SEARCH_PROGRESS_FRACTION + ((0.25f / (1.0f - this.searchAnimationFraction)) * (input - this.searchAnimationFraction));
    }
}
