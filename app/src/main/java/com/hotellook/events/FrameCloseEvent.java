package com.hotellook.events;

import com.hotellook.statistics.mixpanel.OnScreenTimeDuration;

public class FrameCloseEvent {
    public final OnScreenTimeDuration onScreenTimeDuration;

    public FrameCloseEvent(OnScreenTimeDuration onScreenTimeDuration) {
        this.onScreenTimeDuration = onScreenTimeDuration;
    }
}
