package com.hotellook.ui.screen.favorite.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hotellook.C1178R;
import com.hotellook.api.data.DestinationType;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.events.OpenHotelDetailsEvent;
import com.hotellook.events.SearchFailEvent;
import com.hotellook.events.SearchFinishEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.interactor.favorites.FavoriteCityData;
import com.hotellook.interactor.favorites.FavoritesInteractor;
import com.hotellook.interactor.favorites.FavoritesParsedData;
import com.hotellook.interactor.favorites.HotelsByCities;
import com.hotellook.search.KidsSerializer;
import com.hotellook.search.Search;
import com.hotellook.search.SearchKeeper;
import com.hotellook.search.SearchParams;
import com.hotellook.search.SearchParams.Builder;
import com.hotellook.search.ServerDateFormatter;
import com.hotellook.ui.screen.favorite.router.FavoritesRouter;
import com.hotellook.ui.screen.favorite.view.FavoritesView;
import com.hotellook.ui.screen.hotel.HotelScreenOpenInfo;
import com.hotellook.ui.screen.hotel.Source;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CommonPreferences;
import com.hotellook.utils.EventBus;
import com.hotellook.utils.LocationUtils;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import javax.inject.Inject;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavoritesPresenter extends MvpBasePresenter<FavoritesView> {
    public static final int CITY_ID_CURRENT_SEARCH = -1;
    private final CommonPreferences commonPreferences;
    private final CurrencyRepository currencyRepository;
    @Nullable
    private String currentSearchString;
    private final EventBus eventBus;
    private final FavoritesInteractor favoritesInteractor;
    private final HotelDataSource hotelDataSource;
    @Nullable
    private FavoritesParsedData parsedData;
    private final FavoritesRouter router;
    private final SearchKeeper searchKeeper;
    private int selectedCityPosition;
    @Nullable
    private Subscription subscription;

    @Inject
    public FavoritesPresenter(FavoritesInteractor favoritesInteractor, SearchKeeper searchKeeper, EventBus eventBus, HotelDataSource hotelDataSource, CommonPreferences commonPreferences, CurrencyRepository currencyRepository, FavoritesRouter router) {
        this.selectedCityPosition = 0;
        this.favoritesInteractor = favoritesInteractor;
        this.searchKeeper = searchKeeper;
        this.eventBus = eventBus;
        this.hotelDataSource = hotelDataSource;
        this.commonPreferences = commonPreferences;
        this.currencyRepository = currencyRepository;
        this.router = router;
    }

    public void attachView(FavoritesView view) {
        super.attachView(view);
        this.currentSearchString = view.getResources().getString(C1178R.string.current_search);
        this.eventBus.register(this);
    }

    public void detachView(boolean retainInstance) {
        this.eventBus.unregister(this);
        if (this.subscription != null) {
            this.subscription.unsubscribe();
        }
        super.detachView(retainInstance);
    }

    @Subscribe
    public void onSearchStarted(SearchStartEvent event) {
        loadData();
    }

    @Subscribe
    public void onSearchFinished(SearchFinishEvent event) {
        loadData();
    }

    @Subscribe
    public void onSearchFailed(SearchFailEvent event) {
        showResults();
    }

    public void loadData() {
        this.subscription = this.favoritesInteractor.favoriteData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(FavoritesPresenter$$Lambda$1.lambdaFactory$(this)).map(FavoritesPresenter$$Lambda$2.lambdaFactory$(this)).subscribe(FavoritesPresenter$$Lambda$3.lambdaFactory$(this), FavoritesPresenter$$Lambda$4.lambdaFactory$(this));
    }

    @NonNull
    private FavoritesParsedData toParsedData(HotelsByCities hotelsByCities) {
        List<FavoriteCityData> mergedCities = new CitiesMerge(this.parsedData).mergeWith(extractCities(hotelsByCities));
        Collections.sort(mergedCities, new FavoriteCityDataComparator());
        return new FavoritesParsedData(mergedCities, hotelsByCities);
    }

    private void addCurrentSearch(HotelsByCities hotelsByCities) {
        List<FavoriteHotelDataEntity> currentSearchHotels = currentSearchHotels(hotelsByCities);
        if (currentSearchHotels != null && notAHotelSearch()) {
            hotelsByCities.put(Integer.valueOf(CITY_ID_CURRENT_SEARCH), currentSearchHotels);
        }
    }

    private boolean notAHotelSearch() {
        return this.searchKeeper == null || this.searchKeeper.lastSearch() == null || this.searchKeeper.lastSearch().searchData() == null || !this.searchKeeper.lastSearch().searchData().searchParams().isHotelSearch();
    }

    private List<FavoriteCityData> extractCities(HotelsByCities hotelsByCities) {
        List<FavoriteCityData> cities = new ArrayList();
        for (Entry<Integer, List<FavoriteHotelDataEntity>> entry : hotelsByCities.entrySet()) {
            int locationId = ((Integer) entry.getKey()).intValue();
            List<FavoriteHotelDataEntity> hotels = (List) entry.getValue();
            if (locationId == CITY_ID_CURRENT_SEARCH) {
                cities.add(new FavoriteCityData(locationId, this.currentSearchString, hotels.size()));
            } else if (!hotels.isEmpty()) {
                cities.add(new FavoriteCityData(locationId, ((FavoriteHotelDataEntity) hotels.get(0)).getLocationName(), hotels.size()));
            }
        }
        return cities;
    }

    public void onCitySelected(int position) {
        if (this.selectedCityPosition != position) {
            this.selectedCityPosition = position;
            showResults();
        }
    }

    private void onLoadError(Throwable throwable) {
        showResults();
    }

    private void onLoadSuccess(FavoritesParsedData parsedData) {
        this.parsedData = parsedData;
        showResults();
    }

    private void showResults() {
        FavoritesView view = (FavoritesView) getView();
        if (view != null) {
            if (hasNoSelector()) {
                onNothingToShow(view);
                return;
            }
            ((FavoritesView) getView()).showCitiesSelector(this.parsedData.cities(), this.selectedCityPosition);
            onHasSomethingToShow(view);
        }
    }

    private void onNothingToShow(FavoritesView view) {
        view.hideCitySelector();
        if (searchStarted()) {
            view.showPlaceHolderAddFirstFromResults();
        } else {
            view.showPlaceHolderAddFirstHotelAndStartSearch();
        }
    }

    private void onHasSomethingToShow(FavoritesView view) {
        onShowData(this.parsedData);
    }

    private void onShowData(FavoritesParsedData parsedData) {
        List<FavoriteHotelDataEntity> selectedHotels = parsedData.hotels(((FavoriteCityData) parsedData.cities().get(this.selectedCityPosition)).id);
        if (selectedHotels != null && !selectedHotels.isEmpty()) {
            showCurrentData();
        } else if (noHotelsInTabs(parsedData)) {
            ((FavoritesView) getView()).hideCitySelector();
            ((FavoritesView) getView()).showPlaceHolderAddFirstFromResults();
        } else if (isCurrentSearch(parsedData)) {
            ((FavoritesView) getView()).showPlaceHolderAddFirstFromResults();
        } else {
            ((FavoritesView) getView()).showPlaceholderSwitchCity();
        }
    }

    private boolean isCurrentSearch(FavoritesParsedData parsedData) {
        return ((FavoriteCityData) parsedData.cities().get(this.selectedCityPosition)).id == CITY_ID_CURRENT_SEARCH;
    }

    private boolean noHotelsInTabs(FavoritesParsedData parsedData) {
        return parsedData.hotels().size() == 0;
    }

    private boolean hasNoSelector() {
        return this.parsedData == null || this.parsedData.cities() == null || this.parsedData.cities().isEmpty();
    }

    private void showCurrentData() {
        ((FavoritesView) getView()).showCitiesSelector(this.parsedData.cities(), this.selectedCityPosition);
        ((FavoritesView) getView()).showHotels(this.parsedData.hotels(((FavoriteCityData) this.parsedData.cities().get(this.selectedCityPosition)).id));
    }

    private List<FavoriteHotelDataEntity> findFavoritesCitiesInResults(HotelsByCities hotels) {
        Search search = this.searchKeeper.lastSearch();
        if (!hasNoSearchResults(search)) {
            List<HotelData> all = search.filters().getSortedHotels();
            List<FavoriteHotelDataEntity> allFavoritesHotels = hotels.all();
            List<FavoriteHotelDataEntity> result = new ArrayList();
            for (FavoriteHotelDataEntity favoriteHotel : allFavoritesHotels) {
                for (HotelData hotelInResults : all) {
                    if (favoriteHotel.getHotelId() == hotelInResults.getId()) {
                        result.add(favoriteHotel);
                    }
                }
            }
            return result;
        } else if (hasSearchParams(search)) {
            return hotelsByLocation(search.searchParams().locationId(), hotels);
        } else {
            return new ArrayList();
        }
    }

    private boolean hasSearchParams(Search search) {
        return (search == null || search.searchParams() == null) ? false : true;
    }

    private List<FavoriteHotelDataEntity> hotelsByLocation(int locationId, HotelsByCities hotels) {
        return hotels.get((Object) Integer.valueOf(locationId));
    }

    private boolean hasNoSearchResults(Search search) {
        return search == null || search.searchData() == null;
    }

    @Nullable
    private List<FavoriteHotelDataEntity> currentSearchHotels(HotelsByCities hotelsByCities) {
        if (searchStarted()) {
            return findFavoritesCitiesInResults(hotelsByCities);
        }
        return null;
    }

    private boolean searchStarted() {
        Search search = this.searchKeeper.lastSearch();
        return hasSearchResults(search) || (search != null && search.isLoading());
    }

    private boolean hasSearchResults(Search search) {
        return !hasNoSearchResults(search);
    }

    @Subscribe
    public void onHotelSelected(OpenHotelDetailsEvent event) {
        FavoriteHotelDataEntity favoriteHotelData = event.favoriteHotelData;
        long hotelId = favoriteHotelData.getHotelId();
        int locationId = favoriteHotelData.getLocationId();
        SearchParams searchParams = generateSearchParams(event);
        Search search = this.searchKeeper.lastSearch();
        if (search == null || search.searchData() == null || !search.searchData().hotels().contains(locationId, hotelId)) {
            this.hotelDataSource.observeFromFavorites(favoriteHotelData, searchParams);
        } else {
            this.hotelDataSource.observeFromSearch(hotelId, search, (List) search.filters().getFilteredOffers().get(Long.valueOf(hotelId)), search.startTimestamp());
        }
        this.router.showHotelScreen(this.hotelDataSource, new HotelScreenOpenInfo(Source.FAVORITES, event.positionInList), event.pageIndex);
    }

    @NonNull
    private SearchParams generateSearchParams(OpenHotelDetailsEvent event) {
        FavoriteHotelDataEntity hotel = event.favoriteHotelData;
        ServerDateFormatter dateFormatter = new ServerDateFormatter();
        return new Builder().locationId(hotel.getLocationId()).hotelId(hotel.getHotelId()).checkIn(dateFormatter.parse(hotel.getCheckIn())).checkOut(dateFormatter.parse(hotel.getCheckOut())).adults(hotel.getAdults()).kids(KidsSerializer.parse(hotel.getChildren())).location(LocationUtils.from(hotel.getLocation())).destinationName(hotel.getLocationName()).currency(this.currencyRepository.currencyCode()).destinationType(DestinationType.HOTEL).language(AndroidUtils.getLanguage()).allowEnGates(this.commonPreferences.areEnGatesAllowed()).build();
    }

    public PresenterSnapshot saveState() {
        return new PresenterSnapshot(this.selectedCityPosition, this.parsedData);
    }

    public void restoreState(@Nullable PresenterSnapshot presenterSate) {
        if (!(presenterSate == null || presenterSate.favoritesParsedData == null)) {
            this.parsedData = presenterSate.favoritesParsedData;
            this.selectedCityPosition = presenterSate.selectedPosition;
        }
        loadData();
    }
}
