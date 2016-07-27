package com.hotellook.ui.view.touchypager;

public class VelocityUtils {
    private static final float VELOCITY_ON_OVER_SCROLL = 0.5f;

    public static float applyTranslateVelocity(float translate) {
        return VELOCITY_ON_OVER_SCROLL * translate;
    }

    public static float applyScaleVelocity(float currentScale, float targetScale) {
        return (currentScale + ((targetScale - currentScale) * VELOCITY_ON_OVER_SCROLL)) / currentScale;
    }
}
