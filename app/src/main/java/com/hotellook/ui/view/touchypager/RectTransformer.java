package com.hotellook.ui.view.touchypager;

import android.graphics.PointF;
import android.graphics.RectF;

public class RectTransformer {
    public RectF scale(RectF rect, float factor) {
        return scale(rect, factor, new PointF(0.0f, 0.0f));
    }

    public RectF scale(RectF rect, float factor, PointF pivot) {
        RectF scaled = new RectF(rect);
        float scaledWidth = rect.width() * factor;
        float scaledHeight = rect.height() * factor;
        float bottomPerc = Math.abs(pivot.y - rect.bottom) / rect.height();
        float leftPerc = Math.abs(pivot.x - rect.left) / rect.width();
        float rightPerc = Math.abs(pivot.x - rect.right) / rect.width();
        scaled.top = pivot.y - ((Math.abs(pivot.y - rect.top) / rect.height()) * scaledHeight);
        scaled.bottom = pivot.y + (bottomPerc * scaledHeight);
        scaled.left = pivot.x - (leftPerc * scaledWidth);
        scaled.right = pivot.x + (rightPerc * scaledWidth);
        return scaled;
    }
}
