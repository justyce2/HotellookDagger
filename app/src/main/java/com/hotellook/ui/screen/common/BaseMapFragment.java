package com.hotellook.ui.screen.common;

import android.os.Bundle;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.hotellook.utils.AndroidUtils;

public abstract class BaseMapFragment extends ButterKnifeFragment implements OnMapReadyCallback {
    private boolean hasGMS;
    private MapView mapView;

    public BaseMapFragment() {
        this.hasGMS = true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.hasGMS = AndroidUtils.isGoogleServicesAvailable(getContext());
    }

    public void onMapViewCreated(MapView map, Bundle savedInstanceState) {
        this.mapView = map;
        if (this.mapView != null && this.hasGMS) {
            this.mapView.onCreate(null);
            MapsInitializer.initialize(getActivity());
            this.mapView.getMapAsync(this);
        }
    }

    public void onResume() {
        if (this.mapView != null && this.hasGMS) {
            this.mapView.onResume();
        }
        super.onResume();
    }

    public void onDestroyView() {
        if (this.mapView != null && this.hasGMS) {
            fixWeirdCrashOnBackPress();
            this.mapView.onDestroy();
        }
        super.onDestroyView();
    }

    private void fixWeirdCrashOnBackPress() {
        ((ViewGroup) this.mapView.getParent()).removeView(this.mapView);
    }

    public void onPause() {
        if (this.mapView != null && this.hasGMS) {
            this.mapView.onPause();
        }
        super.onPause();
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (this.hasGMS && this.mapView != null) {
            this.mapView.onLowMemory();
        }
    }

    protected void setMapImmutableMode(GoogleMap googleMap) {
        UiSettings settings = googleMap.getUiSettings();
        settings.setAllGesturesEnabled(false);
        settings.setCompassEnabled(false);
        settings.setMapToolbarEnabled(false);
        settings.setMyLocationButtonEnabled(false);
        settings.setZoomControlsEnabled(false);
    }

    public boolean hasGMS() {
        return this.hasGMS;
    }
}
