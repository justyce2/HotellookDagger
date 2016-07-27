package com.hotellook.ui.screen.locationchooser;

import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class LocationChooserFragment$$Lambda$1 implements OnCameraChangeListener {
    private final LocationChooserFragment arg$1;

    private LocationChooserFragment$$Lambda$1(LocationChooserFragment locationChooserFragment) {
        this.arg$1 = locationChooserFragment;
    }

    public static OnCameraChangeListener lambdaFactory$(LocationChooserFragment locationChooserFragment) {
        return new LocationChooserFragment$$Lambda$1(locationChooserFragment);
    }

    @Hidden
    public void onCameraChange(CameraPosition cameraPosition) {
        this.arg$1.lambda$moveCameraToInitialPoint$0(cameraPosition);
    }
}
