package com.hotellook.ui.view.touchypager;

import android.graphics.RectF;

public class ImageFlingLimits {
    public final int maxX;
    public final int maxY;
    public final int minX;
    public final int minY;

    public ImageFlingLimits(RectF imageRect, RectF viewRect) {
        int leftDelta = (int) (viewRect.left - imageRect.left);
        int topDelta = (int) (viewRect.top - imageRect.top);
        int bottomDelta = (int) (imageRect.bottom - viewRect.bottom);
        int rightDelta = Math.max((int) (imageRect.right - viewRect.right), 0);
        leftDelta = Math.max(leftDelta, 0);
        bottomDelta = Math.max(bottomDelta, 0);
        topDelta = Math.max(topDelta, 0);
        this.minX = (int) (imageRect.centerX() - ((float) rightDelta));
        this.maxX = (int) (imageRect.centerX() + ((float) leftDelta));
        this.minY = (int) (imageRect.centerY() - ((float) bottomDelta));
        this.maxY = (int) (imageRect.centerY() + ((float) topDelta));
    }

    public String toString() {
        return "ImageFlingLimits{minX=" + this.minX + ", maxX=" + this.maxX + ", minY=" + this.minY + ", maxY=" + this.maxY + '}';
    }
}
