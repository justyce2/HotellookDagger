package com.hotellook.ui.screen.hotel.general;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.backstack.Savable;
import com.hotellook.badges.Badge;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.common.District;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.core.api.pojo.hoteldetail.AmenityData;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.events.HotelMapClickEvent;
import com.hotellook.events.SearchFailEvent;
import com.hotellook.events.SearchFinishEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.events.ShowLoadedHotelInfoContentEvent;
import com.hotellook.events.StreetViewBtnClickEvent;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.distancetarget.MyLocationDistanceTarget;
import com.hotellook.filters.distancetarget.PoiDistanceTarget;
import com.hotellook.filters.distancetarget.PoiHistoryDistanceTarget;
import com.hotellook.search.Search;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.common.BaseMapFragment;
import com.hotellook.ui.screen.filters.SeasonsUtils;
import com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout;
import com.hotellook.ui.screen.hotel.OffersLoaderStateModel;
import com.hotellook.ui.screen.hotel.Source;
import com.hotellook.ui.screen.hotel.amenity.AmenitiesLayout;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.hotel.distance.DistanceItem;
import com.hotellook.ui.screen.hotel.distance.DistanceItem.Factory;
import com.hotellook.ui.screen.hotel.distance.DistancesView;
import com.hotellook.ui.screen.hotel.features.HotelFeaturesView;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Clipboard;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.LocationUtils;
import com.hotellook.utils.PoiFilter;
import com.hotellook.utils.StringUtils;
import com.hotellook.utils.ViewUtils;
import com.squareup.otto.Subscribe;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HotelGeneralFragment extends BaseMapFragment implements Savable {
    public static final float GOOD_LOOKING_ZOOM_LEVEL = 16.0f;
    private View addressContainer;
    private TextView bestOfferTitle;
    private BestOfferView bestOfferView;
    private OffersLoaderStateModel bestOffersStateModel;
    private View btnStreetView;
    private View descriptionHeader;
    private TextView descriptionTextView;
    private DistancesView distancesView;
    private HotelFeaturesView featuresView;
    private AmenitiesLayout hotelAmenities;
    private View hotelAmenitiesHeader;
    private HotelDataSource hotelDataSource;
    private HotelInfoLoadableLayout loadableLayout;
    private View mapClickableArea;
    private FrameLayout mapContainer;
    private View mapPlace;
    private AmenitiesLayout roomAmenities;
    private View roomAmenitiesHeader;
    private View scrollContent;
    private int scrollY;
    private Source source;
    private TextView tvAddress;
    private TextView tvLocation;

    /* renamed from: com.hotellook.ui.screen.hotel.general.HotelGeneralFragment.1 */
    class C13201 implements OnGlobalLayoutListener {
        final /* synthetic */ View val$layout;

        C13201(View view) {
            this.val$layout = view;
        }

        public void onGlobalLayout() {
            AndroidUtils.removeOnGlobalLayoutListener(this.val$layout, this);
            if (HotelGeneralFragment.this.getActivity() != null) {
                HotelGeneralFragment.this.loadableLayout.observeState(HotelGeneralFragment.this.hotelDataSource);
                HotelGeneralFragment.this.postSetScrollAction(this.val$layout);
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.general.HotelGeneralFragment.2 */
    class C13212 extends MonkeySafeClickListener {
        C13212() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new StreetViewBtnClickEvent());
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.general.HotelGeneralFragment.3 */
    class C13223 extends MonkeySafeClickListener {
        C13223() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new HotelMapClickEvent());
        }
    }

    static class Snapshot {
        final OffersLoaderStateModel bestOffersStateModel;
        final HotelDataSource hotelDataSource;
        final int scrollY;
        final Source source;

        Snapshot(int scrollY, Source source, HotelDataSource hotelDataSource, OffersLoaderStateModel bestOffersStateModel) {
            this.hotelDataSource = hotelDataSource;
            this.scrollY = scrollY;
            this.source = source;
            this.bestOffersStateModel = bestOffersStateModel;
        }
    }

    public static Fragment create(HotelDataSource hotelDataSource, Source source) {
        HotelGeneralFragment fragment = new HotelGeneralFragment();
        fragment.setHotelDataSource(hotelDataSource);
        fragment.setSource(source);
        return fragment;
    }

    private void setHotelDataSource(HotelDataSource hotelDataSource) {
        this.hotelDataSource = hotelDataSource;
    }

    protected View inflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(C1178R.layout.fragment_hotel_general, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tryRetrieveScrollFromOldLayout();
        injectViews(view);
        setUpViews(savedInstanceState);
        if (this.hotelDataSource.hasBasicData()) {
            fillBasicData();
        } else {
            subscribeToBasicData();
        }
        if (this.hotelDataSource.hasAllHotelData()) {
            fillLoadableData();
        } else {
            subscribeToLoadableData();
        }
        requestInitLoadableLayoutStateOnLayout(view);
        HotellookApplication.eventBus().register(this);
    }

    private void subscribeToLoadableData() {
        keepUntilDestroyView(this.hotelDataSource.allDataObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(HotelGeneralFragment$$Lambda$1.lambdaFactory$(this), HotelGeneralFragment$$Lambda$2.lambdaFactory$()));
    }

    /* synthetic */ void lambda$subscribeToLoadableData$0(Boolean ok) {
        fillLoadableData();
    }

    private void subscribeToBasicData() {
        keepUntilDestroyView(Observable.combineLatest(this.hotelDataSource.basicHotelDataObservable(), this.hotelDataSource.locationObservable(), HotelGeneralFragment$$Lambda$3.lambdaFactory$()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(HotelGeneralFragment$$Lambda$4.lambdaFactory$(this), HotelGeneralFragment$$Lambda$5.lambdaFactory$()));
    }

    /* synthetic */ void lambda$subscribeToBasicData$2(Pair dataPair) {
        fillBasicData();
    }

    private void setUpViews(Bundle savedInstanceState) {
        setUpBestOfferView();
        setUpStreetViewBtn();
        setUpMapView(savedInstanceState);
    }

    private void setUpBestOfferView() {
        if (datesSet()) {
            setBestOfferTitleWithNightsCount();
        } else {
            setDefaultBestOfferTitle();
        }
        if (this.bestOffersStateModel == null) {
            this.bestOffersStateModel = new OffersLoaderStateModel(this.hotelDataSource, this.source);
        }
        this.bestOfferView.setModel(this.bestOffersStateModel);
    }

    private void setDefaultBestOfferTitle() {
        this.bestOfferTitle.setText(C1178R.string.default_best_offer_title);
    }

    private void setBestOfferTitleWithNightsCount() {
        String nightsText = String.format(getResources().getQuantityString(C1178R.plurals.nights, this.hotelDataSource.searchParams().nightsCount()), new Object[]{Integer.valueOf(nights)});
        this.bestOfferTitle.setText(String.format(getString(C1178R.string.best_offer_header), new Object[]{nightsText}));
    }

    private boolean datesSet() {
        SearchParams searchParams = this.hotelDataSource.searchParams();
        return (searchParams == null || searchParams.checkIn() == null || searchParams.checkOut() == null) ? false : true;
    }

    private void fillBasicData() {
        fillBadges();
    }

    private void tryRetrieveScrollFromOldLayout() {
        if (getView() != null) {
            this.scrollY = getView().getScrollY();
        }
    }

    private void requestInitLoadableLayoutStateOnLayout(View layout) {
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new C13201(layout));
    }

    private void postSetScrollAction(View layout) {
        layout.post(HotelGeneralFragment$$Lambda$6.lambdaFactory$(this, layout));
    }

    /* synthetic */ void lambda$postSetScrollAction$4(View layout) {
        layout.setScrollY(this.scrollY);
    }

    private void setUpStreetViewBtn() {
        this.btnStreetView.setOnClickListener(new C13212());
    }

    private void fillBadges() {
        this.featuresView.setUpBadges(getBadges());
    }

    @Nullable
    private List<Badge> getBadges() {
        Search search = relatedSearch();
        return search != null ? search.badges().getBadges(this.hotelDataSource.basicHotelData().id()) : null;
    }

    private void injectViews(View layout) {
        this.bestOfferTitle = (TextView) layout.findViewById(C1178R.id.best_offer_title);
        this.bestOfferView = (BestOfferView) layout.findViewById(C1178R.id.best_offer);
        this.mapContainer = (FrameLayout) layout.findViewById(C1178R.id.map_container);
        this.addressContainer = layout.findViewById(C1178R.id.address_container);
        this.tvAddress = (TextView) layout.findViewById(C1178R.id.hotel_address);
        this.tvLocation = (TextView) layout.findViewById(C1178R.id.hotel_location);
        this.btnStreetView = layout.findViewById(C1178R.id.btn_street_view);
        this.distancesView = (DistancesView) layout.findViewById(C1178R.id.distances);
        this.featuresView = (HotelFeaturesView) layout.findViewById(C1178R.id.features);
        this.descriptionHeader = layout.findViewById(C1178R.id.description_header);
        this.descriptionTextView = (TextView) layout.findViewById(C1178R.id.description_text);
        this.hotelAmenitiesHeader = layout.findViewById(C1178R.id.hotel_amenities_header);
        this.hotelAmenities = (AmenitiesLayout) layout.findViewById(C1178R.id.hotel_amenities);
        this.roomAmenitiesHeader = layout.findViewById(C1178R.id.room_amenities_header);
        this.roomAmenities = (AmenitiesLayout) layout.findViewById(C1178R.id.room_amenities);
        this.scrollContent = layout.findViewById(C1178R.id.content);
        this.mapClickableArea = layout.findViewById(C1178R.id.map_clickable);
        this.mapPlace = layout.findViewById(C1178R.id.map_placeholder);
        this.loadableLayout = (HotelInfoLoadableLayout) layout.findViewById(C1178R.id.loadable_layout);
    }

    private void setUpMapView(Bundle savedInstanceState) {
        if (hasGMS()) {
            MapView mapView = createMapView();
            this.mapContainer.addView(mapView, new LayoutParams(-1, -1));
            onMapViewCreated(mapView, savedInstanceState);
            this.mapClickableArea.setOnClickListener(new C13223());
            return;
        }
        this.mapPlace.setVisibility(8);
    }

    private void fillAddress(HotelDetailData hotelDetailData) {
        String address = hotelDetailData.getAddress();
        String location = locationText(hotelDetailData);
        if (StringUtils.isNotBlank(address)) {
            this.tvAddress.setText(address);
            this.tvLocation.setText(location);
            this.addressContainer.setOnLongClickListener(HotelGeneralFragment$$Lambda$7.lambdaFactory$(this, address));
            return;
        }
        this.addressContainer.setVisibility(8);
        ViewUtils.addTopPadding(this.mapPlace, getStandardOffset());
    }

    /* synthetic */ boolean lambda$fillAddress$5(String address, View v) {
        copyAddressToClipboard(address);
        return true;
    }

    private void copyAddressToClipboard(@NonNull String address) {
        Clipboard.from(getContext()).copyAddress(address);
        Toast.makeText(getContext(), C1178R.string.address_copied_to_clipboard, 1).show();
    }

    private String locationText(HotelDetailData hotelDetailData) {
        StringBuilder locationBuilder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(hotelDetailData.getDistricts())) {
            District district = (District) hotelDetailData.getDistricts().get(0);
            if (district.getNameInfo() != null && StringUtils.isNotBlank(district.getNameInfo().getName())) {
                locationBuilder.append(StringUtils.capitalize(district.getNameInfo().getName())).append(", ");
            }
        }
        locationBuilder.append(hotelDetailData.getLocationName());
        return locationBuilder.toString();
    }

    @NonNull
    private MapView createMapView() {
        GoogleMapOptions options = new GoogleMapOptions();
        options.camera(CameraPosition.fromLatLngZoom(getHotelPosition(), GOOD_LOOKING_ZOOM_LEVEL));
        options.liteMode(true);
        options.mapToolbarEnabled(false);
        return new MapView(getActivity(), options);
    }

    public void onMapReady(GoogleMap googleMap) {
        setMapImmutableMode(googleMap);
        googleMap.addMarker(new MarkerOptions().anchor(0.4f, 0.9f).icon(BitmapDescriptorFactory.fromResource(C1178R.drawable.marker_hotel)).position(getHotelPosition()));
    }

    @NonNull
    private LatLng getHotelPosition() {
        Coordinates location = this.hotelDataSource.basicHotelData().location();
        return new LatLng(location.getLat(), location.getLon());
    }

    public void onDestroyView() {
        super.onDestroyView();
        HotellookApplication.eventBus().unregister(this);
    }

    @Subscribe
    public void onSearchStarted(SearchStartEvent event) {
        updateBestOffer();
        if (this.hotelDataSource.detailHotelData() != null) {
            updateCheckInOutSegment();
        }
    }

    private void updateBestOffer() {
        this.bestOfferView.notifyModelChanged();
    }

    private void updateCheckInOutSegment() {
        fillCheckInOut(this.hotelDataSource.detailHotelData());
    }

    @Subscribe
    public void onSearchResults(SearchFinishEvent event) {
        updateBestOffer();
    }

    @Subscribe
    public void onSearchResultsFailed(SearchFailEvent event) {
        updateBestOffer();
    }

    private void fillLoadableData() {
        HotelDetailData hotelDetailData = this.hotelDataSource.detailHotelData();
        if (hotelDetailData != null) {
            fillAddress(hotelDetailData);
            fillDistances(hotelDetailData);
            fillFeatures(hotelDetailData);
            fillDescription(hotelDetailData);
            fillAllAmenities(hotelDetailData);
            setUpStreetView();
            HotellookApplication.eventBus().post(new ShowLoadedHotelInfoContentEvent());
        }
    }

    private void setUpStreetView() {
        if (this.hotelDataSource.hasStreetView() && hasGMS()) {
            this.btnStreetView.setVisibility(0);
            return;
        }
        this.btnStreetView.setVisibility(8);
        ViewUtils.addBottomPadding(this.mapPlace, getStandardOffset());
    }

    private void fillDistances(@NonNull HotelDetailData hotelDetailData) {
        CityInfo mainPlace;
        Search search = relatedSearch();
        Set categoriesToFilter = getSupportedCategories();
        List<DistanceItem> items = Factory.uniqueFrom(PoiFilter.filter(hotelDetailData.getPois(), categoriesToFilter), hotelDetailData.getPoiDistance());
        SearchParams searchParams = this.hotelDataSource.searchParams();
        if (searchParams == null || !searchParams.isDestinationTypeUserLocation() || search == null) {
            mainPlace = this.hotelDataSource.cityInfo();
        } else {
            mainPlace = search.searchData().locations().nearestTo(hotelDetailData.getLocation());
        }
        items.add(Factory.cityCenter((int) LocationUtils.distanceBetween(mainPlace.getLocation(), hotelDetailData.getLocation()), mainPlace.getCity(), getContext()));
        if (this.hotelDataSource.cityInfo().getId() != hotelDetailData.getLocationId()) {
            double doubleValue = hotelDetailData.getDistance().doubleValue() * 1000.0d;
            items.add(Factory.cityCenter((int) r18, hotelDetailData.getLocationName(), getContext()));
        }
        if (isUserInSameCityAsHotel()) {
            Location userLocation = getComponent().lastKnownLocationProvider().lastKnownLocation();
            if (userLocation != null) {
                float distanceBetween = LocationUtils.distanceBetween(userLocation, LocationUtils.from(hotelDetailData.getLocation()));
                items.add(Factory.myLocation((int) r17, getContext()));
            }
        }
        if (search != null) {
            DistanceItem filterSelectedItem = toDistanceItem(search.filters().getGeneralPage().getDistanceFilterItem().getDistanceTarget(), hotelDetailData);
            if (!(filterSelectedItem == null || alreadyContains(items, filterSelectedItem))) {
                items.add(filterSelectedItem);
            }
        }
        Collections.sort(items);
        this.distancesView.bindTo(items);
    }

    private boolean isUserInSameCityAsHotel() {
        int hotelCityId = this.hotelDataSource.cityInfo().getId();
        List<GeoLocationData> userCitiesLocations = getComponent().nearestLocationsProvider().nearestLocations();
        if (CollectionUtils.isNotEmpty(userCitiesLocations)) {
            for (GeoLocationData locationData : userCitiesLocations) {
                if (hotelCityId == locationData.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean alreadyContains(List<DistanceItem> items, DistanceItem filterSelectedItem) {
        for (DistanceItem item : items) {
            if (item.poiName.equals(filterSelectedItem.poiName)) {
                return true;
            }
        }
        return false;
    }

    private DistanceItem toDistanceItem(DistanceTarget distanceTarget, HotelDetailData hotelDetailData) {
        if (distanceTarget instanceof PoiDistanceTarget) {
            return Factory.from(((PoiDistanceTarget) distanceTarget).getPoi(), hotelDetailData.getLocation());
        }
        if (distanceTarget instanceof PoiHistoryDistanceTarget) {
            return Factory.from(((PoiHistoryDistanceTarget) distanceTarget).getPoiHistoryItem().toPoi(), hotelDetailData.getLocation());
        }
        if (distanceTarget instanceof MyLocationDistanceTarget) {
            return Factory.myLocation((int) LocationUtils.distanceBetween(LocationUtils.from(hotelDetailData.getLocation()), ((MyLocationDistanceTarget) distanceTarget).location), getContext());
        }
        return null;
    }

    @NonNull
    private Set<String> getSupportedCategories() {
        Set<String> categoriesToFilter = new HashSet();
        categoriesToFilter.addAll(Arrays.asList(new String[]{Poi.CATEGORY_AIRPORT, Poi.CATEGORY_METRO_STATION, Poi.CATEGORY_TRAIN_STATION}));
        Search search = relatedSearch();
        if (search != null) {
            categoriesToFilter.addAll(SeasonsUtils.seasonsPoiCategories(search.searchData().seasons().inLocation(this.hotelDataSource.cityInfo().getId())));
        }
        return categoriesToFilter;
    }

    private void fillFeatures(@NonNull HotelDetailData data) {
        fillCheckInOut(data);
        fillAccommodationType(data);
        this.featuresView.setUpParking(data.getHotelAmenities());
        this.featuresView.setUpStaffLanguage(data.getLanguageAmenities());
    }

    private void fillCheckInOut(@NonNull HotelDetailData data) {
        Calendar checkInDate = null;
        Calendar checkOutDate = null;
        SearchParams searchParams = getSearchParams();
        if (searchParams != null) {
            checkInDate = searchParams.checkIn();
            checkOutDate = searchParams.checkOut();
        }
        this.featuresView.setUpCheckInOut(data.getCheckIn(), data.getCheckOut(), checkInDate, checkOutDate);
    }

    private void fillAccommodationType(@NonNull HotelDetailData data) {
        int adultsCount = 0;
        int childrenCount = 0;
        SearchParams searchParams = getSearchParams();
        if (searchParams != null) {
            adultsCount = searchParams.adults();
            childrenCount = searchParams.kidsCount();
        }
        this.featuresView.setUpAccommodationType(data.getPropertyTypeName(), adultsCount, childrenCount);
    }

    @Nullable
    private SearchParams getSearchParams() {
        return this.hotelDataSource.searchParams();
    }

    private void fillDescription(HotelDetailData hotelDetailData) {
        String description = hotelDetailData.getDescription();
        if (StringUtils.isBlank(description)) {
            this.descriptionHeader.setVisibility(8);
            this.descriptionTextView.setVisibility(8);
            return;
        }
        this.descriptionTextView.setText(description);
    }

    private void fillAllAmenities(HotelDetailData hotelDetailData) {
        fillAmenitiesGroup(hotelDetailData.getHotelAmenities(), this.hotelAmenitiesHeader, this.hotelAmenities);
        fillAmenitiesGroup(hotelDetailData.getRoomAmenities(), this.roomAmenitiesHeader, this.roomAmenities);
    }

    private void fillAmenitiesGroup(List<AmenityData> amenities, View header, AmenitiesLayout amenitiesLayout) {
        if (amenities == null || amenities.size() <= 0) {
            header.setVisibility(8);
            amenitiesLayout.setVisibility(8);
            return;
        }
        amenitiesLayout.setData(amenities);
    }

    public void onPause() {
        super.onPause();
        this.scrollY = getView().getScrollY();
    }

    @Nullable
    private Search relatedSearch() {
        SearchParams searchParams = this.hotelDataSource.searchParams();
        Search lastSearch = getComponent().searchKeeper().lastSearch();
        return (searchParams == null || lastSearch == null || !searchParams.equals(lastSearch.searchParams()) || lastSearch.isLoading()) ? null : lastSearch;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    private int lastScrollY() {
        return getView() == null ? this.scrollY : getView().getScrollY();
    }

    @NonNull
    public Object saveState() {
        return new Snapshot(lastScrollY(), this.source, this.hotelDataSource, this.bestOffersStateModel);
    }

    public void restoreState(@NonNull Object state) {
        Snapshot snapshot = (Snapshot) state;
        this.source = snapshot.source;
        this.scrollY = snapshot.scrollY;
        this.hotelDataSource = snapshot.hotelDataSource;
        this.bestOffersStateModel = snapshot.bestOffersStateModel;
    }
}
