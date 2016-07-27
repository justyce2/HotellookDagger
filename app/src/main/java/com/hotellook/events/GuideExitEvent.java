package com.hotellook.events;

import com.hotellook.statistics.mixpanel.AppGuideStat;

public class GuideExitEvent {
    public final AppGuideStat appGuideParams;

    public GuideExitEvent(AppGuideStat appGuideParams) {
        this.appGuideParams = appGuideParams;
    }
}
