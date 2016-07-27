package com.hotellook.events;

import android.view.View;

public class HotelScreenSyncScrollEvent {
    public final int scrollY;
    public final View view;

    public HotelScreenSyncScrollEvent(View view, int scrollY) {
        this.view = view;
        this.scrollY = scrollY;
    }
}
