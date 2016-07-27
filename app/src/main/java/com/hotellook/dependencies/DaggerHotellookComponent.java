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
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

public final class DaggerHotellookComponent implements HotellookComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Provider<CurrencyConverter> currencyConverterProvider;
    private Provider<NearbyCitiesAvailabilitySource> nearbySearchAvailabilitySourceProvider;
    private Provider<ABTesting> provideABTestingProvider;
    private Provider<Interceptor> provideApiInterceptorProvider;
    private Provider<Application> provideAppProvider;
    private Provider<AppVersionRepository> provideAppVersionRepositoryProvider;
    private Provider<AsyncOffersSource> provideAsyncOffersSourceProvider;
    private Provider<BackStackManager> provideBackStackManagerProvider;
    private Provider<Badges> provideBadgesProvider;
    private Provider<Cards> provideCardsProvider;
    private Provider<CityImageHierarchyFactory> provideCityImageHierarchyFactoryProvider;
    private Provider<CommonPreferences> provideCommonPreferencesProvider;
    private Provider<CurrencyRepository> provideCurrencyRepositoryProvider;
    private Provider<DatabaseHelper> provideDbHelperProvider;
    private Provider<BookingSource> provideDeeplinkSourceProvider;
    private Provider<DumpCacher> provideDumpCacherProvider;
    private Provider<DumpSource> provideDumpProvider;
    private Provider<EventBus> provideEventBusProvider;
    private Provider<ExchangeRateSource> provideExchangeRateSourceProvider;
    private Provider<FavoritesRepository> provideFavoritesRepositoryProvider;
    private Provider<Filters> provideFiltersProvider;
    private Provider<PersistentFilters> provideGlobalFiltersProvider;
    private Provider<String> provideHostProvider;
    private Provider<HotelDataSource> provideHotelDataSourceProvider;
    private Provider<HotelDetailSource> provideHotelDetailSourceProvider;
    private Provider<HotellookClient> provideHotellookClientProvider;
    private Provider<HotellookImageUrlProvider> provideHotellookImageUrlProvider;
    private Provider<HotellookService> provideHotellookServiceProvider;
    private Provider<LocationDumpSource> provideHotelsSourceProvider;
    private Provider<ShortUrlService> provideInternalHotellookServiceProvider;
    private Provider<LastKnownLocationProvider> provideLastKnownLocationProvider;
    private Provider<SearchDestinationFavoritesCache> provideLocationFavoritesCacheProvider;
    private Provider<LocationIdsSource> provideLocationIdsSourceProvider;
    private Provider<ReactiveLocationProvider> provideLocationProvider;
    private Provider<LocationRequestResolver> provideLocationRequestResolverProvider;
    private Provider<LocationsSource> provideLocationsSourceProvider;
    private Provider<MinPricesLoader> provideMinPricesLoaderProvider;
    private Provider<MixPanelEventsKeeper> provideMixPanelEventsKeeperProvider;
    private Provider<NearestLocationsProvider> provideNearestLocationsProvider;
    private Provider<NearestLocationsSource> provideNearestLocationsSourceProvider;
    private Provider<OkHttpClient> provideOkHttpClientProvider;
    private Provider<PoiHistoryDBHelper> providePoiHistoryHelperProvider;
    private Provider<RateDialogConditionsTracker> provideRateDialogConditionsTrackerProvider;
    private Provider<RequestFlagsHelperTracker> provideRequestFlagsHelperTrackerProvider;
    private Provider<RoomTypesSource> provideRoomTypesSourceProvider;
    private Provider<SearchEngine> provideSearchEngineProvider;
    private Provider<SearchFormPreferences> provideSearchFormPreferencesProvider;
    private Provider<SearchKeeper> provideSearchProvider;
    private Provider<SearchTracker> provideSearchTrackerProvider;
    private Provider<SnapshotKeeper> provideSnapshotKeeperProvider;
    private Provider<Statistics> provideStatisticsProvider;
    private Provider<StreetViewCheckLoader> provideStreetViewLoaderProvider;
    private Provider<String> provideTokenProvider;

    public static final class Builder {
        private AppModule appModule;
        private DataSourceModule dataSourceModule;
        private DatabaseModule databaseModule;
        private HotellookApiModule hotellookApiModule;
        private ImageModule imageModule;
        private LoadersModule loadersModule;
        private LocationModule locationModule;
        private SearchModule searchModule;
        private StatisticsModule statisticsModule;
        private StorageModule storageModule;

        private Builder() {
        }

        public HotellookComponent build() {
            if (this.appModule == null) {
                throw new IllegalStateException(AppModule.class.getCanonicalName() + " must be set");
            }
            if (this.storageModule == null) {
                this.storageModule = new StorageModule();
            }
            if (this.imageModule == null) {
                this.imageModule = new ImageModule();
            }
            if (this.hotellookApiModule == null) {
                this.hotellookApiModule = new HotellookApiModule();
            }
            if (this.loadersModule == null) {
                this.loadersModule = new LoadersModule();
            }
            if (this.databaseModule == null) {
                this.databaseModule = new DatabaseModule();
            }
            if (this.statisticsModule == null) {
                this.statisticsModule = new StatisticsModule();
            }
            if (this.dataSourceModule == null) {
                this.dataSourceModule = new DataSourceModule();
            }
            if (this.searchModule == null) {
                this.searchModule = new SearchModule();
            }
            if (this.locationModule == null) {
                this.locationModule = new LocationModule();
            }
            return new DaggerHotellookComponent();
        }

        public Builder appModule(AppModule appModule) {
            this.appModule = (AppModule) Preconditions.checkNotNull(appModule);
            return this;
        }

        public Builder hotellookApiModule(HotellookApiModule hotellookApiModule) {
            this.hotellookApiModule = (HotellookApiModule) Preconditions.checkNotNull(hotellookApiModule);
            return this;
        }

        public Builder loadersModule(LoadersModule loadersModule) {
            this.loadersModule = (LoadersModule) Preconditions.checkNotNull(loadersModule);
            return this;
        }

        public Builder storageModule(StorageModule storageModule) {
            this.storageModule = (StorageModule) Preconditions.checkNotNull(storageModule);
            return this;
        }

        public Builder imageModule(ImageModule imageModule) {
            this.imageModule = (ImageModule) Preconditions.checkNotNull(imageModule);
            return this;
        }

        public Builder databaseModule(DatabaseModule databaseModule) {
            this.databaseModule = (DatabaseModule) Preconditions.checkNotNull(databaseModule);
            return this;
        }

        public Builder statisticsModule(StatisticsModule statisticsModule) {
            this.statisticsModule = (StatisticsModule) Preconditions.checkNotNull(statisticsModule);
            return this;
        }

        public Builder dataSourceModule(DataSourceModule dataSourceModule) {
            this.dataSourceModule = (DataSourceModule) Preconditions.checkNotNull(dataSourceModule);
            return this;
        }

        public Builder searchModule(SearchModule searchModule) {
            this.searchModule = (SearchModule) Preconditions.checkNotNull(searchModule);
            return this;
        }

        public Builder locationModule(LocationModule locationModule) {
            this.locationModule = (LocationModule) Preconditions.checkNotNull(locationModule);
            return this;
        }
    }

    static {
        $assertionsDisabled = !DaggerHotellookComponent.class.desiredAssertionStatus();
    }

    private DaggerHotellookComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.provideAppProvider = ScopedProvider.create(AppModule_ProvideAppFactory.create(builder.appModule));
        this.provideEventBusProvider = ScopedProvider.create(AppModule_ProvideEventBusFactory.create(builder.appModule));
        this.provideSnapshotKeeperProvider = ScopedProvider.create(AppModule_ProvideSnapshotKeeperFactory.create(builder.appModule));
        this.provideBackStackManagerProvider = ScopedProvider.create(AppModule_ProvideBackStackManagerFactory.create(builder.appModule));
        this.provideSearchFormPreferencesProvider = StorageModule_ProvideSearchFormPreferencesFactory.create(builder.storageModule, this.provideAppProvider);
        this.provideCommonPreferencesProvider = ScopedProvider.create(StorageModule_ProvideCommonPreferencesFactory.create(builder.storageModule, this.provideAppProvider));
        this.provideCityImageHierarchyFactoryProvider = ScopedProvider.create(ImageModule_ProvideCityImageHierarchyFactoryFactory.create(builder.imageModule, this.provideAppProvider));
        this.provideHotellookClientProvider = ScopedProvider.create(HotellookApiModule_ProvideHotellookClientFactory.create(builder.hotellookApiModule, this.provideAppProvider, this.provideEventBusProvider));
        this.provideHotellookServiceProvider = ScopedProvider.create(HotellookApiModule_ProvideHotellookServiceFactory.create(builder.hotellookApiModule, this.provideHotellookClientProvider));
        this.provideCurrencyRepositoryProvider = StorageModule_ProvideCurrencyRepositoryFactory.create(builder.storageModule, this.provideCommonPreferencesProvider);
        this.provideMinPricesLoaderProvider = ScopedProvider.create(LoadersModule_ProvideMinPricesLoaderFactory.create(builder.loadersModule, this.provideHotellookServiceProvider, this.provideCurrencyRepositoryProvider));
        this.provideApiInterceptorProvider = ScopedProvider.create(HotellookApiModule_ProvideApiInterceptorFactory.create(builder.hotellookApiModule, this.provideHotellookClientProvider));
        this.provideOkHttpClientProvider = ScopedProvider.create(HotellookApiModule_ProvideOkHttpClientFactory.create(builder.hotellookApiModule, this.provideApiInterceptorProvider));
        this.provideStreetViewLoaderProvider = LoadersModule_ProvideStreetViewLoaderFactory.create(builder.loadersModule, this.provideOkHttpClientProvider);
        this.provideSearchTrackerProvider = ScopedProvider.create(StorageModule_ProvideSearchTrackerFactory.create(builder.storageModule, this.provideAppProvider));
        this.provideRateDialogConditionsTrackerProvider = ScopedProvider.create(StorageModule_ProvideRateDialogConditionsTrackerFactory.create(builder.storageModule, this.provideCommonPreferencesProvider));
        this.provideHotellookImageUrlProvider = ScopedProvider.create(HotellookApiModule_ProvideHotellookImageUrlProviderFactory.create(builder.hotellookApiModule));
        this.provideDbHelperProvider = ScopedProvider.create(DatabaseModule_ProvideDbHelperFactory.create(builder.databaseModule, this.provideAppProvider));
        this.provideRequestFlagsHelperTrackerProvider = ScopedProvider.create(StorageModule_ProvideRequestFlagsHelperTrackerFactory.create(builder.storageModule));
        this.provideInternalHotellookServiceProvider = ScopedProvider.create(HotellookApiModule_ProvideInternalHotellookServiceFactory.create(builder.hotellookApiModule));
        this.provideFavoritesRepositoryProvider = DatabaseModule_ProvideFavoritesRepositoryFactory.create(builder.databaseModule, this.provideDbHelperProvider);
        this.providePoiHistoryHelperProvider = DatabaseModule_ProvidePoiHistoryHelperFactory.create(builder.databaseModule, this.provideDbHelperProvider);
        this.provideLocationFavoritesCacheProvider = ScopedProvider.create(DatabaseModule_ProvideLocationFavoritesCacheFactory.create(builder.databaseModule, this.provideFavoritesRepositoryProvider));
        this.provideMixPanelEventsKeeperProvider = ScopedProvider.create(StatisticsModule_ProvideMixPanelEventsKeeperFactory.create(builder.statisticsModule));
        this.provideStatisticsProvider = ScopedProvider.create(StatisticsModule_ProvideStatisticsFactory.create(builder.statisticsModule, this.provideAppProvider, this.provideHotellookClientProvider, this.provideMixPanelEventsKeeperProvider));
        this.provideABTestingProvider = ScopedProvider.create(StatisticsModule_ProvideABTestingFactory.create(builder.statisticsModule));
        this.currencyConverterProvider = ScopedProvider.create(StorageModule_CurrencyConverterFactory.create(builder.storageModule));
        this.provideLocationsSourceProvider = DataSourceModule_ProvideLocationsSourceFactory.create(builder.dataSourceModule, this.provideHotellookServiceProvider);
        this.provideHotelsSourceProvider = DataSourceModule_ProvideHotelsSourceFactory.create(builder.dataSourceModule, this.provideHotellookServiceProvider);
        this.provideDumpProvider = DataSourceModule_ProvideDumpFactory.create(builder.dataSourceModule, this.provideLocationsSourceProvider, this.provideHotelsSourceProvider);
        this.provideDumpCacherProvider = ScopedProvider.create(DataSourceModule_ProvideDumpCacherFactory.create(builder.dataSourceModule, this.provideAppProvider));
        this.provideHotelDetailSourceProvider = DataSourceModule_ProvideHotelDetailSourceFactory.create(builder.dataSourceModule, this.provideHotellookServiceProvider);
        this.provideHotelDataSourceProvider = DataSourceModule_ProvideHotelDataSourceFactory.create(builder.dataSourceModule, this.provideAppProvider, this.provideEventBusProvider);
        this.provideLocationIdsSourceProvider = DataSourceModule_ProvideLocationIdsSourceFactory.create(builder.dataSourceModule, this.provideHotellookServiceProvider);
        this.provideRoomTypesSourceProvider = DataSourceModule_ProvideRoomTypesSourceFactory.create(builder.dataSourceModule, this.provideHotellookServiceProvider);
        this.provideExchangeRateSourceProvider = DataSourceModule_ProvideExchangeRateSourceFactory.create(builder.dataSourceModule, this.provideHotellookServiceProvider);
        this.provideSearchEngineProvider = SearchModule_ProvideSearchEngineFactory.create(builder.searchModule, this.provideAppProvider, this.provideDumpCacherProvider, this.provideEventBusProvider, this.provideFavoritesRepositoryProvider);
        this.provideSearchProvider = ScopedProvider.create(SearchModule_ProvideSearchFactory.create(builder.searchModule));
        this.provideGlobalFiltersProvider = SearchModule_ProvideGlobalFiltersFactory.create(builder.searchModule, this.provideAppProvider);
        this.provideFiltersProvider = SearchModule_ProvideFiltersFactory.create(builder.searchModule, this.provideGlobalFiltersProvider);
        this.provideBadgesProvider = SearchModule_ProvideBadgesFactory.create(builder.searchModule, this.provideAppProvider);
        this.provideCardsProvider = SearchModule_ProvideCardsFactory.create(builder.searchModule, this.provideAppProvider, this.provideCommonPreferencesProvider, this.provideGlobalFiltersProvider);
        this.provideLocationProvider = ScopedProvider.create(LocationModule_ProvideLocationProviderFactory.create(builder.locationModule, this.provideAppProvider));
        this.provideLocationRequestResolverProvider = ScopedProvider.create(LocationModule_ProvideLocationRequestResolverFactory.create(builder.locationModule, this.provideAppProvider, this.provideLocationProvider));
        this.provideLastKnownLocationProvider = ScopedProvider.create(LocationModule_ProvideLastKnownLocationProviderFactory.create(builder.locationModule, this.provideAppProvider, this.provideLocationRequestResolverProvider, this.provideLocationProvider));
        this.provideNearestLocationsSourceProvider = LocationModule_ProvideNearestLocationsSourceFactory.create(builder.locationModule, this.provideHotellookServiceProvider);
        this.provideNearestLocationsProvider = ScopedProvider.create(LocationModule_ProvideNearestLocationsProviderFactory.create(builder.locationModule, this.provideLastKnownLocationProvider, this.provideNearestLocationsSourceProvider, this.provideEventBusProvider));
        this.provideTokenProvider = ScopedProvider.create(HotellookApiModule_ProvideTokenFactory.create(builder.hotellookApiModule, this.provideHotellookClientProvider));
        this.provideDeeplinkSourceProvider = DataSourceModule_ProvideDeeplinkSourceFactory.create(builder.dataSourceModule, this.provideHotellookServiceProvider, this.provideTokenProvider, this.provideAppProvider);
        this.provideHostProvider = ScopedProvider.create(HotellookApiModule_ProvideHostFactory.create(builder.hotellookApiModule, this.provideHotellookClientProvider));
        this.provideAsyncOffersSourceProvider = DataSourceModule_ProvideAsyncOffersSourceFactory.create(builder.dataSourceModule, this.provideHotellookServiceProvider, this.provideTokenProvider);
        this.provideAppVersionRepositoryProvider = StorageModule_ProvideAppVersionRepositoryFactory.create(builder.storageModule, this.provideAppProvider);
        this.nearbySearchAvailabilitySourceProvider = DataSourceModule_NearbySearchAvailabilitySourceFactory.create(builder.dataSourceModule, this.provideHotellookServiceProvider);
    }

    public void inject(BaseFragment fragment) {
        MembersInjectors.noOp().injectMembers(fragment);
    }

    public void inject(MainActivity activity) {
        MembersInjectors.noOp().injectMembers(activity);
    }

    public void inject(BottomNavActivity bottomNavActivity) {
        MembersInjectors.noOp().injectMembers(bottomNavActivity);
    }

    public Application app() {
        return (Application) this.provideAppProvider.get();
    }

    public EventBus eventBus() {
        return (EventBus) this.provideEventBusProvider.get();
    }

    public SnapshotKeeper snapshotKeeper() {
        return (SnapshotKeeper) this.provideSnapshotKeeperProvider.get();
    }

    public BackStackManager backStackManager() {
        return (BackStackManager) this.provideBackStackManagerProvider.get();
    }

    public SearchFormPreferences getSearchFormPreferences() {
        return (SearchFormPreferences) this.provideSearchFormPreferencesProvider.get();
    }

    public CommonPreferences getCommonPreferences() {
        return (CommonPreferences) this.provideCommonPreferencesProvider.get();
    }

    public CityImageHierarchyFactory getCityImageHierarchyFactory() {
        return (CityImageHierarchyFactory) this.provideCityImageHierarchyFactoryProvider.get();
    }

    public HotellookService getHotellookService() {
        return (HotellookService) this.provideHotellookServiceProvider.get();
    }

    public CurrencyRepository currencyRepository() {
        return (CurrencyRepository) this.provideCurrencyRepositoryProvider.get();
    }

    public MinPricesLoader getMinPricesLoader() {
        return (MinPricesLoader) this.provideMinPricesLoaderProvider.get();
    }

    public StreetViewCheckLoader getStreetViewLoader() {
        return (StreetViewCheckLoader) this.provideStreetViewLoaderProvider.get();
    }

    public HotellookClient getHotellookClient() {
        return (HotellookClient) this.provideHotellookClientProvider.get();
    }

    public SearchTracker getSearchTracker() {
        return (SearchTracker) this.provideSearchTrackerProvider.get();
    }

    public RateDialogConditionsTracker getRateDialogConditionsTracker() {
        return (RateDialogConditionsTracker) this.provideRateDialogConditionsTrackerProvider.get();
    }

    public HotellookImageUrlProvider getHotelImageUrlProvider() {
        return (HotellookImageUrlProvider) this.provideHotellookImageUrlProvider.get();
    }

    public DatabaseHelper getDatabaseHelper() {
        return (DatabaseHelper) this.provideDbHelperProvider.get();
    }

    public RequestFlagsHelperTracker getRequestFlagsHelperTracker() {
        return (RequestFlagsHelperTracker) this.provideRequestFlagsHelperTrackerProvider.get();
    }

    public ShortUrlService getShortUrlService() {
        return (ShortUrlService) this.provideInternalHotellookServiceProvider.get();
    }

    public FavoritesRepository favoritesRepository() {
        return (FavoritesRepository) this.provideFavoritesRepositoryProvider.get();
    }

    public PoiHistoryDBHelper getPoiHistoryDBHelper() {
        return (PoiHistoryDBHelper) this.providePoiHistoryHelperProvider.get();
    }

    public SearchDestinationFavoritesCache getLocationFavoritesCache() {
        return (SearchDestinationFavoritesCache) this.provideLocationFavoritesCacheProvider.get();
    }

    public Statistics getStatistics() {
        return (Statistics) this.provideStatisticsProvider.get();
    }

    public ABTesting getABTesting() {
        return (ABTesting) this.provideABTestingProvider.get();
    }

    public MixPanelEventsKeeper getMixPanelEventsKeeper() {
        return (MixPanelEventsKeeper) this.provideMixPanelEventsKeeperProvider.get();
    }

    public CurrencyConverter getCurrencyConverter() {
        return (CurrencyConverter) this.currencyConverterProvider.get();
    }

    public LocationsSource locationSource() {
        return (LocationsSource) this.provideLocationsSourceProvider.get();
    }

    public LocationDumpSource hotelsSource() {
        return (LocationDumpSource) this.provideHotelsSourceProvider.get();
    }

    public DumpSource dumpSource() {
        return (DumpSource) this.provideDumpProvider.get();
    }

    public DumpCacher dumpCacher() {
        return (DumpCacher) this.provideDumpCacherProvider.get();
    }

    public HotelDetailSource hotelDetailSource() {
        return (HotelDetailSource) this.provideHotelDetailSourceProvider.get();
    }

    public HotelDataSource hotelDataSource() {
        return (HotelDataSource) this.provideHotelDataSourceProvider.get();
    }

    public LocationIdsSource locationIdsSource() {
        return (LocationIdsSource) this.provideLocationIdsSourceProvider.get();
    }

    public RoomTypesSource roomTypesSource() {
        return (RoomTypesSource) this.provideRoomTypesSourceProvider.get();
    }

    public ExchangeRateSource exchangeRateSource() {
        return (ExchangeRateSource) this.provideExchangeRateSourceProvider.get();
    }

    public SearchEngine searchEngine() {
        return (SearchEngine) this.provideSearchEngineProvider.get();
    }

    public SearchKeeper searchKeeper() {
        return (SearchKeeper) this.provideSearchProvider.get();
    }

    public Filters filters() {
        return (Filters) this.provideFiltersProvider.get();
    }

    public PersistentFilters persistentFilters() {
        return (PersistentFilters) this.provideGlobalFiltersProvider.get();
    }

    public Badges badges() {
        return (Badges) this.provideBadgesProvider.get();
    }

    public Cards cards() {
        return (Cards) this.provideCardsProvider.get();
    }

    public LastKnownLocationProvider lastKnownLocationProvider() {
        return (LastKnownLocationProvider) this.provideLastKnownLocationProvider.get();
    }

    public NearestLocationsProvider nearestLocationsProvider() {
        return (NearestLocationsProvider) this.provideNearestLocationsProvider.get();
    }

    public NearestLocationsSource nearestLocationsSource() {
        return (NearestLocationsSource) this.provideNearestLocationsSourceProvider.get();
    }

    public BookingSource bookingSource() {
        return (BookingSource) this.provideDeeplinkSourceProvider.get();
    }

    public String token() {
        return (String) this.provideTokenProvider.get();
    }

    public String host() {
        return (String) this.provideHostProvider.get();
    }

    public OkHttpClient okHttpClient() {
        return (OkHttpClient) this.provideOkHttpClientProvider.get();
    }

    public LocationRequestResolver locationRequestResolver() {
        return (LocationRequestResolver) this.provideLocationRequestResolverProvider.get();
    }

    public ReactiveLocationProvider locationProvider() {
        return (ReactiveLocationProvider) this.provideLocationProvider.get();
    }

    public AsyncOffersSource asyncOffersSource() {
        return (AsyncOffersSource) this.provideAsyncOffersSourceProvider.get();
    }

    public AppVersionRepository appVersionRepository() {
        return (AppVersionRepository) this.provideAppVersionRepositoryProvider.get();
    }

    public NearbyCitiesAvailabilitySource nearbySearchAvailabilitySource() {
        return (NearbyCitiesAvailabilitySource) this.nearbySearchAvailabilitySourceProvider.get();
    }
}
