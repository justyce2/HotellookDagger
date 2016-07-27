package com.hotellook.ui.screen.searchresults.map;

import com.google.android.gms.maps.model.Marker;
import com.hotellook.ui.screen.searchresults.map.ResultsMapOverlay.MarkerAnimationListener;
import com.hotellook.ui.screen.searchresults.map.clustering.HotelCluster;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class MapClicksChoreographer$$Lambda$3 implements MarkerAnimationListener {
    private final MapClicksChoreographer arg$1;
    private final Marker arg$2;
    private final HotelCluster arg$3;
    private final String arg$4;

    private MapClicksChoreographer$$Lambda$3(MapClicksChoreographer mapClicksChoreographer, Marker marker, HotelCluster hotelCluster, String str) {
        this.arg$1 = mapClicksChoreographer;
        this.arg$2 = marker;
        this.arg$3 = hotelCluster;
        this.arg$4 = str;
    }

    public static MarkerAnimationListener lambdaFactory$(MapClicksChoreographer mapClicksChoreographer, Marker marker, HotelCluster hotelCluster, String str) {
        return new MapClicksChoreographer$$Lambda$3(mapClicksChoreographer, marker, hotelCluster, str);
    }

    @Hidden
    public void onAnimationFinished() {
        this.arg$1.lambda$collapseOldAndRevealNewMarker$2(this.arg$2, this.arg$3, this.arg$4);
    }
}
