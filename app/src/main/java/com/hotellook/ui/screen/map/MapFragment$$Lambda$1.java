package com.hotellook.ui.screen.map;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class MapFragment$$Lambda$1 implements Runnable {
    private final MapFragment arg$1;

    private MapFragment$$Lambda$1(MapFragment mapFragment) {
        this.arg$1 = mapFragment;
    }

    public static Runnable lambdaFactory$(MapFragment mapFragment) {
        return new MapFragment$$Lambda$1(mapFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onLocationPermissionGranted$0();
    }
}
