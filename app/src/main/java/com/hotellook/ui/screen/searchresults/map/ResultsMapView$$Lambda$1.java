package com.hotellook.ui.screen.searchresults.map;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ResultsMapView$$Lambda$1 implements Runnable {
    private final ResultsMapView arg$1;

    private ResultsMapView$$Lambda$1(ResultsMapView resultsMapView) {
        this.arg$1 = resultsMapView;
    }

    public static Runnable lambdaFactory$(ResultsMapView resultsMapView) {
        return new ResultsMapView$$Lambda$1(resultsMapView);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$setUpLayer$2();
    }
}
