package com.hotellook.ui.view.touchypager;

import android.graphics.RectF;
import android.view.MotionEvent;

public class CheckHelper {
    public static boolean isLeftScroll(float distanceX) {
        return distanceX > 0.0f;
    }

    public static boolean isRightScroll(float distanceX) {
        return distanceX < 0.0f;
    }

    public static boolean isUpScroll(float distanceY) {
        return distanceY > 0.0f;
    }

    public static boolean isDownScroll(float distanceY) {
        return distanceY < 0.0f;
    }

    public static boolean isHorizontalScroll(float distanceX, float distanceY) {
        return Math.abs(distanceX) > Math.abs(distanceY);
    }

    public static boolean isTouchFinished(int action) {
        action &= 255;
        if (action == 1 || action == 3) {
            return true;
        }
        return false;
    }

    public static boolean isTouchStarted(int action) {
        return (action & 255) == 0;
    }

    public static boolean isLeftSideReached(RectF imageRect, RectF viewRect) {
        return imageRect.left >= viewRect.left;
    }

    public static boolean isRightSideReached(RectF imageRect, RectF viewRect) {
        return imageRect.right <= viewRect.right;
    }

    public static boolean isLeftSideOverscrolled(RectF imageRect, RectF viewRect) {
        return imageRect.left <= viewRect.left;
    }

    public static boolean isRightSideOverscrolled(RectF imageRect, RectF viewRect) {
        return imageRect.right >= viewRect.right;
    }

    public static boolean isBottomReached(RectF imageRect, RectF viewRect) {
        return imageRect.bottom <= viewRect.bottom;
    }

    public static boolean isTopReached(RectF imageRect, RectF viewRect) {
        return imageRect.top >= viewRect.top;
    }

    public static boolean isScale(MotionEvent event) {
        return event.getPointerCount() > 1;
    }

    public static boolean imageLessThanView(RectF viewRect, RectF currentImageRect) {
        return currentImageRect.height() < viewRect.height();
    }
}
