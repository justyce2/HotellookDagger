package com.hotellook.ui.screen.locationchooser;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseMapFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.LocationUtils;

public abstract class LocationChooserFragment extends BaseMapFragment {
    private static final int CITY_LEVEL_ZOOM = 12;
    public static final String LOCATION_BUNDLE_ID = "LOCATION_BUNDLE_ID";
    @BindView
    View applyBtn;
    private GoogleMap googleMap;
    private Location location;
    @BindView
    View locationIcon;
    @BindView
    MapView mapView;
    @BindView
    View placeHolder;

    /* renamed from: com.hotellook.ui.screen.locationchooser.LocationChooserFragment.1 */
    class C13461 extends MonkeySafeClickListener {
        C13461() {
        }

        public void onSafeClick(View v) {
            LatLng center = LocationChooserFragment.this.googleMap.getCameraPosition().target;
            LocationChooserFragment.this.choosePointAndReturn(LocationUtils.from(center.latitude, center.longitude));
        }
    }

    abstract void choosePointAndReturn(Location location);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.location = (Location) savedInstanceState.getParcelable(LOCATION_BUNDLE_ID);
        } else {
            this.location = (Location) getArguments().getParcelable(LOCATION_BUNDLE_ID);
        }
    }

    protected View inflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(C1178R.layout.fragment_location_chooser, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(view);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(view);
        if (hasGMS()) {
            onMapViewCreated(this.mapView, savedInstanceState);
            setUpApplyBtnClickListener();
        } else {
            this.placeHolder.setVisibility(0);
            this.mapView.setVisibility(8);
            this.applyBtn.setVisibility(8);
        }
        setUpToolbar();
    }

    private void setUpApplyBtnClickListener() {
        this.applyBtn.setOnClickListener(new C13461());
    }

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setUpMap(googleMap);
        moveCameraToInitialPoint(googleMap);
        if (AndroidUtils.hasLocationPermission(getContext())) {
            googleMap.setMyLocationEnabled(true);
        }
        this.locationIcon.setVisibility(0);
    }

    private void moveCameraToInitialPoint(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LocationUtils.toLatLng(this.location), 12.0f));
        googleMap.setOnCameraChangeListener(LocationChooserFragment$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$moveCameraToInitialPoint$0(CameraPosition cameraPosition) {
        this.location = LocationUtils.from(cameraPosition.target.latitude, cameraPosition.target.longitude);
    }

    private void setUpMap(GoogleMap googleMap) {
        UiSettings settings = googleMap.getUiSettings();
        settings.setCompassEnabled(true);
        settings.setMapToolbarEnabled(false);
        settings.setMyLocationButtonEnabled(false);
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, getToolbar(), false);
        title.setText(C1178R.string.location_chooser_title);
        title.setTextColor(ContextCompat.getColor(getContext(), 17170443));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(1).bkgColor(ContextCompat.getColor(getContext(), C1178R.color.toolbar_green_bkg)).statusBarColor(ContextCompat.getColor(getContext(), C1178R.color.spf_statusbar_bkg)).toggleColor(ContextCompat.getColor(getContext(), 17170443)).withCustomView(title));
    }

    static void fillArgs(LocationChooserFragment fragment, Location location) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(LOCATION_BUNDLE_ID, location);
        fragment.setArguments(bundle);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LOCATION_BUNDLE_ID, this.location);
        super.onSaveInstanceState(outState);
    }
}
