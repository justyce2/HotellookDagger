package com.hotellook.ui.screen.searchresults.map;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ResultsMapView$$Lambda$2 implements Runnable {
    private final ResultsMapView arg$1;

    private ResultsMapView$$Lambda$2(ResultsMapView resultsMapView) {
        this.arg$1 = resultsMapView;
    }

    public static Runnable lambdaFactory$(ResultsMapView resultsMapView) {
        return new ResultsMapView$$Lambda$2(resultsMapView);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$showUserLocation$3();
    }
}
