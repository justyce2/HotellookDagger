package com.hotellook.ui.screen.searchresults.map;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ResultsMapView$$Lambda$4 implements OnMapLoadedCallback {
    private final ResultsMapView arg$1;
    private final CameraUpdate arg$2;

    private ResultsMapView$$Lambda$4(ResultsMapView resultsMapView, CameraUpdate cameraUpdate) {
        this.arg$1 = resultsMapView;
        this.arg$2 = cameraUpdate;
    }

    public static OnMapLoadedCallback lambdaFactory$(ResultsMapView resultsMapView, CameraUpdate cameraUpdate) {
        return new ResultsMapView$$Lambda$4(resultsMapView, cameraUpdate);
    }

    @Hidden
    public void onMapLoaded() {
        this.arg$1.lambda$moveCameraToGeneralView$5(this.arg$2);
    }
}
