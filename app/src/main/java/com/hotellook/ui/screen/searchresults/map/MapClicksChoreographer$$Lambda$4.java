package com.hotellook.ui.screen.searchresults.map;

import com.hotellook.ui.screen.searchresults.map.ResultsMapOverlay.MarkerAnimationListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class MapClicksChoreographer$$Lambda$4 implements MarkerAnimationListener {
    private final MapClicksChoreographer arg$1;
    private final MarkerAnimationListener arg$2;

    private MapClicksChoreographer$$Lambda$4(MapClicksChoreographer mapClicksChoreographer, MarkerAnimationListener markerAnimationListener) {
        this.arg$1 = mapClicksChoreographer;
        this.arg$2 = markerAnimationListener;
    }

    public static MarkerAnimationListener lambdaFactory$(MapClicksChoreographer mapClicksChoreographer, MarkerAnimationListener markerAnimationListener) {
        return new MapClicksChoreographer$$Lambda$4(mapClicksChoreographer, markerAnimationListener);
    }

    @Hidden
    public void onAnimationFinished() {
        this.arg$1.lambda$collapseMarker$3(this.arg$2);
    }
}
