package com.hotellook.events;

import com.hotellook.ui.screen.searchform.LaunchParams;

public class NewLaunchParamsEvent {
    public final LaunchParams launchParams;

    public NewLaunchParamsEvent(LaunchParams launchParams) {
        this.launchParams = launchParams;
    }
}
