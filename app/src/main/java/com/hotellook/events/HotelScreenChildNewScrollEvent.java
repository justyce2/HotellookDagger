package com.hotellook.events;

import android.view.View;

public class HotelScreenChildNewScrollEvent {
    public final int fragmentPositionInViewPager;
    public final int scrollY;
    public final View view;

    public HotelScreenChildNewScrollEvent(View view, int scrollY, int fragmentPositionInViewPager) {
        this.view = view;
        this.scrollY = scrollY;
        this.fragmentPositionInViewPager = fragmentPositionInViewPager;
    }
}
