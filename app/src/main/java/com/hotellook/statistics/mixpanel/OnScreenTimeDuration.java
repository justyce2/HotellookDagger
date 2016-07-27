package com.hotellook.statistics.mixpanel;

public class OnScreenTimeDuration {
    private long duration;
    private long screenStartedTime;

    public OnScreenTimeDuration() {
        this.screenStartedTime = System.currentTimeMillis();
    }

    public void onScreenFinishedOrPaused() {
        this.duration += System.currentTimeMillis() - this.screenStartedTime;
    }

    public void onScreenStartedOrResumed() {
        this.screenStartedTime = System.currentTimeMillis();
    }

    public long getDurationInSeconds() {
        return this.duration / 1000;
    }

    public boolean isDurationReached(long targetDuration) {
        return System.currentTimeMillis() - this.screenStartedTime >= targetDuration;
    }
}
