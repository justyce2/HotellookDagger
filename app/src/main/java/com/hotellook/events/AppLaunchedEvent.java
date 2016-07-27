package com.hotellook.events;

import com.hotellook.statistics.mixpanel.LaunchSource;

public class AppLaunchedEvent {
    public final LaunchSource launchSource;

    public AppLaunchedEvent(LaunchSource launchSource) {
        this.launchSource = launchSource;
    }
}
