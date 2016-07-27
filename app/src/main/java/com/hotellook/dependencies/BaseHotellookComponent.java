package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.api.ShortUrlService;
import com.hotellook.api.abtesting.ABTesting;
import com.hotellook.api.data.SearchFormPreferences;
import com.hotellook.api.dataloaders.MinPricesLoader;
import com.hotellook.api.dataloaders.StreetViewCheckLoader;
import com.hotellook.api.exchange.CurrencyConverter;
import com.hotellook.api.exchange.ExchangeRateSource;
import com.hotellook.api.trackers.RateDialogConditionsTracker;
import com.hotellook.api.trackers.RequestFlagsHelperTracker;
import com.hotellook.api.trackers.SearchTracker;
import com.hotellook.backstack.BackStackManager;
import com.hotellook.backstack.SnapshotKeeper;
import com.hotellook.badges.Badges;
import com.hotellook.core.api.HotellookClient;
import com.hotellook.core.api.HotellookImageUrlProvider;
import com.hotellook.core.api.HotellookService;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.db.DatabaseHelper;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.db.PoiHistoryDBHelper;
import com.hotellook.db.SearchDestinationFavoritesCache;
import com.hotellook.dependencies.qualifier.Host;
import com.hotellook.dependencies.qualifier.Token;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.location.LastKnownLocationProvider;
import com.hotellook.location.LocationRequestResolver;
import com.hotellook.location.NearestLocationsProvider;
import com.hotellook.location.NearestLocationsSource;
import com.hotellook.search.AsyncOffersSource;
import com.hotellook.search.DumpCacher;
import com.hotellook.search.DumpSource;
import com.hotellook.search.HotelDetailSource;
import com.hotellook.search.LocationDumpSource;
import com.hotellook.search.LocationIdsSource;
import com.hotellook.search.LocationsSource;
import com.hotellook.search.NearbyCitiesAvailabilitySource;
import com.hotellook.search.RoomTypesSource;
import com.hotellook.search.SearchEngine;
import com.hotellook.search.SearchKeeper;
import com.hotellook.statistics.MixPanelEventsKeeper;
import com.hotellook.statistics.Statistics;
import com.hotellook.ui.activity.BottomNavActivity;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.hotel.api.BookingSource;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.searchresults.CityImageHierarchyFactory;
import com.hotellook.ui.screen.searchresults.adapters.cards.controller.Cards;
import com.hotellook.utils.CommonPreferences;
import com.hotellook.utils.EventBus;
import okhttp3.OkHttpClient;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

public interface BaseHotellookComponent {
    Application app();

    AppVersionRepository appVersionRepository();

    AsyncOffersSource asyncOffersSource();

    BackStackManager backStackManager();

    Badges badges();

    BookingSource bookingSource();

    Cards cards();

    CurrencyRepository currencyRepository();

    DumpCacher dumpCacher();

    DumpSource dumpSource();

    EventBus eventBus();

    ExchangeRateSource exchangeRateSource();

    FavoritesRepository favoritesRepository();

    Filters filters();

    ABTesting getABTesting();

    CityImageHierarchyFactory getCityImageHierarchyFactory();

    CommonPreferences getCommonPreferences();

    CurrencyConverter getCurrencyConverter();

    DatabaseHelper getDatabaseHelper();

    HotellookImageUrlProvider getHotelImageUrlProvider();

    HotellookClient getHotellookClient();

    HotellookService getHotellookService();

    SearchDestinationFavoritesCache getLocationFavoritesCache();

    MinPricesLoader getMinPricesLoader();

    MixPanelEventsKeeper getMixPanelEventsKeeper();

    PoiHistoryDBHelper getPoiHistoryDBHelper();

    RateDialogConditionsTracker getRateDialogConditionsTracker();

    RequestFlagsHelperTracker getRequestFlagsHelperTracker();

    SearchFormPreferences getSearchFormPreferences();

    SearchTracker getSearchTracker();

    ShortUrlService getShortUrlService();

    Statistics getStatistics();

    StreetViewCheckLoader getStreetViewLoader();

    @Host
    String host();

    HotelDataSource hotelDataSource();

    HotelDetailSource hotelDetailSource();

    LocationDumpSource hotelsSource();

    void inject(BottomNavActivity bottomNavActivity);

    void inject(MainActivity mainActivity);

    void inject(BaseFragment baseFragment);

    LastKnownLocationProvider lastKnownLocationProvider();

    LocationIdsSource locationIdsSource();

    ReactiveLocationProvider locationProvider();

    LocationRequestResolver locationRequestResolver();

    LocationsSource locationSource();

    NearbyCitiesAvailabilitySource nearbySearchAvailabilitySource();

    NearestLocationsProvider nearestLocationsProvider();

    NearestLocationsSource nearestLocationsSource();

    OkHttpClient okHttpClient();

    PersistentFilters persistentFilters();

    RoomTypesSource roomTypesSource();

    SearchEngine searchEngine();

    SearchKeeper searchKeeper();

    SnapshotKeeper snapshotKeeper();

    @Token
    String token();
}
