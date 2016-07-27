package com.hotellook.statistics.mixpanel;

import android.content.Intent;

public class LaunchSource {
    public static final String DEEPLINK = "deeplink";
    public static final String DIRECT = "direct";
    public static final String PUSH = "push";
    private final String source;

    public LaunchSource(Intent intent) {
        this.source = findSource(intent);
    }

    private String findSource(Intent intent) {
        if (intent == null || intent.getData() == null || intent.getData().getHost() == null) {
            return DIRECT;
        }
        return DEEPLINK;
    }

    public String toString() {
        return this.source;
    }
}
