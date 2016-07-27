package com.hotellook.ui.screen.searchresults.map;

import com.hotellook.ui.screen.searchresults.map.ResultsMapOverlay.MarkerAnimationListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class MapClicksChoreographer$$Lambda$5 implements MarkerAnimationListener {
    private final Runnable arg$1;

    private MapClicksChoreographer$$Lambda$5(Runnable runnable) {
        this.arg$1 = runnable;
    }

    public static MarkerAnimationListener lambdaFactory$(Runnable runnable) {
        return new MapClicksChoreographer$$Lambda$5(runnable);
    }

    @Hidden
    public void onAnimationFinished() {
        this.arg$1.run();
    }
}
