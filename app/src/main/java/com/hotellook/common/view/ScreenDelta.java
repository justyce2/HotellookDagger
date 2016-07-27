package com.hotellook.common.view;

public class ScreenDelta {
    public final int xDelta;
    public final int yDelta;

    public ScreenDelta(OnScreenLocation fromPosition, OnScreenLocation toPosition) {
        this.xDelta = fromPosition.contentCenterX - toPosition.contentCenterX;
        this.yDelta = fromPosition.contentCenterY - toPosition.contentCenterY;
    }
}
