package com.hotellook.ui.toolbar;

import com.hotellook.ui.screen.gallery.DrawableAnimator.AnimationEndListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ToolbarManager$$Lambda$5 implements AnimationEndListener {
    private final ToolbarManager arg$1;

    private ToolbarManager$$Lambda$5(ToolbarManager toolbarManager) {
        this.arg$1 = toolbarManager;
    }

    public static AnimationEndListener lambdaFactory$(ToolbarManager toolbarManager) {
        return new ToolbarManager$$Lambda$5(toolbarManager);
    }

    @Hidden
    public void onAnimationEnd() {
        this.arg$1.lambda$toggleAnimator$4();
    }
}
