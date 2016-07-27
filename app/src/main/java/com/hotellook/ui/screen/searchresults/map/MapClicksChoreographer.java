package com.hotellook.ui.screen.searchresults.map;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.events.UnbreakableClusterClickEvent;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.ui.screen.searchresults.map.ResultsMapOverlay.MarkerAnimationListener;
import com.hotellook.ui.screen.searchresults.map.clustering.HotelCluster;
import com.hotellook.ui.screen.searchresults.map.clustering.HotelClusteringAlgorithm;
import java.util.List;
import java.util.Map;

public class MapClicksChoreographer {
    public static final int CLUSTER_FOCUS_ANIMATION_DURATION = 300;
    @Nullable
    private final Discounts discounts;
    private final GoogleMap googleMap;
    @Nullable
    private final Highlights highlights;
    private final ResultsMapOverlay mapOverlay;
    private boolean markerRevealed;
    private Map<Long, List<Offer>> searchResults;
    private final int topOffset;

    /* renamed from: com.hotellook.ui.screen.searchresults.map.MapClicksChoreographer.1 */
    class C13861 implements CancelableCallback {
        final /* synthetic */ HotelCluster val$cluster;
        final /* synthetic */ String val$currency;
        final /* synthetic */ Marker val$marker;

        C13861(Marker marker, HotelCluster hotelCluster, String str) {
            this.val$marker = marker;
            this.val$cluster = hotelCluster;
            this.val$currency = str;
        }

        public void onFinish() {
            MapClicksChoreographer.this.markerRevealed = true;
            MapClicksChoreographer.this.mapOverlay.revealMarker(MapClicksChoreographer.this.googleMap, MapClicksChoreographer.this.searchResults, this.val$marker, this.val$cluster, this.val$currency, MapClicksChoreographer.this.discounts, MapClicksChoreographer.this.highlights);
            MapClicksChoreographer.this.blockMapGestures(true);
        }

        public void onCancel() {
        }
    }

    public MapClicksChoreographer(GoogleMap googleMap, ResultsMapOverlay mapOverlay, Map<Long, List<Offer>> searchResults, int topOffset, Discounts discounts, Highlights highlights) {
        this.googleMap = googleMap;
        this.mapOverlay = mapOverlay;
        this.searchResults = searchResults;
        this.topOffset = topOffset;
        this.discounts = discounts;
        this.highlights = highlights;
        googleMap.setOnMapClickListener(MapClicksChoreographer$$Lambda$1.lambdaFactory$(this));
        mapOverlay.setOnTouchListener(MapClicksChoreographer$$Lambda$2.lambdaFactory$(mapOverlay));
    }

    /* synthetic */ void lambda$new$0(LatLng latLng) {
        if (this.markerRevealed) {
            collapseMarker(null, false);
        }
    }

    public void setSearchResults(Map<Long, List<Offer>> searchResults) {
        this.searchResults = searchResults;
    }

    public boolean onMarkerClick(Marker marker, HotelCluster cluster, String currency) {
        if (this.markerRevealed) {
            if (marker != this.mapOverlay.getRevealedMarker()) {
                collapseOldAndRevealNewMarker(marker, cluster, currency);
            }
        } else if (cluster.getSize() == 1) {
            this.googleMap.animateCamera(newCameraUpdateForItemClick(cluster), CLUSTER_FOCUS_ANIMATION_DURATION, new C13861(marker, cluster, currency));
        } else {
            float splitZoom = new HotelClusteringAlgorithm().findZoomWhichSplitsCluster(this.googleMap, cluster);
            if (splitZoom == 0.0f) {
                HotellookApplication.eventBus().post(new UnbreakableClusterClickEvent(cluster.getHotels()));
            } else {
                this.googleMap.animateCamera(newCameraUpdateForClusterClick(cluster, splitZoom), CLUSTER_FOCUS_ANIMATION_DURATION, null);
            }
        }
        return true;
    }

    private void blockMapGestures(boolean blockGestures) {
        this.googleMap.getUiSettings().setAllGesturesEnabled(!blockGestures);
    }

    private void collapseOldAndRevealNewMarker(Marker newMarker, HotelCluster newCluster, String currency) {
        collapseMarker(MapClicksChoreographer$$Lambda$3.lambdaFactory$(this, newMarker, newCluster, currency), false);
    }

    /* synthetic */ void lambda$collapseOldAndRevealNewMarker$2(Marker newMarker, HotelCluster newCluster, String currency) {
        if (newMarker != null) {
            onMarkerClick(newMarker, newCluster, currency);
        }
    }

    public void collapseMarker(MarkerAnimationListener listener, boolean collapseToInfinity) {
        this.mapOverlay.collapseMarker(MapClicksChoreographer$$Lambda$4.lambdaFactory$(this, listener), collapseToInfinity);
    }

    /* synthetic */ void lambda$collapseMarker$3(MarkerAnimationListener listener) {
        onMarkerCollapsed();
        if (listener != null) {
            listener.onAnimationFinished();
        }
    }

    private void onMarkerCollapsed() {
        this.markerRevealed = false;
        blockMapGestures(false);
    }

    @NonNull
    private CameraUpdate newCameraUpdateForClusterClick(HotelCluster cluster, float newZoom) {
        return CameraUpdateFactory.newLatLngZoom(cluster.getItemWithMinPrice(this.searchResults).getPosition(), Math.min(newZoom, this.googleMap.getMaxZoomLevel()));
    }

    @NonNull
    private CameraUpdate newCameraUpdateForItemClick(HotelCluster cluster) {
        Projection projection = this.googleMap.getProjection();
        Point pivotOfReveal = projection.toScreenLocation(cluster.getPosition());
        pivotOfReveal.y -= getMarkerRevealedHeight() / 2;
        return CameraUpdateFactory.newLatLng(projection.fromScreenLocation(pivotOfReveal));
    }

    private int getMarkerRevealedHeight() {
        return this.mapOverlay.getContext().getResources().getDimensionPixelSize(C1178R.dimen.map_card_view_height);
    }

    public void onMyLocation(Runnable myLocationTask) {
        if (!this.mapOverlay.isAnimating()) {
            if (this.markerRevealed) {
                collapseMarker(MapClicksChoreographer$$Lambda$5.lambdaFactory$(myLocationTask), false);
            } else {
                myLocationTask.run();
            }
        }
    }

    public void clean() {
        if (this.markerRevealed) {
            this.markerRevealed = false;
            blockMapGestures(false);
        }
    }

    public boolean isMarkerRevealed() {
        return this.markerRevealed;
    }
}
