package com.hotellook.ui.screen.searchresults.map;

import com.hotellook.ui.screen.searchresults.map.ResultsMapView.C13921;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ResultsMapView$1$$Lambda$1 implements Runnable {
    private final C13921 arg$1;

    private ResultsMapView$1$$Lambda$1(C13921 c13921) {
        this.arg$1 = c13921;
    }

    public static Runnable lambdaFactory$(C13921 c13921) {
        return new ResultsMapView$1$$Lambda$1(c13921);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onFinish$0();
    }
}
