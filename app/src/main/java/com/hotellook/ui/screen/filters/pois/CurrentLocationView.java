package com.hotellook.ui.screen.filters.pois;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.DistanceTargetSelectedEvent;
import com.hotellook.events.LocationPermissionDeniedEvent;
import com.hotellook.events.LocationPermissionGrantedEvent;
import com.hotellook.events.RequestLocationPermissionEvent;
import com.hotellook.filters.distancetarget.MyLocationDistanceTarget;
import com.hotellook.ui.screen.Toasts;
import com.hotellook.ui.view.CircularProgressBar;
import com.squareup.otto.Subscribe;

public class CurrentLocationView extends FrameLayout implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private static final int LOCATION_REQUEST_TIMEOUT = 30000;
    private GoogleApiClient googleApiClient;
    private View ivGps;
    private Location lastLocation;
    private CircularProgressBar pbGps;

    /* renamed from: com.hotellook.ui.screen.filters.pois.CurrentLocationView.1 */
    class C12711 extends MonkeySafeClickListener {
        C12711() {
        }

        public void onSafeClick(View v) {
            if (CurrentLocationView.this.lastLocation != null) {
                CurrentLocationView.this.deliverResult(CurrentLocationView.this.lastLocation);
            } else if (CurrentLocationView.this.googleApiClient.isConnected()) {
                CurrentLocationView.this.requestPermission();
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.pois.CurrentLocationView.2 */
    class C12722 extends AnimatorListenerAdapter {
        C12722() {
        }

        public void onAnimationEnd(Animator animation) {
            CurrentLocationView.this.ivGps.setVisibility(4);
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.pois.CurrentLocationView.3 */
    class C12733 extends AnimatorListenerAdapter {
        C12733() {
        }

        public void onAnimationEnd(Animator animation) {
            CurrentLocationView.this.pbGps.setVisibility(8);
            CurrentLocationView.this.pbGps.setScaleX(1.0f);
            CurrentLocationView.this.pbGps.setScaleY(1.0f);
        }
    }

    public CurrentLocationView(Context context) {
        super(context);
    }

    public CurrentLocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.ivGps = findViewById(C1178R.id.gps);
        this.pbGps = (CircularProgressBar) findViewById(C1178R.id.pb_gps);
        buildGoogleApiClient();
        this.googleApiClient.connect();
        this.pbGps.setVisibility(4);
        setOnClickListener(new C12711());
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        HotellookApplication.eventBus().unregister(this);
    }

    private void requestPermission() {
        HotellookApplication.eventBus().register(this);
        HotellookApplication.eventBus().post(new RequestLocationPermissionEvent());
    }

    protected synchronized void buildGoogleApiClient() {
        this.googleApiClient = new Builder(getContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    public void onConnected(Bundle bundle) {
        if (getContext() != null) {
            this.lastLocation = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        }
    }

    public void onConnectionSuspended(int i) {
    }

    private void requestLocation() {
        this.ivGps.animate().alpha(0.0f).setDuration(100).setListener(new C12722());
        this.pbGps.setVisibility(0);
        this.pbGps.setAlpha(1.0f);
        this.pbGps.reset();
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(100);
        locationRequest.setFastestInterval(5000);
        locationRequest.setInterval(5000);
        locationRequest.setExpirationDuration(30000);
        LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, locationRequest, (LocationListener) this);
    }

    private void deliverResult(Location location) {
        HotellookApplication.eventBus().post(new DistanceTargetSelectedEvent(new MyLocationDistanceTarget(getContext(), location)));
    }

    public void onLocationChanged(Location location) {
        this.lastLocation = location;
        deliverResult(location);
        setLocationInitState();
    }

    private void setLocationInitState() {
        this.pbGps.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setDuration(300).setListener(new C12733());
        this.ivGps.setVisibility(0);
        this.ivGps.setScaleX(0.0f);
        this.ivGps.setScaleY(0.0f);
        this.ivGps.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(300).setListener(null);
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toasts.showErrorFindLocation(getContext());
        setLocationInitState();
    }

    @Subscribe
    public void onLocationPermissionGranted(LocationPermissionGrantedEvent event) {
        HotellookApplication.eventBus().unregister(this);
        requestLocation();
    }

    @Subscribe
    public void onLocationPermissionDenied(LocationPermissionDeniedEvent event) {
        HotellookApplication.eventBus().unregister(this);
    }
}
