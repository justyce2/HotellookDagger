package com.hotellook.common.click;

public class DoubleClickPreventer {
    private long mLastClickTime;
    private final long mTimeDelta;

    public DoubleClickPreventer(long timeDelta) {
        this.mTimeDelta = timeDelta;
    }

    public boolean preventDoubleClick() {
        if (System.currentTimeMillis() - this.mLastClickTime < this.mTimeDelta && System.currentTimeMillis() - this.mLastClickTime > 0) {
            return true;
        }
        this.mLastClickTime = System.currentTimeMillis();
        return false;
    }
}
