package com.hotellook.statistics;

import android.app.Application;
import android.content.Context;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AttributionIDNotReady;
import com.facebook.FacebookSdk;
import com.flurry.android.FlurryAgent;
import com.hotellook.BuildConfig;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.HotellookClient;
import com.hotellook.events.AppLaunchedEvent;
import com.hotellook.events.BookPredictedEvent;
import com.hotellook.events.CurrencyChangedEvent;
import com.hotellook.events.ENGatesCardClosedEvent;
import com.hotellook.events.EnGatesAllowedPrefUpdatedEvent;
import com.hotellook.events.FavoritesOpenEvent;
import com.hotellook.events.FiltaerDistanceCardClosedEvent;
import com.hotellook.events.FiltersApplyEvent;
import com.hotellook.events.FiltersSortingChangedEvent;
import com.hotellook.events.FrameCloseEvent;
import com.hotellook.events.GalleryOpenedEvent;
import com.hotellook.events.GuideExitEvent;
import com.hotellook.events.HotelAddedToFavoritesEvent;
import com.hotellook.events.HotelDataLoadedEvent;
import com.hotellook.events.HotelFragmentCloseEvent;
import com.hotellook.events.HotelFragmentCreatedEvent;
import com.hotellook.events.HotelMapOpenedEvent;
import com.hotellook.events.HotelRemovedFromFavoritesEvent;
import com.hotellook.events.HotelShareEvent;
import com.hotellook.events.HotelTabSelectedEvent;
import com.hotellook.events.HotellookClientErrorEvent;
import com.hotellook.events.ImageShowedEvent;
import com.hotellook.events.InfoTapRateUsEvent;
import com.hotellook.events.InfoTapToLicenceEvent;
import com.hotellook.events.InfoTapToSendFeedback;
import com.hotellook.events.InfoTapToSocialNetworksEvent;
import com.hotellook.events.InformationScreenOpenEvent;
import com.hotellook.events.LocationPermissionDeniedEvent;
import com.hotellook.events.LocationPermissionGrantedEvent;
import com.hotellook.events.MoreThan30DaysBookingEvent;
import com.hotellook.events.NewLaunchParamsEvent;
import com.hotellook.events.NoCitiesOrHotelsEvent;
import com.hotellook.events.OnSocialNetworkClickEvent;
import com.hotellook.events.OutOfDateResultsEvent;
import com.hotellook.events.PurchaseEvent;
import com.hotellook.events.RateUsEvent;
import com.hotellook.events.RateUsOpenedEvent;
import com.hotellook.events.ResultsSwitchedToMapEvent;
import com.hotellook.events.SearchCanceledEvent;
import com.hotellook.events.SearchFailEvent;
import com.hotellook.events.SearchFinishEvent;
import com.hotellook.events.SearchFormOpenedEvent;
import com.hotellook.events.SearchNoResultsEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.events.ToughFiltersEvent;
import com.hotellook.statistics.Constants.Actions;
import com.hotellook.statistics.Constants.FlurryEvents;
import com.hotellook.statistics.Constants.MixPanelEvents;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.mixpanel.AppBuildParams;
import com.hotellook.statistics.mixpanel.ConnectionErrorParams;
import com.hotellook.statistics.mixpanel.CountOfFavoritesHotelsToSuperParamsTask;
import com.hotellook.statistics.mixpanel.DeviceParams;
import com.hotellook.statistics.mixpanel.FavoritesParams;
import com.hotellook.statistics.mixpanel.FiltersApplyParams;
import com.hotellook.statistics.mixpanel.FrameParams;
import com.hotellook.statistics.mixpanel.HotelClosePrams;
import com.hotellook.statistics.mixpanel.HotelPredictedParams;
import com.hotellook.statistics.mixpanel.HotelScreenParams;
import com.hotellook.statistics.mixpanel.InstallParams;
import com.hotellook.statistics.mixpanel.MixPanelParamsBuilder;
import com.hotellook.statistics.mixpanel.MixPanelSuperParamsBuilder;
import com.hotellook.statistics.mixpanel.MixpanelErrorParams;
import com.hotellook.statistics.mixpanel.ParseLaunchParamsUtils;
import com.hotellook.statistics.mixpanel.RateUsCompletedParams;
import com.hotellook.statistics.mixpanel.ResultsScreenParams;
import com.hotellook.statistics.mixpanel.SearchCancelParams;
import com.hotellook.statistics.mixpanel.SearchFormOpenParams;
import com.hotellook.statistics.mixpanel.SocialNetworkClickParams;
import com.hotellook.statistics.mixpanel.SortingChangedParams;
import com.hotellook.statistics.mixpanel.StartSearchParams;
import com.hotellook.statistics.mixpanel.SuperParams;
import com.hotellook.statistics.task.AppLaunchParams;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.otto.Subscribe;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import pl.charmas.android.reactivelocation.C1822R;
import retrofit.RetrofitError;
import retrofit.RetrofitError.Kind;

