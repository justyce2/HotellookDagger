package com.hotellook.ui.screen.searchform;

import android.content.DialogInterface;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.RequestFlags;
import com.hotellook.api.RequestFlagsGenerator;
import com.hotellook.api.callback.CallbackWithTimeout;
import com.hotellook.api.data.DestinationType;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.api.dataloaders.CityImageLoader;
import com.hotellook.api.dataloaders.SearchFormCityImageLoader;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.HotellookService;
import com.hotellook.events.DestinationFoundEvent;
import com.hotellook.events.FindDestinationFailedEvent;
import com.hotellook.events.HotelSearchStartEvent;
import com.hotellook.events.LocationButtonClickEvent;
import com.hotellook.events.NewDestinationEvent;
import com.hotellook.events.NewLaunchParamsEvent;
import com.hotellook.events.SearchButtonClickEvent;
import com.hotellook.events.SearchParamsUpdatedEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.location.InvalidLocationSettingsException;
import com.hotellook.location.LocationRequestResolver;
import com.hotellook.location.PermissionDeniedException;
import com.hotellook.location.PlayServicesNotAvailableException;
import com.hotellook.search.Search;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants.Source;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.Dialogs;
import com.hotellook.ui.screen.Toasts;
import com.hotellook.ui.screen.common.BaseMapFragment;
import com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment;
import com.hotellook.ui.screen.hotel.HotelFragment;
import com.hotellook.ui.screen.hotel.HotelScreenOpenInfo;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.hotel.prices.OffersFragment;
import com.hotellook.ui.screen.searchprogress.SearchProgressFragment;
import com.hotellook.ui.screen.searchresults.SearchPlaceHolderFragment;
import com.hotellook.ui.screen.searchresults.SearchResultsFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.SearchFormView;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.DateUtils;
import com.hotellook.utils.EventBus;
import com.hotellook.utils.LocationUtils;
import com.hotellook.utils.Preconditions;
import com.hotellook.utils.RxUtil;
import com.hotellook.utils.Size;
import com.squareup.otto.Subscribe;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import me.zhanghai.android.materialprogressbar.BuildConfig;
import rx.Observable;
import rx.Subscription;
import timber.log.Timber;

public class SearchFormFragment extends BaseMapFragment {
    public static final String DESTINATION_LOADING_DIALOG_TAG = "destination loading dialog";
    public static final String LOCATION_ERROR_DIALOG_TAG = "location error dialog";
    public static final String NEAREST_LOCATION_LOADING_DIALOG_TAG = "nearest location loading dialog";
    private static final int PIN_APPEARING_DELAY = 700;
    private View cityImageContainer;
    private CityImageLoader cityImageLoader;
    @Nullable
    private SimpleDraweeView cityPhoto;
    private AlertDialog destinationLoadingDialog;
    private EventBus eventBus;
    private GoogleMap googleMap;
    private View imageOverlayLabels;
    private View imageOverlayLabelsContainer;
    @Nullable
    public LaunchParams launchParams;
    private AlertDialog locationLoadingDialog;
    private Observable<Location> locationObservable;
    private LocationRequestResolver locationRequestResolver;
    private Subscription locationSubscription;
    private View mapContainer;
    private MapView mapView;
    private Marker marker;
    private TextView overlayCityNameTxt;
    private TextView overlayHotelsNumTxt;
    private View searchFormContainer;
    private SearchFormData searchFormData;
    private SearchFormMarkerAnimator searchFormMarkerAnimator;
    private SearchFormView searchFormView;
    public CallbackWithTimeout searchingForDestinationCallback;
    private Queue<Runnable> tasksToExecuteAfterResume;

    /* renamed from: com.hotellook.ui.screen.searchform.SearchFormFragment.1 */
    class C13481 implements OnGlobalLayoutListener {
        C13481() {
        }

