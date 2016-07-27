package com.hotellook.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.data.DestinationType;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.api.dataloaders.MinPricesLoader;
import com.hotellook.core.api.pojo.minprice.ColoredMinPriceCalendar;
import com.hotellook.db.DatabaseHelper;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.events.ActivityDestroyedEvent;
import com.hotellook.events.AdultsUpdatedEvent;
import com.hotellook.events.AppLaunchedEvent;
import com.hotellook.events.CloseKidsPickerEvent;
import com.hotellook.events.CurrencyChangedEvent;
import com.hotellook.events.HotelSearchStartEvent;
import com.hotellook.events.LocationPermissionDeniedEvent;
import com.hotellook.events.LocationPermissionGrantedEvent;
import com.hotellook.events.LocationSettingsAppliedEvent;
import com.hotellook.events.LocationSettingsCanceledEvent;
import com.hotellook.events.MainActivityOnResumeEvent;
import com.hotellook.events.NewDestinationEvent;
import com.hotellook.events.OnReturnToSearchFormEvent;
import com.hotellook.events.OpenDestinationPickerEvent;
import com.hotellook.events.OpenDistanceSelectorEvent;
import com.hotellook.events.OpenRightDrawerEvent;
import com.hotellook.events.RequestLocationPermissionEvent;
import com.hotellook.events.RestartCitySearchEvent;
import com.hotellook.events.SearchFormDatesClickEvent;
import com.hotellook.events.SearchFormKidsClickEvent;
import com.hotellook.events.SearchFormOpenedEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.events.ShowCustomLocationRequestDialogEvent;
import com.hotellook.search.Search;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.mixpanel.LaunchSource;
import com.hotellook.ui.dialog.DismissDialogListener;
import com.hotellook.ui.screen.DatesPickerFragment;
import com.hotellook.ui.screen.Dialogs;
import com.hotellook.ui.screen.OnBackPressHandler;
import com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment;
import com.hotellook.ui.screen.filters.pois.PoiPickerFragment;
import com.hotellook.ui.screen.guide.AppGuideFragment;
import com.hotellook.ui.screen.hotel.HotelFragment;
import com.hotellook.ui.screen.hotel.HotelScreenOpenInfo;
import com.hotellook.ui.screen.hotel.Source;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.searchform.LaunchParams;
import com.hotellook.ui.screen.searchform.SearchFormFragment;
import com.hotellook.ui.screen.searchprogress.SearchProgressFragment;
import com.hotellook.ui.toolbar.ToolbarManager;
import com.hotellook.ui.view.BottomSheetView;
import com.hotellook.ui.view.kidspicker.KidsPickerView;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CommonPreferences;
import com.j256.ormlite.field.DatabaseField;
import com.squareup.otto.Subscribe;
import java.util.List;
import pl.charmas.android.reactivelocation.C1822R;

public class MainActivity extends BottomNavActivity {
    public static final int ACTIVITY_REQUEST_CHECK_LOCATION_SETTINGS = 1;
    private static final int ACTIVITY_REQUEST_PERMISSION_LOCATION = 2;
    public static final int PERMISSION_CODE_LOCATION = 101;
    public static final String REQUEST_LOCATION_PERMISSION_DIALOG_TAG = "REQUEST_LOCATION_TAG";
    private BottomSheetView bottomSheet;
    private boolean cancelRestoreInstanceState;
    private DrawerLayout drawerLayout;
    private GoogleApiClient googleClient;
    private MinPricesLoader minPricesLoader;
    private FrameLayout rightDrawer;
    private ToolbarManager toolbarManager;

    /* renamed from: com.hotellook.ui.activity.MainActivity.1 */
    class C12091 extends DismissDialogListener {
        C12091() {
        }

        public void onClick(View v) {
            super.onClick(v);
            HotellookApplication.eventBus().post(new LocationPermissionDeniedEvent());
        }
    }

    /* renamed from: com.hotellook.ui.activity.MainActivity.2 */
    class C12102 extends DismissDialogListener {
        C12102() {
        }

        public void onClick(View v) {
            super.onClick(v);
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
            MainActivity.this.startActivityForResult(intent, MainActivity.ACTIVITY_REQUEST_PERMISSION_LOCATION);
        }
    }