public class HLStatistics implements Statistics {
    private static final String APPSFLYER_APP_KEY = "rcNMEiSTcPrgFybB54YWU6";
    private static final String FACEBOOK_APP_KEY = "643411802345725";
    private static final String FLURRY_APP_KEY = "RGX6RP4BKGPMZ7NTYV8C";
    private static final String GCM_PROJECT_NUMBER = "458649562344";
    private static final String MIXPANEL_TOKEN = "94861cf5cf6510bc1a78dd8eefac5bc1";
    private Context context;
    private final MixPanelEventsKeeper eventsKeeper;
    private final Executor executor;
    private MixpanelAPI mixpanelApi;
    private StatisticPrefs prefs;
    private long startSearchTime;
    private String token;

    /* renamed from: com.hotellook.statistics.HLStatistics.1 */
    class C12031 extends AppsFlyerConversionListenerAdapter {
        C12031() {
        }

        public void onInstallConversionDataLoaded(Map<String, String> map) {
            HLStatistics.this.onConversionDataReceived(map);
        }
    }

    /* renamed from: com.hotellook.statistics.HLStatistics.2 */
    static /* synthetic */ class C12042 {
        static final /* synthetic */ int[] $SwitchMap$retrofit$RetrofitError$Kind;

        static {
            $SwitchMap$retrofit$RetrofitError$Kind = new int[Kind.values().length];
            try {
                $SwitchMap$retrofit$RetrofitError$Kind[Kind.NETWORK.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$retrofit$RetrofitError$Kind[Kind.CONVERSION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$retrofit$RetrofitError$Kind[Kind.HTTP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$retrofit$RetrofitError$Kind[Kind.UNEXPECTED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public HLStatistics(Application application, HotellookClient hotellookClient, MixPanelEventsKeeper eventsKeeper) {
        this.executor = Executors.newSingleThreadExecutor();
        this.context = application;
        this.eventsKeeper = eventsKeeper;
        this.prefs = new StatisticPrefs(this.context);
        this.token = hotellookClient.getToken();
        FlurryAgent.init(this.context, FLURRY_APP_KEY);
        FlurryAgent.setLogEnabled(false);
        AppsFlyerLib.getInstance().setGCMProjectNumber(GCM_PROJECT_NUMBER);
        AppsFlyerLib.getInstance().startTracking(application, APPSFLYER_APP_KEY);
        AppsFlyerLib.getInstance().setCustomerUserId(this.token);
        this.mixpanelApi = MixpanelAPI.getInstance(this.context, MIXPANEL_TOKEN);
        FacebookSdk.sdkInitialize(this.context);
        FacebookSdk.setApplicationId(FACEBOOK_APP_KEY);
        this.mixpanelApi.identify(this.token);
        this.mixpanelApi.registerSuperPropertiesMap(new DeviceParams(this.context).buildParams());
        this.mixpanelApi.registerSuperPropertiesMap(new SingleParams(MixPanelParams.TOKEN, this.token).buildParams());
        this.mixpanelApi.registerSuperPropertiesMap(new AppBuildParams().buildParams());
        if (!BuildConfig.GOOGLE_PLAY_BUILD.booleanValue()) {
            this.mixpanelApi.registerSuperPropertiesMap(new SingleParams(MixPanelParams.INSTALL_MARKER, BuildConfig.STORE_MARKER).buildParams());
        } else if (!this.prefs.isInstallTracked()) {
            Map<String, String> conversionData = getConversionData(this.context);
            if (conversionData != null) {
                onConversionDataReceived(conversionData);
            } else {
                AppsFlyerLib.getInstance().registerConversionListener(this.context, new C12031());
            }
        }
    }

    private Map<String, String> getConversionData(Context context) {
        try {
            return AppsFlyerLib.getInstance().getConversionData(context);
        } catch (AttributionIDNotReady attributionIDNotReady) {
            attributionIDNotReady.printStackTrace();
            return null;
        }
    }

    private void onConversionDataReceived(Map<String, String> map) {
        InstallParams installParams = new InstallParams(map);
        String deepLinkParams = (String) map.get(MixPanelParams.AF_SUB5);
        if (deepLinkParams != null) {
            try {
                HotellookApplication.eventBus().post(new NewLaunchParamsEvent(ParseLaunchParamsUtils.fromApsflyer(deepLinkParams)));
            } catch (ParseException e) {
            }
        }
        try {
            this.mixpanelApi.registerSuperPropertiesMap(installParams.buildSuperParams());
        } catch (Exception e2) {
            this.mixpanelApi.trackMap(MixPanelEvents.ERROR_MIXPANEL, new MixpanelErrorParams(MixPanelEvents.INSTALL, e2).buildParams());
        }
        trackMixPanel(MixPanelEvents.INSTALL);
        this.prefs.setInstallTracked();
    }

    @Subscribe
    public void onAppLaunched(AppLaunchedEvent event) {
        SuperParams superParams = new SuperParams(this.token);
        this.mixpanelApi.registerSuperPropertiesMap(superParams.buildParams());
        if (this.prefs.isNewLaunch()) {
            this.prefs.setLaunchTimestamp();
            if (this.prefs.isFirstLaunch()) {
                this.prefs.setFirstLaunchTracked();
                trackMixPanel(MixPanelEvents.FIRST_LAUNCH);
                this.mixpanelApi.registerSuperPropertiesMap(superParams.buildSuperParams());
            }
            AppLaunchParams appLaunchParams = new AppLaunchParams(this.context, event.launchSource.toString());
            this.eventsKeeper.setLaunchReferrer(event.launchSource);
            FlurryAgent.logEvent(MixPanelEvents.LAUNCH);
            trackMixPanel(MixPanelEvents.LAUNCH, appLaunchParams);
        }
    }

    @Subscribe
    public void onSearchFormOpened(SearchFormOpenedEvent event) {
        this.mixpanelApi.registerSuperPropertiesMap(new SuperParams(this.token).buildParams());
        trackMixPanel(MixPanelEvents.SEARCH_FORM, new SearchFormOpenParams(event.source, this.context));
    }

    @Subscribe
    public void onImageShowed(ImageShowedEvent event) {
        this.eventsKeeper.setImageShowed(event.hotelId, event.position);
    }

    @Subscribe
    public void onGuideExit(GuideExitEvent event) {
        trackMixPanel(MixPanelEvents.TUTORIAL, event.appGuideParams);
    }

    @Subscribe
    public void onStartSearch(SearchStartEvent event) {
        this.prefs.incSearchCount();
        this.startSearchTime = System.currentTimeMillis();
        trackAppsflyer(Actions.SEARCH);
        StartSearchParams params = new StartSearchParams(event.searchParams, event.source, event.imageUrl);
        this.eventsKeeper.setSearchSource(event.source);
        trackMixPanel(MixPanelEvents.SEARCH_ACTION, params);
        FlurryAgent.logEvent(FlurryEvents.SEARCH);
    }

    @Subscribe
    public void onSearchResultsFullyCalculated(SearchFinishEvent event) {
        trackMixPanel(MixPanelEvents.RESULTS_LIST, new ResultsScreenParams(this.context, System.currentTimeMillis() - this.startSearchTime, event.offers(), event.searchData().discounts(), event.searchData().highlights()));
        FlurryAgent.logEvent(FlurryEvents.RESULTS);
    }

    @Subscribe
    public void onSearchCanceled(SearchCanceledEvent event) {
        trackMixPanel(MixPanelEvents.SEARCH_CANCELED, new SearchCancelParams(true, System.currentTimeMillis() - this.startSearchTime));
    }

    @Subscribe
    public void onSearchFailed(SearchFailEvent event) {
        trackMixPanel(MixPanelEvents.SEARCH_CANCELED, new SearchCancelParams(false, System.currentTimeMillis() - this.startSearchTime));
    }

    @Subscribe
    public void onPrefEnGatesAllowedUpdate(EnGatesAllowedPrefUpdatedEvent event) {
        this.mixpanelApi.registerSuperPropertiesMap(new SingleParams(MixPanelParams.ALLOW_EN_GATES, Boolean.valueOf(event.allowed)).buildParams());
    }

    @Subscribe
    public void onFiltersApplied(FiltersApplyEvent event) {
        this.prefs.incFilterCount();
        FiltersApplyParams params = new FiltersApplyParams(this.prefs, event.filters, this.eventsKeeper, event.filterType, event.filterSource);
        if (event.filters.getSortedHotels().isEmpty()) {
            onToughFilters(null);
        }
        this.eventsKeeper.setActionMet(-1, 15);
        trackMixPanel(MixPanelEvents.RESULTS_FILTERED, params);
    }

    @Subscribe
    public void onHotelMapOpened(HotelMapOpenedEvent event) {
        this.eventsKeeper.setActionMet(event.hotelId, 7);
    }

    @Subscribe
    public void onHotelTabSelected(HotelTabSelectedEvent event) {
        switch (event.tabId) {
            case C1822R.styleable.MapAttrs_uiZoomControls /*11*/:
                this.eventsKeeper.setActionMet(event.hotelId, 11);
            case C1822R.styleable.MapAttrs_uiZoomGestures /*12*/:
                this.eventsKeeper.setActionMet(event.hotelId, 12);
            case C1822R.styleable.MapAttrs_useViewLifecycle /*13*/:
                this.eventsKeeper.setActionMet(event.hotelId, 13);
            default:
        }
    }

    @Subscribe
    public void onCurrencyChanged(CurrencyChangedEvent event) {
        trackMixPanel(MixPanelEvents.CURRENCY_CHANGED, new SingleMixpanelAndSuperParam(MixPanelParams.CURRENCY, event.currency));
    }

    @Subscribe
    public void onHotelFragmentOpened(HotelFragmentCreatedEvent event) {
        this.eventsKeeper.setCreateHotelFragmentEvent(event.hotelId, event);
        trackOpenHotel(event.hotelId);
    }

    @Subscribe
    public void onHotelDataLoaded(HotelDataLoadedEvent event) {
        long hotelId = event.hotelDetailData.getId();
        this.eventsKeeper.setHotelDataLoadedEvent(hotelId, event);
        trackOpenHotel(hotelId);
    }

    private void trackOpenHotel(long hotelId) {
        HotelFragmentCreatedEvent createEvent = this.eventsKeeper.getHotelFragmentCreateEvent(hotelId);
        HotelDataLoadedEvent loadedEvent = this.eventsKeeper.getHotelDataLoadedEvent(hotelId);
        if (createEvent != null && loadedEvent != null) {
            trackAppsflyer(MixPanelParams.HOTEL);
            HotelScreenParams params = new HotelScreenParams(createEvent.openInfo, loadedEvent.hotelDetailData, loadedEvent.cityInfo, createEvent.badges, createEvent.prices, createEvent.searchParams, this.eventsKeeper, createEvent.discounts, createEvent.highlights);
            this.eventsKeeper.setHotelOpenedSource(hotelId, createEvent.openInfo.source);
            trackMixPanel(MixPanelEvents.HOTEL_OPENED, params);
            FlurryAgent.logEvent(FlurryEvents.VIEW);
            this.eventsKeeper.clearHotelFragmentsEvents();
        }
    }

    @Subscribe
    public void onFavoritesOpen(FavoritesOpenEvent event) {
        trackMixPanel(MixPanelEvents.FAVORITES_OPENED);
    }

    @Subscribe
    public void onBook(PurchaseEvent event) {
        trackAppsflyer(Actions.PURCHASE);
        trackMixPanel(MixPanelEvents.FRAME_OPENED, new FrameParams(event.basicHotelData, event.hotelDetailData, event.filters, event.badges, event.roomData, event.bestPrice, event.searchParams, this.eventsKeeper, event.openSource, event.tabSourceId, event.discounts, event.highlights));
        this.eventsKeeper.setActionMet(event.basicHotelData.id(), 17);
        FlurryAgent.logEvent(FlurryEvents.CLICK);
    }

    @Subscribe
    public void onMapOpened(ResultsSwitchedToMapEvent event) {
        if (!this.prefs.isMapOpenTracked()) {
            this.prefs.setMapTracked();
        }
    }

    @Subscribe
    public void onInformationOpened(InformationScreenOpenEvent event) {
        trackMixPanel(MixPanelEvents.INFORMATION_OPENED);
    }

    @Subscribe
    public void onHotelAddedToFavorites(HotelAddedToFavoritesEvent event) {
        this.executor.execute(new CountOfFavoritesHotelsToSuperParamsTask(this.mixpanelApi));
        trackMixPanel(MixPanelEvents.HOTEL_LIKED, new FavoritesParams(event.source));
    }

    @Subscribe
    public void onHotelRemovedFromFavorites(HotelRemovedFromFavoritesEvent event) {
        this.executor.execute(new CountOfFavoritesHotelsToSuperParamsTask(this.mixpanelApi));
        trackMixPanel(MixPanelEvents.HOTEL_LIKED_UNDO, new FavoritesParams(event.source));
    }

    @Subscribe
    public void onShareHotel(HotelShareEvent event) {
        trackMixPanel(MixPanelParams.HOTEL_SHARED, new SingleMixpanelAndSuperParam(MixPanelParams.SHARES, Integer.valueOf(this.prefs.incSharesAndGet())));
    }

    @Subscribe
    public void onRateUsopened(RateUsOpenedEvent event) {
        trackMixPanel(MixPanelEvents.RATE_US, new SingleParams(MixPanelParams.RATE_US_REFERRER, event.source.literal));
    }

    @Subscribe
    public void onRateUs(RateUsEvent event) {
        if (event.stars == 0) {
            trackMixPanel(MixPanelEvents.RATE_US_CANCELLED, new SingleParams(MixPanelParams.RATE_US_REFERRER, event.source.literal));
        } else {
            trackMixPanel(MixPanelEvents.RATE_US_COMPLETED, new RateUsCompletedParams(event.stars, event.source.literal));
        }
    }

    @Subscribe
    public void onBookPredicted(BookPredictedEvent event) {
        long durationInSeconds = event.onScreenTimeDuration.getDurationInSeconds();
        trackMixPanel(MixPanelEvents.HOTEL_PREDICTED, new HotelPredictedParams(this.prefs.incPredictedDurationsAndGet(), durationInSeconds, event.hotelId, event.roomData, event.searchParams));
        FlurryAgent.logEvent(FlurryEvents.PREDICTION);
    }

    @Subscribe
    public void onFrameClosed(FrameCloseEvent event) {
        trackMixPanel(MixPanelEvents.FRAME_CLOSED, new SingleParams(MixPanelParams.FRAME_DURATION, Long.valueOf(event.onScreenTimeDuration.getDurationInSeconds())));
    }

    @Subscribe
    public void onHotelFragmentClosed(HotelFragmentCloseEvent event) {
        trackMixPanel(MixPanelEvents.HOTEL_CLOSED, new HotelClosePrams(event.hotelId, this.eventsKeeper));
        this.eventsKeeper.clearHotelFragmentsEvents();
    }

    @Subscribe
    public void onGalleryOpened(GalleryOpenedEvent event) {
        this.eventsKeeper.setActionMet(event.hotelId, 0);
    }

    @Subscribe
    public void onTapInfoSocialNetwork(InfoTapToSocialNetworksEvent event) {
        trackMixPanel(MixPanelEvents.SOCIAL_NETWORKS_MENU);
    }

    @Subscribe
    public void onTapInfoLicence(InfoTapToLicenceEvent event) {
        trackMixPanel(MixPanelEvents.LICENCE_OPENED);
    }

    @Subscribe
    public void onTapInfoRateUs(InfoTapRateUsEvent event) {
    }

    @Subscribe
    public void onTapToSendFeedback(InfoTapToSendFeedback event) {
        trackMixPanel(MixPanelEvents.EMAIL_CONTACT);
    }

    @Subscribe
    public void onSocialNetworkShareClick(OnSocialNetworkClickEvent event) {
        trackMixPanel(MixPanelEvents.SOCIAL_NETWORKS_OUT, new SocialNetworkClickParams(event.link));
    }

    @Subscribe
    public void onLocationPermissionGranted(LocationPermissionGrantedEvent event) {
        this.mixpanelApi.registerSuperPropertiesMap(new SingleParams(MixPanelParams.LOCATION_REQUESTED, Boolean.valueOf(true)).buildParams());
        this.mixpanelApi.registerSuperPropertiesMap(new SingleParams(MixPanelParams.LOCATION_SERVICES, Boolean.valueOf(true)).buildParams());
        trackMixPanel(MixPanelParams.LOCATION_REQUESTED);
    }

    @Subscribe
    public void onLocationPermissionDenied(LocationPermissionDeniedEvent event) {
        this.mixpanelApi.registerSuperPropertiesMap(new SingleParams(MixPanelParams.LOCATION_REQUESTED, Boolean.valueOf(true)).buildParams());
        this.mixpanelApi.registerSuperPropertiesMap(new SingleParams(MixPanelParams.LOCATION_SERVICES, Boolean.valueOf(false)).buildParams());
        trackMixPanel(MixPanelParams.LOCATION_REQUESTED);
    }

    @Subscribe
    public void onApiError(HotellookClientErrorEvent event) {
        RetrofitError error = event.error;
        String method = extractMethod(error.getUrl());
        switch (C12042.$SwitchMap$retrofit$RetrofitError$Kind[error.getKind().ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                trackMixPanel(MixPanelEvents.ERROR_CONNECTION, new ConnectionErrorParams(error, method));
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                trackMixPanel(MixPanelEvents.ERROR_PARCING, new SingleParams(MixPanelParams.ERROR_REFERRER, method));
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                trackMixPanel(MixPanelEvents.ERROR_SERVER, new ConnectionErrorParams(error, method));
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                trackMixPanel(MixPanelEvents.ERROR_UNEXPECTED, new ConnectionErrorParams(error, method));
            default:
        }
    }

    private String extractMethod(String url) {
        String method = me.zhanghai.android.materialprogressbar.BuildConfig.FLAVOR;
        try {
            method = new URL(url).getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String[] segments = method.split("/");
        if (segments.length > 2) {
            return segments[2];
        }
        return segments[1];
    }

    @Subscribe
    public void onBookMoreThan30Days(MoreThan30DaysBookingEvent event) {
        trackMixPanel(MixPanelEvents.ERROR_CONTENT, new SingleParams(MixPanelParams.ERROR_TYPE, MixPanelParams.DAYS_30));
    }

    @Subscribe
    public void onNoCitiesOrHotels(NoCitiesOrHotelsEvent event) {
        trackMixPanel(MixPanelEvents.ERROR_CONTENT, new SingleParams(MixPanelParams.ERROR_TYPE, MixPanelParams.NO_CITIES_OR_HOTELS));
    }

    @Subscribe
    public void onOutOfDateResults(OutOfDateResultsEvent event) {
        trackMixPanel(MixPanelEvents.ERROR_CONTENT, new SingleParams(MixPanelParams.ERROR_TYPE, MixPanelParams.RESULTS_OUT_OF_DATE));
    }

    @Subscribe
    public void onSearchNoResults(SearchNoResultsEvent event) {
        trackMixPanel(MixPanelEvents.ERROR_CONTENT, new SingleParams(MixPanelParams.ERROR_TYPE, MixPanelParams.NO_RESULTS));
    }

    @Subscribe
    public void onToughFilters(ToughFiltersEvent event) {
        trackMixPanel(MixPanelEvents.ERROR_CONTENT, new SingleParams(MixPanelParams.ERROR_TYPE, MixPanelParams.TOUGH_FILTERS));
    }

    @Subscribe
    public void onSortingChanged(FiltersSortingChangedEvent event) {
        trackMixPanel(MixPanelEvents.RESULTS_SORTED, new SortingChangedParams(event.sortingItem, this.eventsKeeper));
    }

    @Subscribe
    public void onDistanceCardClosed(FiltaerDistanceCardClosedEvent event) {
        trackMixPanel(MixPanelEvents.FILTER_DISTANCE_CLOSED);
    }

    @Subscribe
    public void onENGatesCardClosed(ENGatesCardClosedEvent event) {
        trackMixPanel(MixPanelEvents.FILTER_ENGLISH_CLOSED);
    }

    private void trackMixPanel(String eventName) {
        this.executor.execute(HLStatistics$$Lambda$1.lambdaFactory$(this, eventName));
    }

    /* synthetic */ void lambda$trackMixPanel$0(String eventName) {
        this.mixpanelApi.track(eventName);
    }

    private void trackMixPanel(String event, MixPanelParamsBuilder params) {
        this.executor.execute(HLStatistics$$Lambda$2.lambdaFactory$(this, event, params));
    }

    /* synthetic */ void lambda$trackMixPanel$1(String event, MixPanelParamsBuilder params) {
        try {
            this.mixpanelApi.trackMap(event, params.buildParams());
            if (params instanceof MixPanelSuperParamsBuilder) {
                this.mixpanelApi.registerSuperPropertiesMap(((MixPanelSuperParamsBuilder) params).buildSuperParams());
            }
        } catch (Exception e) {
            this.mixpanelApi.trackMap(MixPanelEvents.ERROR_MIXPANEL, new MixpanelErrorParams(event, e).buildParams());
        }
    }

    private void trackAppsflyer(String action) {
        AppsFlyerLib.getInstance().trackEvent(HotellookApplication.getApp(), action, null);
    }
}
