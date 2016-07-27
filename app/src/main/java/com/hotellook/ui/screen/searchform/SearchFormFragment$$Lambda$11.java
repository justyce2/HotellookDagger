package com.hotellook.ui.screen.searchform;

import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormFragment$$Lambda$11 implements OnCameraChangeListener {
    private final SearchFormFragment arg$1;
    private final LatLng arg$2;

    private SearchFormFragment$$Lambda$11(SearchFormFragment searchFormFragment, LatLng latLng) {
        this.arg$1 = searchFormFragment;
        this.arg$2 = latLng;
    }

    public static OnCameraChangeListener lambdaFactory$(SearchFormFragment searchFormFragment, LatLng latLng) {
        return new SearchFormFragment$$Lambda$11(searchFormFragment, latLng);
    }

    @Hidden
    public void onCameraChange(CameraPosition cameraPosition) {
        this.arg$1.lambda$moveCameraToShowMarkerAboveSearchForm$8(this.arg$2, cameraPosition);
    }
}
