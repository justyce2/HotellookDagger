package com.hotellook.ui.screen.destinationpicker;

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
import com.google.android.gms.maps.model.LatLng;
import com.hotellook.C1178R;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseMapFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.LocationUtils;

public class LocationChooserFragment extends BaseMapFragment {
    private static final int CITY_LEVEL_ZOOM = 12;
    @BindView
    View applyBtn;
    private GoogleMap googleMap;
    @BindView
    MapView mapView;
    @BindView
    View placeHolder;

    /* renamed from: com.hotellook.ui.screen.destinationpicker.LocationChooserFragment.1 */
    class C12391 extends MonkeySafeClickListener {
        C12391() {
        }

        public void onSafeClick(View v) {
            LocationChooserFragment.this.returnToSearchFormAndShowChosenLocation();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        this.applyBtn.setOnClickListener(new C12391());
    }

    private void returnToSearchFormAndShowChosenLocation() {
        SearchFormData searchFormData = new SearchFormData(getContext(), getComponent().getSearchFormPreferences());
        LatLng center = this.googleMap.getCameraPosition().target;
        searchFormData.updateWithLocationDestination(LocationUtils.from(center.latitude, center.longitude));
        searchFormData.saveData();
        getMainActivity().returnToSearchForm();
    }

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        UiSettings settings = googleMap.getUiSettings();
        settings.setCompassEnabled(true);
        settings.setMapToolbarEnabled(false);
        settings.setMyLocationButtonEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LocationUtils.toLatLng(new SearchFormData(getContext(), getComponent().getSearchFormPreferences()).getLocation()), 12.0f));
        if (AndroidUtils.hasLocationPermission(getApplication())) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, getToolbar(), false);
        title.setText(C1178R.string.location_chooser_title);
        title.setTextColor(ContextCompat.getColor(getContext(), 17170443));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(1).bkgColor(ContextCompat.getColor(getContext(), C1178R.color.toolbar_green_bkg)).statusBarColor(ContextCompat.getColor(getContext(), C1178R.color.spf_statusbar_bkg)).toggleColor(ContextCompat.getColor(getContext(), 17170443)).withCustomView(title));
    }
}
