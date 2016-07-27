package com.hotellook.ui.screen.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.events.LocationPermissionGrantedEvent;
import com.hotellook.events.RequestLocationPermissionEvent;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.distancetarget.PoiDistanceTarget;
import com.hotellook.filters.distancetarget.PoiHistoryDistanceTarget;
import com.hotellook.search.Search;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseMapFragment;
import com.hotellook.ui.screen.filters.SeasonsUtils;
import com.hotellook.ui.screen.filters.SupportedSeasons;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.LocationUtils;
import com.hotellook.utils.MapUtils;
import com.hotellook.utils.PoiFilter;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pl.charmas.android.reactivelocation.C1822R;

public class MapFragment extends BaseMapFragment {
    private static final int MIN_ZOOM_LEVEL = 5;
    private static final int USER_LOCATION_DETECTION_DELAY = 300;
    private LatLng cityCenterLocation;
    private String cityName;
    private GoogleApiClient googleApiClient;
    private LatLng hotelLocation;
    private int hotelLocationId;
    @Nullable
    private GoogleMap map;
    private int mapBoundsPadding;
    private MapView mapView;
    private float mapZoom;
    private List<Poi> pois;
    private CameraPosition savedCameraPosition;
    private LatLng searchLocation;
    private String title;

    /* renamed from: com.hotellook.ui.screen.map.MapFragment.1 */
    class C13471 extends MonkeySafeClickListener {
        C13471() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new RequestLocationPermissionEvent());
        }
    }

    static class Snapshot {
        final CameraPosition cameraPosition;
        final LatLng cityCenterLocation;
        final String cityName;
        final LatLng hotelLocation;
        final int hotelLocationId;
        final List<Poi> pois;
        final LatLng searchLocation;
        final String title;

        Snapshot(String title, int hotelLocationId, LatLng hotelLocation, String cityName, LatLng cityCenterLocation, LatLng searchLocation, List<Poi> pois, CameraPosition cameraPosition) {
            this.pois = pois;
            this.title = title;
            this.cityName = cityName;
            this.hotelLocation = hotelLocation;
            this.searchLocation = searchLocation;
            this.cameraPosition = cameraPosition;
            this.hotelLocationId = hotelLocationId;
            this.cityCenterLocation = cityCenterLocation;
        }
    }

    public static MapFragment create(@NonNull HotelDetailData hotelDetailData, @NonNull CityInfo cityInfo, @Nullable Location searchLocation, float zoom) {
        MapFragment fragment = new MapFragment();
        fragment.setTitle(hotelDetailData.getName());
        fragment.setHotelLocationId(hotelDetailData.getLocationId());
        fragment.setHotelLocation(LocationUtils.toLatLng(hotelDetailData.getLocation()));
        fragment.setCityName(cityInfo.getCity());
        fragment.setCityCenterLocation(LocationUtils.toLatLng(cityInfo.getLocation()));
        fragment.setSearchLocation(searchLocation != null ? LocationUtils.toLatLng(searchLocation) : null);
        fragment.setPois(hotelDetailData.getPois());
        fragment.setMapZoom(zoom);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        this.mapBoundsPadding = getResources().getDimensionPixelOffset(C1178R.dimen.map_poi_bounds_padding);
    }

    protected View inflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(C1178R.layout.fragment_map_poi, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (hasInitialSnapshot()) {
            restoreFromSnapshot((Snapshot) initialSnapshot());
        }
        this.mapView = (MapView) view.findViewById(C1178R.id.map);
        View btnLocation = view.findViewById(C1178R.id.location);
        if (hasGMS()) {
            onMapViewCreated(this.mapView, savedInstanceState);
            AndroidUtils.addMarginToPlaceViewBelowStatusBar(this.mapView);
            AndroidUtils.addMarginToPlaceViewBelowToolbar(this.mapView);
            btnLocation.setOnClickListener(new C13471());
        } else {
            view.findViewById(C1178R.id.place_holder).setVisibility(0);
            this.mapView.setVisibility(8);
            btnLocation.setVisibility(8);
        }
        setUpToolbar();
        HotellookApplication.eventBus().register(this);
    }

    @Subscribe
    public void onLocationPermissionGranted(LocationPermissionGrantedEvent event) {
        if (this.map.isMyLocationEnabled()) {
            showUserLocation();
            return;
        }
        this.map.setMyLocationEnabled(true);
        getView().postDelayed(MapFragment$$Lambda$1.lambdaFactory$(this), 300);
    }

    /* synthetic */ void lambda$onLocationPermissionGranted$0() {
        showUserLocation();
    }

    private void showUserLocation() {
        Location userLocation = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        if (userLocation != null) {
            Builder builder = new Builder();
            builder.include(LocationUtils.toLatLng(userLocation));
            builder.include(this.hotelLocation);
            for (Poi poi : this.pois) {
                builder.include(LocationUtils.toLatLng(poi.getLocation()));
            }
            if (MapUtils.computeBoundsZoomLevel(builder.build(), this.mapView.getWidth(), this.mapView.getHeight()) < MIN_ZOOM_LEVEL) {
                this.map.animateCamera(CameraUpdateFactory.newLatLngZoom(LocationUtils.toLatLng(userLocation), 5.0f));
            } else {
                this.map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), this.mapBoundsPadding));
            }
        }
    }

    public void onDestroyView() {
        disableMyLocationOnDestroying();
        HotellookApplication.eventBus().unregister(this);
        getView().postDelayed(MapFragment$$Lambda$2.lambdaFactory$(), 300);
        super.onDestroyView();
    }

    private void disableMyLocationOnDestroying() {
        if (this.map.isMyLocationEnabled()) {
            this.map.setMyLocationEnabled(false);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        this.googleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).build();
    }

    public void onPause() {
        super.onPause();
        if (this.googleApiClient.isConnected()) {
            this.googleApiClient.disconnect();
        }
    }

    public void onResume() {
        super.onResume();
        this.googleApiClient.connect();
    }

    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            this.map = googleMap;
            UiSettings settings = googleMap.getUiSettings();
            settings.setMyLocationButtonEnabled(false);
            settings.setCompassEnabled(true);
            settings.setMapToolbarEnabled(false);
            addMarkers();
            enableMyLocation();
            if (this.savedCameraPosition != null) {
                moveToPosition(this.savedCameraPosition);
            } else {
                moveToHotelLocation();
            }
        }
    }

    private void moveToPosition(@NonNull CameraPosition cameraPosition) {
        this.map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void moveToHotelLocation() {
        if (this.searchLocation == null) {
            this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(this.hotelLocation, this.mapZoom));
        } else {
            this.map.moveCamera(CameraUpdateFactory.newLatLngBounds(MapUtils.calculateBounds(this.hotelLocation, this.searchLocation), this.mapBoundsPadding));
        }
    }

    private void enableMyLocation() {
        if (AndroidUtils.hasLocationPermission(getApplication())) {
            this.map.setMyLocationEnabled(true);
        }
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, getMainActivity().getToolbarManager().getToolbar(), false);
        title.setText(this.title);
        title.setTextColor(getResources().getColor(17170443));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(1).bkgColor(getResources().getColor(C1178R.color.toolbar_green_bkg)).statusBarColor(getResources().getColor(C1178R.color.spf_statusbar_bkg)).toggleColor(getResources().getColor(17170443)).withCustomView(title));
    }

    private void addMarkers() {
        addHotelMarker();
        addCityCenterMarker();
        addPoiMarkers();
    }

    private void addHotelMarker() {
        this.map.addMarker(new MarkerOptions().anchor(0.5f, 1.0f).icon(BitmapDescriptorFactory.fromResource(C1178R.drawable.marker_hotel)).position(this.hotelLocation));
    }

    private void addCityCenterMarker() {
        this.map.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromResource(C1178R.drawable.ic_map_poi_city_center)).title(getString(C1178R.string.poi_city_center) + " (" + this.cityName + ")").position(this.cityCenterLocation));
    }

    private void addPoiMarkers() {
        for (Poi poi : this.pois) {
            this.map.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).icon(poiIconByCategory(poi.getCategory())).title(poi.getName()).position(LocationUtils.toLatLng(poi.getLocation())));
        }
    }

    @Nullable
    private BitmapDescriptor poiIconByCategory(@NonNull String category) {
        Object obj = -1;
        switch (category.hashCode()) {
            case -991666997:
                if (category.equals(Poi.CATEGORY_AIRPORT)) {
                    obj = null;
                    break;
                }
                break;
            case -299560451:
                if (category.equals(Poi.CATEGORY_TRAIN_STATION)) {
                    obj = 4;
                    break;
                }
                break;
            case 93610339:
                if (category.equals(SupportedSeasons.SEASON_BEACH)) {
                    obj = 1;
                    break;
                }
                break;
            case 109435293:
                if (category.equals(Poi.CATEGORY_SIGHT)) {
                    obj = MIN_ZOOM_LEVEL;
                    break;
                }
                break;
            case 1012739086:
                if (category.equals(Poi.CATEGORY_METRO_STATION)) {
                    obj = 2;
                    break;
                }
                break;
            case 2130455929:
                if (category.equals(Poi.CATEGORY_SKI)) {
                    obj = 3;
                    break;
                }
                break;
        }
        switch (obj) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                return BitmapDescriptorFactory.fromResource(C1178R.drawable.ic_map_poi_airport);
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                return BitmapDescriptorFactory.fromResource(C1178R.drawable.ic_map_poi_beach);
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                return BitmapDescriptorFactory.fromResource(C1178R.drawable.ic_map_poi_metro_station);
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                return BitmapDescriptorFactory.fromResource(C1178R.drawable.ic_map_poi_ski_lift);
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                return BitmapDescriptorFactory.fromResource(C1178R.drawable.ic_map_poi_train_station);
            case MIN_ZOOM_LEVEL /*5*/:
                return BitmapDescriptorFactory.fromResource(C1178R.drawable.ic_map_poi_pin);
            default:
                return null;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHotelLocationId(int hotelLocationId) {
        this.hotelLocationId = hotelLocationId;
    }

    public void setHotelLocation(LatLng hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityCenterLocation(LatLng cityCenterLocation) {
        this.cityCenterLocation = cityCenterLocation;
    }

    public void setSearchLocation(LatLng searchLocation) {
        this.searchLocation = searchLocation;
    }

    public void setPois(@Nullable List<Poi> pois) {
        List<Poi> filteredItems = new ArrayList();
        filteredItems.addAll(findItemsBySupportedCategories(pois));
        Poi nearestMetro = findNearestMetro(pois);
        if (nearestMetro != null) {
            filteredItems.add(nearestMetro);
        }
        Poi poi = getPoiFromFilter();
        if (!(poi == null || contains(poi, filteredItems))) {
            filteredItems.add(poi);
        }
        this.pois = filteredItems;
    }

    @Nullable
    private Poi findNearestMetro(List<Poi> pois) {
        List<Poi> metroStations = PoiFilter.filter((List) pois, Poi.CATEGORY_METRO_STATION);
        if (CollectionUtils.isNotEmpty(metroStations)) {
            return (Poi) Collections.min(metroStations, CompareUtils.getComparatorPoiByDistance(LocationUtils.toCoordinates(this.hotelLocation)));
        }
        return null;
    }

    private List<Poi> findItemsBySupportedCategories(List<Poi> pois) {
        return PoiFilter.filter((List) pois, supportedPoiCategories());
    }

    private boolean contains(Poi poiToSearch, List<Poi> filteredItems) {
        for (Poi poi : filteredItems) {
            if (poi.getId() == poiToSearch.getId()) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private Poi getPoiFromFilter() {
        Search search = getComponent().searchKeeper().lastSearch();
        if (search != null) {
            DistanceTarget distanceTarget = search.filters().getGeneralPage().getDistanceFilterItem().getDistanceTarget();
            if (distanceTarget instanceof PoiDistanceTarget) {
                return ((PoiDistanceTarget) distanceTarget).getPoi();
            }
            if (distanceTarget instanceof PoiHistoryDistanceTarget) {
                return ((PoiHistoryDistanceTarget) distanceTarget).getPoiHistoryItem().toPoi();
            }
        }
        return null;
    }

    @NonNull
    private Set<String> supportedPoiCategories() {
        Set<String> categories = new HashSet(Arrays.asList(new String[]{Poi.CATEGORY_AIRPORT, Poi.CATEGORY_TRAIN_STATION}));
        Search search = getComponent().searchKeeper().lastSearch();
        if (!(search == null || search.searchData() == null)) {
            categories.addAll(SeasonsUtils.seasonsPoiCategories(search.searchData().seasons().inLocation(this.hotelLocationId)));
        }
        return categories;
    }

    public void setMapZoom(float mapZoom) {
        this.mapZoom = mapZoom;
    }

    @Nullable
    private CameraPosition cameraPosition() {
        return this.map != null ? this.map.getCameraPosition() : null;
    }

    @Nullable
    public Object takeSnapshot() {
        return new Snapshot(this.title, this.hotelLocationId, this.hotelLocation, this.cityName, this.cityCenterLocation, this.searchLocation, this.pois, cameraPosition());
    }

    private void restoreFromSnapshot(@NonNull Snapshot snapshot) {
        this.pois = snapshot.pois;
        this.title = snapshot.title;
        this.cityName = snapshot.cityName;
        this.hotelLocation = snapshot.hotelLocation;
        this.searchLocation = snapshot.searchLocation;
        this.hotelLocationId = snapshot.hotelLocationId;
        this.savedCameraPosition = snapshot.cameraPosition;
        this.cityCenterLocation = snapshot.cityCenterLocation;
    }
}
