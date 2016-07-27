package com.hotellook.ui.screen.searchresults.map;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class MapClicksChoreographer$$Lambda$2 implements OnTouchListener {
    private final ResultsMapOverlay arg$1;

    private MapClicksChoreographer$$Lambda$2(ResultsMapOverlay resultsMapOverlay) {
        this.arg$1 = resultsMapOverlay;
    }

    public static OnTouchListener lambdaFactory$(ResultsMapOverlay resultsMapOverlay) {
        return new MapClicksChoreographer$$Lambda$2(resultsMapOverlay);
    }

    @Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.arg$1.isAnimating();
    }
}
