package com.hotellook.ui.screen.searchresults.map;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.backstack.Savable;
import com.hotellook.badges.Badges;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.events.FilteringFinishedEvent;
import com.hotellook.events.LocationPermissionGrantedEvent;
import com.hotellook.events.QuickFiltersOpenEvent;
import com.hotellook.events.RequestLocationPermissionEvent;
import com.hotellook.filters.Filters;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.search.Locations;
import com.hotellook.search.Search;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.searchresults.map.clustering.HotelCluster;
import com.hotellook.ui.screen.searchresults.map.clustering.HotelClusterItem;
import com.hotellook.ui.screen.searchresults.map.clustering.HotelClusteringAlgorithm;
import com.hotellook.ui.screen.searchresults.map.clustering.HotelRenderer;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.LocationUtils;
import com.hotellook.utils.MapUtils;
import com.hotellook.utils.map.SphericalUtil;
import com.hotellook.utils.map.clustering.Cluster;
import com.hotellook.utils.map.clustering.ClusterManager;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ResultsMapView extends RelativeLayout implements OnCameraChangeListener, Savable {
    private static final float MIN_DIAGONAL_IN_METERS_FOR_GENERAL_POSITION = 500.0f;
    private static final int MIN_ZOOM_LEVEL = 5;
    private static final int USER_LOCATION_DETECTION_DELAY = 300;
    private Badges badges;
    private View btnLocation;
    private boolean canRemoveLocationBtnSelection;
    private ClusterManager<HotelClusterItem> clusterManager;
    private HotelClusteringAlgorithm clusteringAlgorithm;
    private boolean enabled;
    private Filters filters;
    private GoogleApiClient googleApiClient;
    private GoogleMap googleMap;
    private HotelRenderer hotelRenderer;
    private List<HotelData> hotels;
    private View icLocation;
    private Locations locations;
    private MapClicksChoreographer mapClicksChoreographer;
    private ResultsMapOverlay mapOverlay;
    private View mapPlaceholder;
    private MapView mapView;
    private int revealedMarkerTopOffset;
    private CameraPosition savedCameraPosition;
    private Search search;
    private SearchParams searchParams;
    private Map<Long, List<Offer>> searchResults;
    private CancelableCallback showMyLocationCancelableCallback;
    private boolean skipSavedMapLocationRestoration;

    /* renamed from: com.hotellook.ui.screen.searchresults.map.ResultsMapView.1 */
    class C13921 implements CancelableCallback {
        C13921() {
        }

        /* synthetic */ void lambda$onFinish$0() {
            ResultsMapView.this.canRemoveLocationBtnSelection = true;
        }

        public void onFinish() {
            ResultsMapView.this.post(ResultsMapView$1$$Lambda$1.lambdaFactory$(this));
        }

        public void onCancel() {
            ResultsMapView.this.icLocation.setSelected(false);
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.map.ResultsMapView.2 */
    class C13932 extends MonkeySafeClickListener {
        C13932() {
        }

        public void onSafeClick(View v) {
            if (ResultsMapView.this.googleMap != null) {
                HotellookApplication.eventBus().post(new RequestLocationPermissionEvent());
            }
        }
    }

    static class Snapshot {
        final CameraPosition cameraPosition;

        Snapshot(CameraPosition cameraPosition) {
            this.cameraPosition = cameraPosition;
        }
    }

    public ResultsMapView(Context context) {
        super(context);
        this.hotels = new ArrayList();
        this.searchResults = new HashMap();
        this.canRemoveLocationBtnSelection = false;
        this.enabled = false;
        this.skipSavedMapLocationRestoration = false;
        this.showMyLocationCancelableCallback = new C13921();
        init();
    }

    public ResultsMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.hotels = new ArrayList();
        this.searchResults = new HashMap();
        this.canRemoveLocationBtnSelection = false;
        this.enabled = false;
        this.skipSavedMapLocationRestoration = false;
        this.showMyLocationCancelableCallback = new C13921();
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            this.search = getComponent().searchKeeper().lastSearchOrThrowException();
            this.badges = this.search.badges();
            this.filters = this.search.filters();
        }
    }

    protected void onFinishInflate() {
        int i = 8;
        super.onFinishInflate();
        if (!isInEditMode()) {
            boolean hasGMS;
            int i2;
            this.mapView = (MapView) findViewById(C1178R.id.map);
            this.mapOverlay = (ResultsMapOverlay) findViewById(C1178R.id.map_overlay);
            this.btnLocation = findViewById(C1178R.id.location);
            this.icLocation = findViewById(C1178R.id.ic_location);
            this.mapPlaceholder = findViewById(C1178R.id.map_placeholder);
            if (isInEditMode() || GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext()) != 0) {
                hasGMS = false;
            } else {
                hasGMS = true;
            }
            MapView mapView = this.mapView;
            if (hasGMS) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            mapView.setVisibility(i2);
            View view = this.mapPlaceholder;
            if (!hasGMS) {
                i = 0;
            }
            view.setVisibility(i);
            this.btnLocation.setOnClickListener(new C13932());
        }
    }

    private void setUpLayer() {
        post(ResultsMapView$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$setUpLayer$2() {
        setInitialMapPosition();
        SearchData searchData = this.search.searchData();
        Discounts discounts = searchData != null ? searchData.discounts() : null;
        Highlights highlights = searchData != null ? searchData.highlights() : null;
        this.mapClicksChoreographer = new MapClicksChoreographer(this.googleMap, this.mapOverlay, this.searchResults, this.revealedMarkerTopOffset, discounts, highlights);
        this.clusterManager = new ClusterManager(this.googleMap);
        this.hotelRenderer = new HotelRenderer(getContext(), this.googleMap, this.searchResults, this.searchParams.currency(), this.badges, discounts, highlights);
        this.clusterManager.setRenderer(this.hotelRenderer);
        this.clusteringAlgorithm = new HotelClusteringAlgorithm();
        this.clusterManager.setAlgorithm(this.clusteringAlgorithm);
        addHotelsToClusterManager();
        this.googleMap.setOnCameraChangeListener(ResultsMapView$$Lambda$5.lambdaFactory$(this));
        this.clusterManager.setOnClusterClickListener(ResultsMapView$$Lambda$6.lambdaFactory$(this, discounts, highlights));
    }

    /* synthetic */ void lambda$null$0(CameraPosition cameraPosition) {
        if (this.clusterManager != null) {
            this.clusterManager.onCameraChange(cameraPosition);
            onCameraChange(cameraPosition);
        }
    }

    /* synthetic */ boolean lambda$null$1(Discounts discounts, Highlights highlights, Cluster cluster) {
        if (AndroidUtils.preventDoubleClick()) {
            return true;
        }
        return this.mapClicksChoreographer.onMarkerClick(this.clusterManager.getMarker(cluster), new HotelCluster(cluster, discounts, highlights), this.searchParams.currency());
    }

    private void showUserLocation() {
        this.mapClicksChoreographer.onMyLocation(ResultsMapView$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$showUserLocation$3() {
        Location userLocation = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        if (userLocation != null) {
            Builder builder = new Builder();
            builder.include(LocationUtils.toLatLng(userLocation));
            for (HotelData hotel : this.hotels) {
                builder.include(LocationUtils.toLatLng(hotel.getLocation()));
            }
            if (MapUtils.computeBoundsZoomLevel(builder.build(), this.mapView.getWidth(), this.mapView.getHeight()) < MIN_ZOOM_LEVEL) {
                this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LocationUtils.toLatLng(userLocation), 5.0f), this.showMyLocationCancelableCallback);
            } else {
                this.googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), getResources().getDimensionPixelOffset(C1178R.dimen.map_poi_bounds_padding)), this.showMyLocationCancelableCallback);
            }
            this.icLocation.setSelected(true);
            this.canRemoveLocationBtnSelection = false;
        }
    }

    private void addHotelsToClusterManager() {
        for (HotelData hotel : this.hotels) {
            this.clusterManager.addItem(new HotelClusterItem(hotel));
        }
    }

    @Subscribe
    public void onLocationPermissionGranted(LocationPermissionGrantedEvent event) {
        if (this.googleMap.isMyLocationEnabled()) {
            showUserLocation();
            return;
        }
        this.googleMap.setMyLocationEnabled(true);
        postDelayed(ResultsMapView$$Lambda$3.lambdaFactory$(this), 300);
    }

    /* synthetic */ void lambda$onLocationPermissionGranted$4() {
        showUserLocation();
    }

    private void updateMap(boolean moveMapToNewBounds) {
        setUpMarkers();
        onDataChanged(true, moveMapToNewBounds);
        if (this.mapClicksChoreographer != null && this.mapClicksChoreographer.isMarkerRevealed()) {
            this.mapClicksChoreographer.collapseMarker(null, true);
        }
    }

    @Subscribe
    public void onQuickFiltersOpened(QuickFiltersOpenEvent event) {
        if (this.mapClicksChoreographer != null) {
            this.mapClicksChoreographer.collapseMarker(null, false);
        }
    }

    private void setUpMarkers() {
        SearchData searchData = this.search.searchData();
        this.locations = searchData.locations();
        this.searchResults = searchData.offers().all();
        this.hotels = this.filters.getSortedHotels();
        this.searchResults = this.filters.getFilteredOffers();
        applySearchResults(this.searchResults);
    }

    private void applySearchResults(Map<Long, List<Offer>> searchResults) {
        if (this.hotelRenderer != null) {
            this.hotelRenderer.setSearchResults(searchResults);
        }
        if (this.mapClicksChoreographer != null) {
            this.mapClicksChoreographer.setSearchResults(searchResults);
        }
    }

    private HotellookComponent getComponent() {
        return HotellookApplication.getApp().getComponent();
    }

    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            this.googleMap = googleMap;
            SearchData searchData = this.search.searchData();
            this.mapClicksChoreographer = new MapClicksChoreographer(googleMap, this.mapOverlay, this.searchResults, this.revealedMarkerTopOffset, searchData.discounts(), searchData.highlights());
            if (AndroidUtils.hasLocationPermission(getContext())) {
                googleMap.setMyLocationEnabled(true);
            }
            UiSettings settings = googleMap.getUiSettings();
            settings.setMyLocationButtonEnabled(false);
            settings.setCompassEnabled(true);
            settings.setMapToolbarEnabled(false);
            settings.setZoomControlsEnabled(false);
            this.googleMap.setPadding(0, topMapOffset(), 0, 0);
            if (this.enabled) {
                setUpLayer();
                onDataChanged(true, true);
            }
        }
    }

    private int topMapOffset() {
        int standardOffset = getStandardOffset();
        if (AndroidUtils.isPhone(getContext()) && AndroidUtils.isPortrait(getContext())) {
            return standardOffset + getResources().getDimensionPixelOffset(C1178R.dimen.price_filter_height);
        }
        return standardOffset;
    }

    @Subscribe
    public void onFiltersChanged(FilteringFinishedEvent event) {
        if (this.enabled) {
            updateMap(false);
        } else {
            this.skipSavedMapLocationRestoration = true;
        }
    }

    public void onDataChanged(boolean animate, boolean changeMapToNewBounds) {
        if (this.enabled) {
            this.clusterManager.clearItems();
            addHotelsToClusterManager();
            this.clusterManager.cluster();
            if (changeMapToNewBounds) {
                moveCameraToGeneralView(animate);
            }
        }
    }

    public void onMapStateEnabled() {
        this.enabled = true;
        setUpMarkers();
        if (this.googleMap != null) {
            setUpLayer();
        }
    }

    private void setInitialMapPosition() {
        if (this.savedCameraPosition != null) {
            this.googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(this.savedCameraPosition));
            this.savedCameraPosition = null;
            return;
        }
        moveCameraToGeneralView(false);
    }

    private void moveCameraToGeneralView(boolean animate) {
        LatLngBounds mapBounds;
        if (this.hotels.size() == 0) {
            mapBounds = MapUtils.convertCenterAndRadiusToBounds(LocationUtils.toLatLng(this.searchParams.location()), 50000.0d);
        } else {
            mapBounds = getBoundsWithMinDiagonal(MapUtils.computeBounds(this.hotels));
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(mapBounds, getStandardOffset() * 2);
        if (animate) {
            this.googleMap.animateCamera(cameraUpdate);
        } else {
            this.googleMap.setOnMapLoadedCallback(ResultsMapView$$Lambda$4.lambdaFactory$(this, cameraUpdate));
        }
    }

    /* synthetic */ void lambda$moveCameraToGeneralView$5(CameraUpdate cameraUpdate) {
        this.googleMap.moveCamera(cameraUpdate);
    }

    private LatLngBounds getBoundsWithMinDiagonal(LatLngBounds mapBounds) {
        if (((double) LocationUtils.distanceBetween(mapBounds.northeast.latitude, mapBounds.northeast.longitude, mapBounds.southwest.latitude, mapBounds.southwest.longitude)) >= 500.0d) {
            return mapBounds;
        }
        LatLng center = mapBounds.getCenter();
        LatLng northeast = SphericalUtil.computeOffset(center, 250.0d, 45.0d);
        return LatLngBounds.builder().include(northeast).include(SphericalUtil.computeOffset(center, 250.0d, 225.0d)).build();
    }

    private int getStandardOffset() {
        return getResources().getDimensionPixelSize(C1178R.dimen.standard_offset);
    }

    public MapView getMap() {
        return this.mapView;
    }

    public void setLocationProvider(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            HotellookApplication.eventBus().register(this);
        }
        if (!isInEditMode() && this.hotels != null && this.hotels.size() > 0 && filtersChanged()) {
            onFiltersChanged(null);
        }
    }

    private boolean filtersChanged() {
        return !new HashSet(this.hotels).equals(new HashSet(this.filters.getSortedHotels()));
    }

    protected void onDetachedFromWindow() {
        HotellookApplication.eventBus().unregister(this);
        if (this.googleMap != null) {
            this.googleMap.setMyLocationEnabled(false);
        }
        super.onDetachedFromWindow();
    }

    public void onMapStateDisabled() {
        if (this.enabled) {
            this.enabled = false;
            if (this.clusterManager != null) {
                this.clusterManager.clearItems();
                this.clusterManager = null;
            }
            if (this.googleMap != null) {
                this.googleMap.clear();
            }
            if (this.mapOverlay != null) {
                this.mapOverlay.clean();
            }
            if (this.mapClicksChoreographer != null) {
                this.mapClicksChoreographer.clean();
            }
            this.savedCameraPosition = getMapCameraPosition();
        }
    }

    public void onCameraChange(CameraPosition cameraPosition) {
        if (this.icLocation != null && this.icLocation.isSelected() && this.canRemoveLocationBtnSelection) {
            this.icLocation.setSelected(false);
        }
    }

    private CameraPosition getMapCameraPosition() {
        if (this.googleMap != null) {
            return this.googleMap.getCameraPosition();
        }
        return null;
    }

    public void setSearchParams(SearchParams searchParams) {
        this.searchParams = searchParams;
        this.mapOverlay.setSearchParams(this.searchParams);
    }

    public void setRevealedMarkerTopOffset(int revealedMarkerTopOffset) {
        this.revealedMarkerTopOffset = revealedMarkerTopOffset;
    }

    @NonNull
    public Object saveState() {
        CameraPosition mapCameraPosition = getMapCameraPosition();
        if (mapCameraPosition != null && mapCameraPosition.target.latitude == 0.0d && mapCameraPosition.target.longitude == 0.0d) {
            mapCameraPosition = null;
        }
        return new Snapshot(mapCameraPosition);
    }

    public void restoreState(@NonNull Object state) {
        if (this.skipSavedMapLocationRestoration) {
            this.skipSavedMapLocationRestoration = false;
        } else {
            this.savedCameraPosition = ((Snapshot) state).cameraPosition;
        }
    }
}