    public MainActivity() {
        this.cancelRestoreInstanceState = false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        if (!(savedInstanceState == null || HotellookApplication.getApp().wasAppLaunchedAsExpected())) {
            this.cancelRestoreInstanceState = true;
            savedInstanceState = null;
        }
        getWindow().setBackgroundDrawableResource(17170443);
        AndroidUtils.cacheNavBarSizes(this);
        super.onCreate(savedInstanceState);
        setContentView(C1178R.layout.activity_main);
        getWindow().setSoftInputMode(32);
        AndroidUtils.addMarginToOffsetNavBarRight(findViewById(C1178R.id.app_content));
        findViewById(C1178R.id.side_navigation_bar_bkg).getLayoutParams().width = AndroidUtils.getNavBarRightWidth();
        HotellookComponent component = HotellookApplication.from(this).getComponent();
        HotellookApplication.eventBus().register(this);
        this.drawerLayout = (DrawerLayout) findViewById(C1178R.id.drawer_layout);
        this.rightDrawer = (FrameLayout) findViewById(C1178R.id.right_drawer);
        lockRightDrawer();
        Toolbar toolbar = (Toolbar) findViewById(C1178R.id.toolbar);
        AndroidUtils.setUpViewBelowStatusBar(toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(17170445));
        this.toolbarManager = new ToolbarManager(toolbar, this);
        setUpDrawer();
        initDb();
        this.bottomSheet = (BottomSheetView) findViewById(C1178R.id.bottom_sheet);
        this.minPricesLoader = component.getMinPricesLoader();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            component.getCommonPreferences().clearOneSessionData();
            if (intent == null || intent.getData() == null) {
                HotellookApplication.eventBus().post(new AppLaunchedEvent(new LaunchSource(getIntent())));
                launchSearchFormIfNothingLaunched(MixPanelParams.LAUNCH);
                launchGuideIfNeeded();
            } else {
                launchFromIntent(intent);
            }
        }
        this.googleClient = new Builder(HotellookApplication.getApp()).addApi(AppIndex.API).build();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (this.cancelRestoreInstanceState) {
            this.cancelRestoreInstanceState = false;
        } else {
            super.onRestoreInstanceState(savedInstanceState);
        }
    }

    protected void onResume() {
        super.onResume();
        HotellookApplication.eventBus().post(new MainActivityOnResumeEvent());
        HotellookApplication.eventBus().post(new AppLaunchedEvent(new LaunchSource(getIntent())));
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        new PermissionResultProcessor(HotellookApplication.getApp().getComponent().getCommonPreferences(), this).onResult(requestCode, permissions, grantResults);
    }

    @Subscribe
    public void onRequestLocationUpdate(RequestLocationPermissionEvent event) {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            String[] strArr = new String[ACTIVITY_REQUEST_CHECK_LOCATION_SETTINGS];
            strArr[0] = "android.permission.ACCESS_FINE_LOCATION";
            ActivityCompat.requestPermissions(this, strArr, PERMISSION_CODE_LOCATION);
            return;
        }
        HotellookApplication.eventBus().post(new LocationPermissionGrantedEvent());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTIVITY_REQUEST_CHECK_LOCATION_SETTINGS /*1*/:
                switch (resultCode) {
                    case DatabaseField.NO_MAX_FOREIGN_AUTO_REFRESH_LEVEL_SPECIFIED /*-1*/:
                        HotellookApplication.eventBus().post(new LocationSettingsAppliedEvent());
                    case C1822R.styleable.SignInButton_buttonSize /*0*/:
                        HotellookApplication.eventBus().post(new LocationSettingsCanceledEvent());
                    default:
                }
            case ACTIVITY_REQUEST_PERMISSION_LOCATION /*2*/:
                if (AndroidUtils.hasLocationPermission(this)) {
                    HotellookApplication.eventBus().post(new LocationPermissionGrantedEvent());
                } else {
                    HotellookApplication.eventBus().post(new LocationPermissionDeniedEvent());
                }
            default:
        }
    }

    @Subscribe
    public void onShowCustomLocationRequestDialog(ShowCustomLocationRequestDialogEvent event) {
        Dialogs.showLocationPermissionDialog(this, new C12091(), new C12102());
    }

    @Subscribe
    public void onOpenDistanceFragment(OpenDistanceSelectorEvent event) {
        if (this.bottomSheet.isOpen()) {
            this.bottomSheet.dismiss();
        }
        showOverlay(PoiPickerFragment.create(event.poiPickSource));
    }

    private void launchSearchFormIfNothingLaunched(String source) {
        HotellookApplication.eventBus().post(new SearchFormOpenedEvent(source));
        launchFromSearchForm();
    }

    private void launchGuideIfNeeded() {
        CommonPreferences prefs = HotellookApplication.getApp().getComponent().getCommonPreferences();
        if (!prefs.wasGuideShown()) {
            prefs.putGuideWasShown();
            showOverlay(new AppGuideFragment());
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getData() != null) {
            launchFromIntent(intent);
        }
    }

    private void launchFromIntent(Intent intent) {
        if (intent.getData() == null) {
            launchSearchFormIfNothingLaunched(LaunchSource.DEEPLINK);
        } else {
            launchSearchFormOrHotelScreen(new LaunchParams(intent.getData()));
        }
    }

    private void launchSearchFormOrHotelScreen(LaunchParams launchParams) {
        HotellookApplication.getApp().getComponent().getRequestFlagsHelperTracker().saveLaunchRequestFlags(launchParams);
        HotellookApplication.eventBus().post(new AppLaunchedEvent(new LaunchSource(getIntent())));
        if (launchParams.getHotelId() != null) {
            launchHotelScreen(launchParams);
        } else {
            launchSearchForm(launchParams, LaunchSource.DEEPLINK);
        }
    }

    private void launchSearchForm(LaunchParams launchParams, String source) {
        HotellookApplication.eventBus().post(new SearchFormOpenedEvent(source));
        launchFromSearchForm(SearchFormFragment.create(launchParams));
    }

    private void launchHotelScreen(LaunchParams launchParams) {
        SearchFormData searchFormData = new SearchFormData(getApplicationContext(), getComponent().getSearchFormPreferences());
        searchFormData.reset(launchParams);
        searchFormData.saveData();
        HotelDataSource hotelDataSource = getComponent().hotelDataSource();
        hotelDataSource.observeFromIntent(launchParams);
        launchFromHotel(HotelFragment.create(hotelDataSource, new HotelScreenOpenInfo(Source.INTENT), 0));
        HotellookApplication.getApp().setAppLaunchedAsExpected();
    }

    private HotellookComponent getComponent() {
        return HotellookApplication.from(this).getComponent();
    }

    private void initDb() {
        getHelper();
    }

    public DatabaseHelper getHelper() {
        return HotellookApplication.getApp().getComponent().getDatabaseHelper();
    }

    private void setUpDrawer() {
        this.drawerLayout.setScrimColor(getResources().getColor(C1178R.color.drawer_content_overlay));
    }

    public void lockDrawer() {
        this.drawerLayout.setDrawerLockMode(ACTIVITY_REQUEST_CHECK_LOCATION_SETTINGS, 3);
    }

    public void unlockDrawer() {
        this.drawerLayout.setDrawerLockMode(0, 3);
    }

    public void lockRightDrawer() {
        this.drawerLayout.setDrawerLockMode(ACTIVITY_REQUEST_CHECK_LOCATION_SETTINGS, 5);
    }

    public void unlockRightDrawer() {
    }

    public boolean isRightDrawerOpened() {
        return this.drawerLayout.isDrawerOpen(5);
    }

    public void onBackPressed() {
        if (!closeBottomSheet()) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment == null || !(currentFragment instanceof OnBackPressHandler) || !((OnBackPressHandler) currentFragment).onBackPressedHandled()) {
                goBack();
            }
        }
    }

    public void returnToSearchForm() {
        goToSearchForm();
        HotellookApplication.eventBus().post(new OnReturnToSearchFormEvent());
    }

    public boolean closeBottomSheet() {
        if (!this.bottomSheet.isOpen()) {
            return false;
        }
        this.bottomSheet.dismiss();
        return true;
    }

    protected void onDestroy() {
        HotellookApplication.eventBus().post(new ActivityDestroyedEvent());
        HotellookApplication.eventBus().unregister(this);
        releaseToolbarManager();
        super.onDestroy();
    }

    private void releaseToolbarManager() {
        if (this.toolbarManager != null) {
            this.toolbarManager.release();
        }
    }

    public ToolbarManager getToolbarManager() {
        return this.toolbarManager;
    }

    public void closeRightDrawer() {
        this.drawerLayout.closeDrawer(5);
    }

    public void showBottomSheet(View view) {
        this.bottomSheet.show(view);
    }

    public void dismissBottomSheet() {
        this.bottomSheet.dismiss();
    }

    public void setFragmentToRightDrawer(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(C1178R.id.right_drawer, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void clearRightDrawer() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(C1178R.id.right_drawer);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    @Nullable
    public Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment overlayFragment = fragmentManager.findFragmentById(C1178R.id.overlay_frame);
        return overlayFragment != null ? overlayFragment : fragmentManager.findFragmentById(C1178R.id.content_frame);
    }

    public void showSearchFormKidsPicker(List<Integer> kids) {
        KidsPickerView kidsPickerView = (KidsPickerView) LayoutInflater.from(this).inflate(C1178R.layout.kids_picker_layout, this.bottomSheet, false);
        kidsPickerView.init(kids);
        showBottomSheet(kidsPickerView);
    }

    @Subscribe
    public void onCloseKidsPicker(CloseKidsPickerEvent event) {
        dismissBottomSheet();
    }

    @Subscribe
    public void onNewDestinationEvent(NewDestinationEvent event) {
        if (event.type != DestinationType.CITY) {
            this.minPricesLoader.reset();
        } else {
            preloadMinPrices();
        }
    }

    @Subscribe
    public void onRestartSearch(RestartCitySearchEvent event) {
        Search search = getComponent().searchKeeper().lastSearch();
        if (search == null) {
            returnToSearchForm();
            return;
        }
        SearchParams newParams = search.searchParams().toBuilder().allowEnGates(getComponent().getCommonPreferences().areEnGatesAllowed()).build();
        getComponent().searchEngine().makeSearch(newParams, new SearchStartEvent(newParams, Constants.Source.SEARCH_FORM, null));
        showFragment(SearchProgressFragment.create());
    }

    @Subscribe
    public void onHotelSearchStarted(HotelSearchStartEvent event) {
        resetStateOnNewSearch();
    }

    @Subscribe
    public void onSearchStarted(SearchStartEvent event) {
        resetStateOnNewSearch();
    }

    private void resetStateOnNewSearch() {
        resetFavoritesBackStackIfNotCurrent();
    }

    private void preloadMinPrices() {
        SearchFormData searchFormData = new SearchFormData(getApplicationContext(), HotellookApplication.getApp().getComponent().getSearchFormPreferences());
        this.minPricesLoader.reset();
        if (searchFormData.getDestinationType() == DestinationType.CITY) {
            this.minPricesLoader.load(searchFormData.getCityId(), searchFormData.getAdults());
        }
    }

    @Subscribe
    public void onAdultsUpdatedEvent(AdultsUpdatedEvent event) {
        preloadMinPrices();
    }

    @Subscribe
    public void onCurrencyChangedEvent(CurrencyChangedEvent event) {
        preloadMinPrices();
    }

    @Subscribe
    public void onOpenRightDrawer(OpenRightDrawerEvent event) {
        this.drawerLayout.openDrawer(5);
    }

    public ColoredMinPriceCalendar getMinPricesCalendar() {
        return this.minPricesLoader.getData();
    }

    protected void onStart() {
        super.onStart();
        this.googleClient.connect();
    }

    protected void onStop() {
        this.googleClient.disconnect();
        super.onStop();
    }

    public GoogleApiClient getGoogleClient() {
        return this.googleClient;
    }

    @Subscribe
    public void onSearchFormDatesClick(SearchFormDatesClickEvent event) {
        ColoredMinPriceCalendar minPriceCalendar = null;
        if (event.searchFormData.isCityType() && hasCalendarPricesForLocationId(event)) {
            minPriceCalendar = getMinPricesCalendar();
        }
        showFragment(DatesPickerFragment.create(event.searchFormData, minPriceCalendar));
    }

    private boolean hasCalendarPricesForLocationId(SearchFormDatesClickEvent event) {
        return this.minPricesLoader.hasData() && event.searchFormData.getCityId() == this.minPricesLoader.getLocationId();
    }

    @Subscribe
    public void onOpenDestinationPicker(OpenDestinationPickerEvent event) {
        showFragment(new DestinationPickerFragment());
    }

    @Subscribe
    public void onOpenKidsPickerEvent(SearchFormKidsClickEvent event) {
        showSearchFormKidsPicker(event.kids);
    }
}
