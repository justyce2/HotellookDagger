package com.hotellook.ui.screen.searchresults.map;

import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ResultsMapView$$Lambda$5 implements OnCameraChangeListener {
    private final ResultsMapView arg$1;

    private ResultsMapView$$Lambda$5(ResultsMapView resultsMapView) {
        this.arg$1 = resultsMapView;
    }

    public static OnCameraChangeListener lambdaFactory$(ResultsMapView resultsMapView) {
        return new ResultsMapView$$Lambda$5(resultsMapView);
    }

    @Hidden
    public void onCameraChange(CameraPosition cameraPosition) {
        this.arg$1.lambda$null$0(cameraPosition);
    }
}
