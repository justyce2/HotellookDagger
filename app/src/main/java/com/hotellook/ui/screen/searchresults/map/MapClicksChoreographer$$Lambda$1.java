package com.hotellook.ui.screen.searchresults.map;

import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class MapClicksChoreographer$$Lambda$1 implements OnMapClickListener {
    private final MapClicksChoreographer arg$1;

    private MapClicksChoreographer$$Lambda$1(MapClicksChoreographer mapClicksChoreographer) {
        this.arg$1 = mapClicksChoreographer;
    }

    public static OnMapClickListener lambdaFactory$(MapClicksChoreographer mapClicksChoreographer) {
        return new MapClicksChoreographer$$Lambda$1(mapClicksChoreographer);
    }

    @Hidden
    public void onMapClick(LatLng latLng) {
        this.arg$1.lambda$new$0(latLng);
    }
}
