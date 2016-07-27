package com.hotellook.ui.view.viewmovers;

import android.support.annotation.Nullable;
import android.view.View;

public class ViewTranslationData {
    @Nullable
    public final View hideView;
    public final int maxTranslation;
    public final View translateView;

    ViewTranslationData(View translateView, int maxTranslation) {
        this.translateView = translateView;
        this.maxTranslation = maxTranslation;
        this.hideView = null;
    }

    ViewTranslationData(View translateView, View hideView, int maxTranslation) {
        this.translateView = translateView;
        this.hideView = hideView;
        this.maxTranslation = maxTranslation;
    }
}
