package com.hotellook.ui.toolbar;

import android.graphics.drawable.Drawable;
import com.hotellook.ui.screen.gallery.DrawableAnimator.AnimationEndListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ToolbarManager$$Lambda$4 implements AnimationEndListener {
    private final ToolbarManager arg$1;
    private final Drawable arg$2;

    private ToolbarManager$$Lambda$4(ToolbarManager toolbarManager, Drawable drawable) {
        this.arg$1 = toolbarManager;
        this.arg$2 = drawable;
    }

    public static AnimationEndListener lambdaFactory$(ToolbarManager toolbarManager, Drawable drawable) {
        return new ToolbarManager$$Lambda$4(toolbarManager, drawable);
    }

    @Hidden
    public void onAnimationEnd() {
        this.arg$1.lambda$toggleAnimator$3(this.arg$2);
    }
}