        public void onGlobalLayout() {
            if (SearchFormFragment.this.cityPhoto != null) {
                AndroidUtils.removeOnGlobalLayoutListener(SearchFormFragment.this.cityPhoto, this);
                SearchFormFragment.this.addCityPhotoToImage();
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchform.SearchFormFragment.2 */
    class C13492 extends MonkeySafeClickListener {
        C13492() {
        }

        public void onSafeClick(View v) {
            SearchFormFragment.this.launchDestinationPicker();
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchform.SearchFormFragment.3 */
    class C13503 extends MonkeySafeClickListener {
        C13503() {
        }

        public void onSafeClick(View v) {
            SearchFormFragment.this.returnToSearchResults();
        }
    }

    static class Snapshot {
        final Observable<Location> locationObservable;
        final LocationRequestResolver locationRequestResolver;

        Snapshot(Observable<Location> locationObservable, LocationRequestResolver locationRequestResolver) {
            this.locationObservable = locationObservable;
            this.locationRequestResolver = locationRequestResolver;
        }
    }

    public SearchFormFragment() {
        this.tasksToExecuteAfterResume = new LinkedList();
    }

    public static SearchFormFragment create(LaunchParams launchParams) {
        SearchFormFragment fragment = new SearchFormFragment();
        fragment.setLaunchParams(launchParams);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HotellookApplication app = HotellookApplication.from(getContext());
        this.eventBus = app.getComponent().eventBus();
        app.setAppLaunchedAsExpected();
    }

    protected View inflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(C1178R.layout.fragment_search_form, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (hasInitialSnapshot()) {
            restoreFromSnapshot((Snapshot) initialSnapshot());
        }
        this.searchFormData = new SearchFormData(getApplication(), getComponent().getSearchFormPreferences());
        if (savedInstanceState == null && this.launchParams != null) {
            this.searchFormData.updateWithLaunchParams(this.launchParams);
        }
        postNewDestinationEvent();
        getActivity().getWindow().setSoftInputMode(32);
        setUpToolbar();
        ViewGroup layout = (ViewGroup) view;
        this.searchFormContainer = layout.findViewById(C1178R.id.search_form_container);
        this.searchFormView = (SearchFormView) layout.findViewById(OffersFragment.SEARCH_FORM_ID);
        this.cityImageContainer = layout.findViewById(C1178R.id.city_image_container);
        this.imageOverlayLabelsContainer = layout.findViewById(C1178R.id.city_labels_container);
        this.imageOverlayLabels = layout.findViewById(C1178R.id.city_labels);
        this.overlayCityNameTxt = (TextView) layout.findViewById(C1178R.id.city_name);
        this.overlayHotelsNumTxt = (TextView) layout.findViewById(C1178R.id.hotels_num);
        this.cityPhoto = (SimpleDraweeView) layout.findViewById(C1178R.id.iv_city);
        this.cityImageLoader = new SearchFormCityImageLoader(this.cityPhoto);
        this.mapContainer = layout.findViewById(C1178R.id.map_container);
        this.mapView = (MapView) layout.findViewById(C1178R.id.map);
        layout.findViewById(C1178R.id.map_overlay).setOnClickListener(SearchFormFragment$$Lambda$1.lambdaFactory$(this));
        if (this.searchFormContainer != null) {
            AndroidUtils.addMarginToPlaceViewBelowStatusBar(this.searchFormContainer);
            AndroidUtils.addMarginToPlaceViewBelowToolbar(this.searchFormContainer);
        }
        if (this.imageOverlayLabelsContainer != null) {
            AndroidUtils.addMarginToPlaceViewBelowStatusBar(this.imageOverlayLabelsContainer);
        }
        setUpImageLabel(this.imageOverlayLabels);
        onMapViewCreated(this.mapView, savedInstanceState);
        setUpData();
        if (this.searchFormData.hasData()) {
            this.cityPhoto.getViewTreeObserver().addOnGlobalLayoutListener(new C13481());
        }
        if (hasDestinationInfoInLaunchParams() && this.searchFormData.noData()) {
            findDestination(this.launchParams);
        } else if (this.searchFormData.hasData()) {
            launchSearchByStartParamsIfNeed();
        }
        if (this.locationObservable != null) {
            subscribeToLocationUpdates();
        }
        this.eventBus.register(this);
    }

    /* synthetic */ void lambda$onViewCreated$0(View v) {
        launchDestinationPicker();
    }

    private void observeCoordinatesSearchRequest() {
        LocationRequest locationRequest = createFindLocationRequest();
        this.locationRequestResolver = getComponent().locationRequestResolver();
        this.locationObservable = this.locationRequestResolver.resolveLocationRequest(locationRequest).doOnNext(SearchFormFragment$$Lambda$2.lambdaFactory$(this)).flatMap(SearchFormFragment$$Lambda$3.lambdaFactory$(locationRequest)).replay().autoConnect();
    }

    /* synthetic */ void lambda$observeCoordinatesSearchRequest$1(Boolean ok) {
        onLocationSearchStarted();
    }

    private void subscribeToLocationUpdates() {
        RxUtil.safeUnsubscribe(this.locationSubscription);
        this.locationSubscription = this.locationObservable.subscribe(SearchFormFragment$$Lambda$4.lambdaFactory$(this), SearchFormFragment$$Lambda$5.lambdaFactory$(this));
        keepUntilDestroyView(this.locationSubscription);
    }

    private void postNewDestinationEvent() {
        this.eventBus.post(new NewDestinationEvent(this.searchFormData.getCityId(), this.searchFormData.getHotelId(), this.searchFormData.getDestinationType(), AndroidUtils.getLanguage()));
    }

    private void onLocationSearchStarted() {
        if (getView() != null) {
            getView().post(SearchFormFragment$$Lambda$6.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$onLocationSearchStarted$3() {
        this.searchFormView.showLocationSearchAnimation();
    }

    private void onLocationSearchFinished() {
        this.searchFormView.switchToLocationFoundState();
    }

    @NonNull
    private LocationRequest createFindLocationRequest() {
        return LocationRequest.create().setPriority(100).setNumUpdates(1).setInterval(1000);
    }

    private void handleLocationErrors(Throwable error) {
        cleanLocationObservingObjects();
        if (error instanceof PermissionDeniedException) {
            onLocationPermissionDenied();
        } else if (error instanceof PlayServicesNotAvailableException) {
            onNoPlayServices();
        } else if (error instanceof InvalidLocationSettingsException) {
            onInvalidLocationSettings();
        } else {
            Timber.m757w(error, "Failed to get location", new Object[0]);
            Toasts.showErrorFindLocation(getContext());
        }
        onLocationSearchFinished();
    }

    private void onNoPlayServices() {
        Timber.m756w("No play services.", new Object[0]);
        Toasts.showNoPlayServicesToast(getContext());
    }

    private void onLocationPermissionDenied() {
        Timber.m756w("Location permission was not granted.", new Object[0]);
        Toasts.showLocationPermissionDeniedToast(getContext());
    }

    private void onInvalidLocationSettings() {
        Timber.m756w("Invalid location settings.", new Object[0]);
        Toasts.showInvalidLocationSettingsToast(getContext());
    }

    private void onLocationUpdate(Location location) {
        cleanLocationObservingObjects();
        this.searchFormData.updateWithUserLocationDestination(location);
        this.searchFormView.setUpData(this.searchFormData);
        LatLng newPosition = LocationUtils.toLatLng(location);
        CameraUpdate cameraUpdate = createCameraUpdateForMap(newPosition);
        addUserLocationMarker(newPosition);
        if (this.mapContainer.getVisibility() == 8) {
            this.mapContainer.setVisibility(0);
            this.googleMap.moveCamera(cameraUpdate);
            if (AndroidUtils.isTablet(getContext()) || AndroidUtils.isLandscape(getContext())) {
                moveCameraToShowMarkerAboveSearchForm(newPosition);
            }
            animateMapAndPinAppearing();
        } else {
            if (AndroidUtils.isTablet(getContext()) || AndroidUtils.isLandscape(getContext())) {
                cameraUpdate = createCameraUpdateToShowMarkerAboveSearchForm(newPosition);
            }
            this.googleMap.animateCamera(cameraUpdate);
            this.marker.setAlpha(1.0f);
        }
        onLocationSearchFinished();
    }

    private void cleanLocationObservingObjects() {
        RxUtil.safeUnsubscribe(this.locationSubscription);
        this.locationObservable = null;
        this.locationRequestResolver = null;
    }

    private void addUserLocationMarker(LatLng newPosition) {
        if (this.marker == null) {
            this.marker = this.googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(C1178R.drawable.user_map_pin)).anchor(0.5f, 0.5f).position(newPosition).alpha(0.0f));
        } else {
            this.marker.setPosition(newPosition);
        }
        this.searchFormMarkerAnimator = new SearchFormMarkerAnimator(getResources(), this.marker);
    }

    @NonNull
    private CameraUpdate createCameraUpdateForMap(LatLng newPosition) {
        return CameraUpdateFactory.newLatLngZoom(newPosition, 10.0f);
    }

    private void animateMapAndPinAppearing() {
        if (this.cityImageContainer != null) {
            this.cityImageContainer.animate().alpha(0.0f).withEndAction(SearchFormFragment$$Lambda$7.lambdaFactory$(this));
        }
        if (this.overlayCityNameTxt != null) {
            ((View) this.overlayCityNameTxt.getParent()).animate().alpha(0.0f);
        }
        showPinAppearingAnimation();
    }

    /* synthetic */ void lambda$animateMapAndPinAppearing$4() {
        this.cityImageContainer.setVisibility(8);
    }

    public boolean isFloatingSearchForm() {
        return AndroidUtils.isLandscape(getActivity()) || AndroidUtils.isTablet(getActivity());
    }

    public void setUpImageLabel(View imageLabelOverlay) {
        if (imageLabelOverlay != null) {
            imageLabelOverlay.setOnClickListener(new C13492());
        }
    }

    public void onDestroyView() {
        this.cityPhoto = null;
        this.overlayCityNameTxt = null;
        this.overlayHotelsNumTxt = null;
        this.searchFormView = null;
        this.searchFormData = null;
        this.searchFormContainer = null;
        this.imageOverlayLabels = null;
        this.imageOverlayLabelsContainer = null;
        dismissNearestLocationLoadingDialog();
        dismissDestinationLoadingDialog();
        clearMap();
        this.eventBus.unregister(this);
        super.onDestroyView();
    }

    private void clearMap() {
        if (this.searchFormMarkerAnimator != null) {
            this.searchFormMarkerAnimator.release();
        }
        if (this.googleMap != null) {
            this.googleMap.clear();
        }
        this.marker = null;
    }

    private void setUpToolbar() {
        ToolbarSettings settings = new ToolbarSettings();
        if (hasResultsForCurrentSearch()) {
            View returnToSearchResultsBtn = LayoutInflater.from(getActivity()).inflate(C1178R.layout.return_to_search_results_view, getToolbar(), false);
            returnToSearchResultsBtn.setOnClickListener(new C13503());
            settings = settings.withCustomView(returnToSearchResultsBtn);
        }
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), settings);
    }

    private boolean hasResultsForCurrentSearch() {
        Search lastSearch = getComponent().searchKeeper().lastSearch();
        if (lastSearch == null || lastSearch.searchData() == null) {
            return false;
        }
        return this.searchFormData.toSearchParams(getCurrencyCode(), areEnGatesAllowed()).equals(lastSearch.searchParams());
    }

    private void returnToSearchResults() {
        if (((SearchData) Preconditions.checkNotNull(((Search) Preconditions.checkNotNull(getComponent().searchKeeper().lastSearch())).searchData())).hotels().isEmpty()) {
            getMainActivity().showFragment(new SearchPlaceHolderFragment());
        } else {
            getMainActivity().showFragment(new SearchResultsFragment());
        }
    }

    @Subscribe
    public void onSearchParamsUpdated(SearchParamsUpdatedEvent event) {
        setUpToolbar();
    }

    @Subscribe
    public void onLocationBtnClicked(LocationButtonClickEvent event) {
        if (this.mapContainer.getVisibility() == 0) {
            this.searchFormMarkerAnimator.animatePinBounce();
        }
        observeCoordinatesSearchRequest();
        subscribeToLocationUpdates();
    }

    private void launchDestinationPicker() {
        MainActivity activity = getMainActivity();
        if (activity != null) {
            activity.showFragment(new DestinationPickerFragment());
        }
    }

    private void showDestinationLoadingDialog() {
        if (this.destinationLoadingDialog == null) {
            this.destinationLoadingDialog = Dialogs.showDestinationLoadingDialog(getActivity());
            this.destinationLoadingDialog.setOnDismissListener(SearchFormFragment$$Lambda$8.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$showDestinationLoadingDialog$5(DialogInterface dialog) {
        setUpDefaultLocationDataIfNecessary();
        this.destinationLoadingDialog = null;
    }

    private void startSearch(Source source) {
        if (this.cityPhoto != null) {
            SearchParams params = this.searchFormData.toSearchParams(getCurrencyCode(), areEnGatesAllowed());
            String imageUrl = null;
            if (!params.isDestinationTypeUserLocation()) {
                imageUrl = this.cityImageLoader.getImageUrl(this.searchFormData.getCityId(), new Size(this.cityPhoto.getWidth(), this.cityPhoto.getHeight()));
            }
            SearchStartEvent event = new SearchStartEvent(params, source, imageUrl);
            getComponent().searchKeeper().clear();
            switch (this.searchFormData.getDestinationType()) {
                case DestinationType.CITY /*401*/:
                    launchSearchProgress(params, event);
                case DestinationType.HOTEL /*402*/:
                    launchHotelSearch(params);
                case DestinationType.USER_LOCATION /*403*/:
                case DestinationType.MAP_POINT /*405*/:
                    launchSearchByCoordinates(params, event);
                default:
                    throw new RuntimeException(String.format("No such destination type: %d", new Object[]{Integer.valueOf(this.searchFormData.getDestinationType())}));
            }
        }
    }

    private boolean areEnGatesAllowed() {
        return getComponent().getCommonPreferences().areEnGatesAllowed();
    }

    private String getCurrencyCode() {
        return getComponent().currencyRepository().currencyCode();
    }

    private void launchSearchProgress(SearchParams params, SearchStartEvent event) {
        getComponent().searchEngine().makeSearch(params, event);
        launchSearchProgressFragment();
    }

    private void launchHotelSearch(SearchParams params) {
        RequestFlags requestFlags = new RequestFlagsGenerator().hotelSearchFromSearchForm(params);
        HotelDataSource hotelDataSource = getComponent().hotelDataSource();
        hotelDataSource.observeFromSearchForm(params, requestFlags);
        getMainActivity().showFragment(HotelFragment.create(hotelDataSource, new HotelScreenOpenInfo(com.hotellook.ui.screen.hotel.Source.HOTEL_SEARCH)));
        this.eventBus.post(new HotelSearchStartEvent());
    }

    private void launchSearchByCoordinates(SearchParams params, SearchStartEvent event) {
        getComponent().searchEngine().makeSearch(params, event);
        launchSearchProgressFragment();
    }

    private void launchSearchProgressFragment() {
        getMainActivity().showFragment(SearchProgressFragment.create());
    }

    private boolean hasDestinationInfoInLaunchParams() {
        return (this.launchParams == null || (TextUtils.isEmpty(this.launchParams.getDestination()) && this.launchParams.getLocationId() == null)) ? false : true;
    }

    @Subscribe
    public void onCityFoundByIata(DestinationFoundEvent event) {
        dismissLoadingDestination();
        this.searchFormData.setCity(event.data);
        this.searchFormData.saveData();
        setUpData();
        addCityPhotoToImage();
        launchSearchByStartParamsIfNeed();
        postNewDestinationEvent();
    }

    public void dismissLoadingDestination() {
        this.searchingForDestinationCallback = null;
        this.launchParams = null;
        dismissDestinationLoadingDialog();
    }

    @Subscribe
    public void onCityFindByIataFailed(FindDestinationFailedEvent event) {
        dismissLoadingDestination();
        setCityIfNoData();
    }

    private void findDestination(LaunchParams launchParams) {
        if (this.searchingForDestinationCallback == null) {
            showDestinationLoadingDialog();
            HotellookService hotellookService = getApplication().getComponent().getHotellookService();
            if (launchParams.getLocationId() != null) {
                DestinationByIdCallback destinationByIdCallback = new DestinationByIdCallback();
                hotellookService.cityInfo(AndroidUtils.getLanguage(), launchParams.getLocationId().intValue(), destinationByIdCallback);
                this.searchingForDestinationCallback = destinationByIdCallback;
                return;
            }
            String destination = launchParams.getDestination();
            DestinationByNameCallback destinationByNameCallback = new DestinationByNameCallback(destination);
            hotellookService.autocomplete(destination, AndroidUtils.getLanguage(), destinationByNameCallback);
            this.searchingForDestinationCallback = destinationByNameCallback;
        }
    }

    private void setCityIfNoData() {
        if (this.searchFormData.noData() && this.launchParams == null) {
            this.searchFormData.addDefaultCityData();
            setUpData();
            addCityPhotoToImage();
        }
    }

    private void setUpDefaultLocationDataIfNecessary() {
        if (this.searchFormData != null && this.searchFormData.noData()) {
            this.searchFormData.addDefaultCityData();
            setUpData();
            addCityPhotoToImage();
        }
    }

    private void setUpData() {
        setUpImageOverlayData();
        this.searchFormView.setUpData(this.searchFormData);
        setUpCoordinatesSearch(this.googleMap);
    }

    private void setUpImageOverlayData() {
        if (!hasImageOverlayViews()) {
            return;
        }
        if (this.searchFormData.noData()) {
            this.overlayCityNameTxt.setText(BuildConfig.FLAVOR);
            this.overlayHotelsNumTxt.setText(BuildConfig.FLAVOR);
            this.overlayCityNameTxt.setVisibility(4);
            this.overlayHotelsNumTxt.setVisibility(4);
            return;
        }
        this.overlayCityNameTxt.setTextSize(0, getResources().getDimension(C1178R.dimen.sf_image_label_overlay_default_text_size));
        this.overlayCityNameTxt.setText(this.searchFormData.getCity().toUpperCase());
        this.overlayHotelsNumTxt.setText(this.searchFormData.getHotelsNumTxt(getActivity()));
        this.overlayCityNameTxt.setVisibility(0);
        this.overlayHotelsNumTxt.setVisibility(0);
    }

    private boolean hasImageOverlayViews() {
        return this.overlayCityNameTxt != null;
    }

    private void addCityPhotoToImage() {
        if (this.cityPhoto != null) {
            this.cityPhoto.post(SearchFormFragment$$Lambda$9.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$addCityPhotoToImage$6() {
        if (this.cityPhoto != null) {
            this.cityImageLoader.loadImage(this.cityPhoto, new Size(this.cityPhoto.getWidth(), this.cityPhoto.getHeight()), this.searchFormData.getCityId());
        }
    }

    private void launchSearchByStartParamsIfNeed() {
        if (this.launchParams != null && this.launchParams.isSearchStartRequested() && this.searchFormData.hasData()) {
            startSearch(Source.URL_SCHEME);
            this.launchParams = null;
        }
    }

    private boolean isNotAvailableForUiUpdates() {
        return getActivity() == null || !isVisible();
    }

    @Subscribe
    public void onSearchButtonClickEvent(SearchButtonClickEvent event) {
        startSearch(Source.SEARCH_FORM);
    }

    private void dismissNearestLocationLoadingDialog() {
        if (this.locationLoadingDialog != null) {
            dismissDialog(this.locationLoadingDialog);
            this.locationLoadingDialog = null;
        }
    }

    private void dismissDestinationLoadingDialog() {
        if (this.destinationLoadingDialog != null) {
            dismissDialog(this.destinationLoadingDialog);
            this.destinationLoadingDialog = null;
        }
    }

    private void dismissDialog(AlertDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void onResume() {
        super.onResume();
        Calendar today = DateUtils.getTodayCalendar();
        if (DateUtils.isPreviousDayActualAnywhereOnThePlanet()) {
            today.add(5, -1);
        }
        if (this.searchFormData != null && this.searchFormData.getCheckInDate().before(today.getTime())) {
            this.searchFormData = new SearchFormData(getApplication(), HotellookApplication.getApp().getComponent().getSearchFormPreferences());
            setUpData();
        }
        executeOnResumeTasks();
    }

    private void executeOnResumeTasks() {
        for (Runnable task : this.tasksToExecuteAfterResume) {
            task.run();
        }
        this.tasksToExecuteAfterResume.clear();
    }

    @Subscribe
    public void onNewLaunchParamsEvent(NewLaunchParamsEvent event) {
        this.launchParams = event.launchParams;
        this.searchFormData.updateWithLaunchParams(event.launchParams);
        if (hasDestinationInfoInLaunchParams()) {
            findDestination(this.launchParams);
        } else {
            launchSearchByStartParamsIfNeed();
        }
    }

    private void setLaunchParams(LaunchParams launchParams) {
        this.launchParams = launchParams;
    }

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setMapImmutableMode(googleMap);
        setUpCoordinatesSearch(googleMap);
    }

    private void setUpCoordinatesSearch(GoogleMap googleMap) {
        if (this.searchFormData != null && this.searchFormData.isMapType() && this.cityImageContainer != null && googleMap != null && getView() != null) {
            getView().post(SearchFormFragment$$Lambda$10.lambdaFactory$(this, googleMap));
        }
    }

    /* synthetic */ void lambda$setUpCoordinatesSearch$7(GoogleMap googleMap) {
        if (this.searchFormData != null) {
            this.cityImageContainer.setVisibility(8);
            this.mapContainer.setVisibility(0);
            LatLng location = LocationUtils.toLatLng(this.searchFormData.getLocation());
            googleMap.moveCamera(createCameraUpdateForMap(location));
            if (AndroidUtils.isTablet(getContext()) || AndroidUtils.isLandscape(getContext())) {
                moveCameraToShowMarkerAboveSearchForm(location);
            }
            addUserLocationMarker(location);
            showPinAppearingAnimation();
        }
    }

    private void moveCameraToShowMarkerAboveSearchForm(LatLng location) {
        this.googleMap.setOnCameraChangeListener(SearchFormFragment$$Lambda$11.lambdaFactory$(this, location));
    }

    /* synthetic */ void lambda$moveCameraToShowMarkerAboveSearchForm$8(LatLng location, CameraPosition cameraPosition) {
        if (getActivity() == null || this.searchFormView == null) {
            this.googleMap.setOnCameraChangeListener(null);
            return;
        }
        this.googleMap.moveCamera(createCameraUpdateToShowMarkerAboveSearchForm(location));
        this.googleMap.setOnCameraChangeListener(null);
    }

    @NonNull
    private CameraUpdate createCameraUpdateToShowMarkerAboveSearchForm(LatLng location) {
        Projection projection = this.googleMap.getProjection();
        int neededShift = (this.mapView.getHeight() / 2) - ((AndroidUtils.getViewYLocationOnScreen(this.searchFormView) / 2) + (AndroidUtils.getStatusBarHeight(getContext()) / 2));
        Point centerPoint = projection.toScreenLocation(location);
        return createCameraUpdateForMap(projection.fromScreenLocation(new Point(centerPoint.x, centerPoint.y + neededShift)));
    }

    private void showPinAppearingAnimation() {
        this.searchFormMarkerAnimator.animateAppearing(PIN_APPEARING_DELAY);
    }

    @Nullable
    public Object takeSnapshot() {
        return new Snapshot(this.locationObservable, this.locationRequestResolver);
    }

    private void restoreFromSnapshot(@NonNull Snapshot snapshot) {
        this.locationObservable = snapshot.locationObservable;
        this.locationRequestResolver = snapshot.locationRequestResolver;
    }
}
