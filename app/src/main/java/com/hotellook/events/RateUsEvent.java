package com.hotellook.events;

import com.hotellook.ui.screen.information.RateDialogFragment.Source;

public class RateUsEvent {
    public static final int LATER = 0;
    public final Source source;
    public final int stars;

    public RateUsEvent(int stars, Source source) {
        this.stars = stars;
        this.source = source;
    }
}
