package com.hotellook.ui.screen.hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AlertDialog;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.HotelLinkGenerator;
import com.hotellook.api.RequestFlags;
import com.hotellook.api.RequestFlagsGenerator;
import com.hotellook.badges.Badge;
import com.hotellook.badges.Badges;
import com.hotellook.booking.SearchInfoForBooking;
import com.hotellook.common.click.DoubleClickPreventer;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.common.view.OnScreenLocation;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.currency.CurrencyFormatter;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.events.BestOfferChangeSearchParamsClickEvent;
import com.hotellook.events.BestOfferFindPricesClickEvent;
import com.hotellook.events.BestOfferUpdateClickEvent;
import com.hotellook.events.BookRequestEvent;
import com.hotellook.events.ChangeSearchParamsEvent;
import com.hotellook.events.GalleryGridItemClickEvent;
import com.hotellook.events.HotelAddedToFavoritesEvent;
import com.hotellook.events.HotelFragmentCloseEvent;
import com.hotellook.events.HotelFragmentCreatedEvent;
import com.hotellook.events.HotelLinkGeneratedEvent;
import com.hotellook.events.HotelMapClickEvent;
import com.hotellook.events.HotelMapOpenedEvent;
import com.hotellook.events.HotelRemovedFromFavoritesEvent;
import com.hotellook.events.HotelScreenChildNewScrollEvent;
import com.hotellook.events.HotelScreenScrollEvent;
import com.hotellook.events.HotelShareEvent;
import com.hotellook.events.HotelTabSelectedEvent;
import com.hotellook.events.OnNextPhotoBtnClick;
import com.hotellook.events.OnPhotoClickEvent;
import com.hotellook.events.OnPhotoSelectedEvent;
import com.hotellook.events.OnPreviousPhotoBtnClick;
import com.hotellook.events.OutOfDateResultsEvent;
import com.hotellook.events.OverlayClosedEvent;
import com.hotellook.events.PlayRatingAnimationEvent;
import com.hotellook.events.PoiMapFragmentCloseEvent;
import com.hotellook.events.PoiMapFragmentOpenEvent;
import com.hotellook.events.PurchaseEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.events.ShareHotelEvent;
import com.hotellook.events.StreetViewBtnClickEvent;
import com.hotellook.filters.Filters;
import com.hotellook.search.Discounts;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.Highlights;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.Search;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants.Source;
import com.hotellook.statistics.flurry.HotelScreenUserActions;
import com.hotellook.ui.dialog.DismissDialogListener;
import com.hotellook.ui.dialog.LoadingDialogContent;
import com.hotellook.ui.dialog.LoadingDialogFactory;
import com.hotellook.ui.screen.AlertDialogFragment;
import com.hotellook.ui.screen.Dialogs;
import com.hotellook.ui.screen.OnBackPressHandler;
import com.hotellook.ui.screen.Toasts;
import com.hotellook.ui.screen.browser.BookingLoadingDialogFragment;
import com.hotellook.ui.screen.browser.BrowserFragment;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.gallery.PhotosGalleryFragment;
import com.hotellook.ui.screen.gallery.TransitionData;
import com.hotellook.ui.screen.gallery.ViewSizeCalculator;
import com.hotellook.ui.screen.hotel.HotelTabsAdapter.FragmentsStates;
import com.hotellook.ui.screen.hotel.api.Booking;
import com.hotellook.ui.screen.hotel.data.BasicHotelData;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.hotel.general.HotelGeneralFragment;
import com.hotellook.ui.screen.hotel.general.StreetViewFragment;
import com.hotellook.ui.screen.map.MapFragment;
import com.hotellook.ui.screen.searchprogress.SearchProgressFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.tabview.SlidingTabLayout;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.DateUtils;
import com.hotellook.utils.RxUtil;
import com.hotellook.utils.Size;
import com.hotellook.utils.sdk.OnPageChangeListenerAdapter;
import com.squareup.otto.Subscribe;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.zhanghai.android.materialprogressbar.C1759R;
import pl.charmas.android.reactivelocation.C1822R;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class HotelFragment extends BaseFragment implements OnBackPressHandler {
    private static final String HOTEL_LINK_TEMPLATE = "https://search.hotellook.com/?hotelId=%d";
    public static final String LOADING_FROM_INTENT_DIALOG_TAG = "loading from intent dialog";
    private static final int MENU_ICON_ANIMATION_DURATION = 250;
    private static final float MOTION_FACTOR_FOR_SLOW_SCROLLING_VIEWS = 0.33333334f;
    public static final String OUT_OF_DATE_DIALOG_TAG = "out of date dialog";
    public static final String RESTART_SEARCH_DIALOG_TAG = "RESTART_SEARCh_DIALOG_TAG";
    private static final int RESULTS_TIMEOUT = 3600000;
    private static final String SHARING_LOADING_DIALOG_TAG = "SHARING_LOADING_DIALOG_TAG";
    private static final float SKIP_FACTOR_FOR_SLOW_REACTING_VIEWS = 0.33333334f;
    private static final String STRING_DELIMITER = " \u2014 ";
    private Action appIndexingViewAction;
    @Nullable
    private Badges badges;
    private Offer bestOffer;
    private Observable<Booking> bookingObservable;
    private Subscription bookingSubscription;
    private Discounts discounts;
    private MenuItem favoriteMenuItem;
    private TransitionDrawable favoriteTransitionDrawable;
    private FavoritesRepository favoritesRepository;
    @Nullable
    private Filters filters;
    private Size gridItemSize;
    private Highlights highlights;
    private HotelScreenCardView hotelCard;
    private boolean hotelCardCollapsed;
    private View hotelCardOverlay;
    private Size hotelCardSize;
    private HotelDataSource hotelDataSource;
    private int initImageIndex;
    private int lastScrollY;
    private View layout;
    private int layoutOverPhotoOffset;
    private AlertDialog loadingFromIntentErrorAlertDialog;
    private int maxContentWidth;
    private int maxScrollForLayout;
    private View nextPhotoBtn;
    private HotelScreenOpenInfo openInfo;
    private final DoubleClickPreventer optionsDoubleClickPreventer;
    private View photoControls;
    private View previousPhotoBtn;
    private FragmentsStates savedTabsFragmentsStates;
    private float scrollProgress;
    private View scrollableLayout;
    private MenuItem shareMenuItem;
    private AlertDialog sharingLoadingDialog;
    private int standardOffset;
    private SlidingTabLayout tabBar;
    private View tabBarShadow;
    private HotelTabsAdapter tabsAdapter;
    private View toolbarShadow;
    private TextView toolbarTitle;
    private boolean underOtherFragment;
    private final HotelScreenUserActions userActions;
    private ViewPagerWithExtendedMotionInterception viewPager;
    private View viewPagerBkg;
    private View viewPagerLoader;
    private ViewSizeCalculator viewSizeCalculator;

    /* renamed from: com.hotellook.ui.screen.hotel.HotelFragment.1 */
    class C12991 implements OnGlobalLayoutListener {
        C12991() {
        }

        public void onGlobalLayout() {
            AndroidUtils.removeOnGlobalLayoutListener(HotelFragment.this.layout, this);
            if (HotelFragment.this.getActivity() != null && HotelFragment.this.viewPager != null) {
                HotelFragment.this.computeMaxOffsetForPhotoOverlay();
                HotelFragment.this.setUpHotelCard();
                HotelFragment.this.tabBar.setY((float) (HotelFragment.this.hotelCardSize.getHeight() - HotelFragment.this.layoutOverPhotoOffset));
                float viewPagerY = HotelFragment.this.tabBar.getY() + ((float) HotelFragment.this.getContext().getResources().getDimensionPixelSize(C1178R.dimen.tab_height));
                HotelFragment.this.viewPagerBkg.setY(viewPagerY);
                HotelFragment.this.tabBarShadow.setY(viewPagerY);
                HotelFragment.this.toolbarShadow.setY((float) (AndroidUtils.getToolbarHeight(HotelFragment.this.getActivity()) + AndroidUtils.getStatusBarHeight(HotelFragment.this.getActivity())));
                HotelFragment.this.viewPager.setY(viewPagerY);
                int viewPagerHeight = ((int) (((float) HotelFragment.this.scrollableLayout.getHeight()) - HotelFragment.this.viewPager.getY())) + HotelFragment.this.maxScrollForLayout;
                HotelFragment.this.viewPager.getLayoutParams().height = viewPagerHeight;
                HotelFragment.this.viewPagerBkg.getLayoutParams().height = viewPagerHeight;
                HotelFragment.this.viewPagerLoader.setY(viewPagerY);
                if (AndroidUtils.isLandscape(HotelFragment.this.getContext())) {
                    HotelFragment.this.limitViewWidthToMax(HotelFragment.this.tabBar);
                    HotelFragment.this.limitViewWidthToMax(HotelFragment.this.tabBarShadow);
                    HotelFragment.this.limitViewWidthToMax(HotelFragment.this.viewPagerBkg);
                    int sideMargin = (HotelFragment.this.scrollableLayout.getWidth() - HotelFragment.this.maxContentWidth) / 2;
                    HotelFragment.this.hotelCard.setContentPadding(sideMargin, 0, sideMargin, HotelFragment.this.layoutOverPhotoOffset);
                    HotelFragment.this.photoControls.setY((float) ((HotelFragment.this.hotelCardSize.getHeight() / 2) - (HotelFragment.this.photoControls.getHeight() / 2)));
                    HotelFragment.this.toolbarShadow.setVisibility(0);
                    return;
                }
                HotelFragment.this.toolbarShadow.setVisibility(8);
                HotelFragment.this.photoControls.setVisibility(8);
                HotelFragment.this.hotelCard.setContentPadding(HotelFragment.this.standardOffset, 0, HotelFragment.this.standardOffset, 0);
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.HotelFragment.2 */
    class C13002 implements OnGlobalLayoutListener {
        C13002() {
        }

        public void onGlobalLayout() {
            AndroidUtils.removeOnGlobalLayoutListener(HotelFragment.this.layout, this);
            if (HotelFragment.this.getActivity() != null) {
                HotelFragment.this.scrollableLayout.getLayoutParams().height = HotelFragment.this.layout.getHeight() + HotelFragment.this.maxScrollForLayout;
                HotelFragment.this.restoreScrollProgress();
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.HotelFragment.3 */
    class C13013 extends MonkeySafeClickListener {
        C13013() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new OnPreviousPhotoBtnClick());
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.HotelFragment.4 */
    class C13024 extends MonkeySafeClickListener {
        C13024() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new OnNextPhotoBtnClick());
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.HotelFragment.5 */
    class C13035 extends OnPageChangeListenerAdapter {
        final /* synthetic */ BasicHotelData val$basicHotelData;

        C13035(BasicHotelData basicHotelData) {
            this.val$basicHotelData = basicHotelData;
        }

        public void onPageSelected(int position) {
            int tabId = HotelFragment.this.tabsAdapter.getTabId(position);
            HotellookApplication.eventBus().post(new HotelTabSelectedEvent(this.val$basicHotelData.id(), tabId));
            HotelFragment.this.applyStatisticsPage(tabId);
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.HotelFragment.6 */
    class C13046 extends DismissDialogListener {
        C13046() {
        }

        public void onClick(View v) {
            super.onClick(v);
            if (HotelFragment.this.getActivity() != null) {
                HotelFragment.this.getMainActivity().returnToSearchForm();
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.HotelFragment.7 */
    class C13057 extends DismissDialogListener {
        C13057() {
        }

        public void onClick(View v) {
            super.onClick(v);
            HotelFragment.this.hotelDataSource.updateBasicData();
            HotelFragment.this.subscribeToBasicData();
            HotelFragment.this.viewPagerLoader.animate().alpha(1.0f);
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.HotelFragment.8 */
    class C13068 extends DismissDialogListener {
        C13068() {
        }

        public void onClick(View v) {
            super.onClick(v);
            SearchParams searchParams = HotelFragment.this.hotelDataSource.searchParams();
            RequestFlags requestFlags = HotelFragment.this.hotelDataSource.requestFlags();
            if (requestFlags == null) {
                requestFlags = HotelFragment.this.generateRequestFlags(searchParams);
            }
            SearchStartEvent event = new SearchStartEvent(searchParams, Source.MY_HOTELS, HotelFragment.this.hotelCard.getImageUrl());
            if (searchParams.isHotelSearch()) {
                HotelFragment.this.hotelDataSource.updateOffers(searchParams, requestFlags);
                HotellookApplication.eventBus().post(event);
            } else if (HotelFragment.this.haveSearchDatesPassed(searchParams)) {
                HotelFragment.this.getMainActivity().returnToSearchForm();
            } else {
                HotelFragment.this.getComponent().searchEngine().makeSearch(searchParams, event);
                HotelFragment.this.getMainActivity().showFragment(SearchProgressFragment.create(), false);
            }
        }
    }

    private class OnPageChangeListenerForRatingAnimation implements OnPageChangeListener {
        private int currentState;

        private OnPageChangeListenerForRatingAnimation() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (this.currentState == 1 && ((double) positionOffset) > 0.1d && HotelFragment.this.tabsAdapter.isRatingTab(position + 1)) {
                HotellookApplication.eventBus().post(new PlayRatingAnimationEvent());
                HotelFragment.this.viewPager.post(HotelFragment$OnPageChangeListenerForRatingAnimation$$Lambda$1.lambdaFactory$(this, this));
            }
        }

        /* synthetic */ void lambda$onPageScrolled$0(OnPageChangeListener listener) {
            if (HotelFragment.this.viewPager != null) {
                HotelFragment.this.viewPager.removeOnPageChangeListener(listener);
            }
        }

        public void onPageSelected(int position) {
            if (HotelFragment.this.tabsAdapter.isRatingTab(position)) {
                HotelFragment.this.viewPager.post(HotelFragment$OnPageChangeListenerForRatingAnimation$$Lambda$2.lambdaFactory$(this, this));
                HotelFragment.this.applyStatisticsPage(HotelFragment.this.tabsAdapter.getTabId(position));
            }
        }

        /* synthetic */ void lambda$onPageSelected$1(OnPageChangeListener listener) {
            if (HotelFragment.this.viewPager != null) {
                HotelFragment.this.viewPager.removeOnPageChangeListener(listener);
                HotellookApplication.eventBus().post(new PlayRatingAnimationEvent());
            }
        }

        public void onPageScrollStateChanged(int state) {
            this.currentState = state;
        }
    }

    static class Snapshot {
        final Observable<Booking> bookingObservable;
        final HotelDataSource hotelDataSource;
        final int initImageIndex;
        final HotelScreenOpenInfo openInfo;
        final float scrollProgress;
        final FragmentsStates tabsFragmentsStates;
        final boolean underOtherFragment;

        Snapshot(int initImageIndex, HotelScreenOpenInfo openInfo, int scrollY, int maxScroll, boolean underOtherFragment, HotelDataSource hotelDataSource, Observable<Booking> bookingObservable, FragmentsStates tabsFragmentsStates) {
            this.initImageIndex = initImageIndex;
            this.openInfo = openInfo;
            this.underOtherFragment = underOtherFragment;
            this.scrollProgress = ((float) scrollY) / ((float) maxScroll);
            this.hotelDataSource = hotelDataSource;
            this.bookingObservable = bookingObservable;
            this.tabsFragmentsStates = tabsFragmentsStates;
        }
    }

    public HotelFragment() {
        this.optionsDoubleClickPreventer = new DoubleClickPreventer(1000);
        this.userActions = new HotelScreenUserActions();
        this.underOtherFragment = false;
        this.hotelCardCollapsed = false;
        this.lastScrollY = 0;
    }

    public static HotelFragment create(HotelDataSource hotelDataSource, HotelScreenOpenInfo hotelScreenOpenInfo) {
        return create(hotelDataSource, 0, hotelScreenOpenInfo);
    }

    public static HotelFragment create(HotelDataSource hotelDataSource, HotelScreenOpenInfo hotelScreenOpenInfo, int photoIdx) {
        return create(hotelDataSource, photoIdx, hotelScreenOpenInfo);
    }

    private static HotelFragment create(HotelDataSource hotelDataSource, int initialPhotoIdx, HotelScreenOpenInfo hotelScreenOpenInfo) {
        HotelFragment fragment = new HotelFragment();
        fragment.setHotelDataSource(hotelDataSource);
        fragment.setInitImageIndex(initialPhotoIdx);
        fragment.setOpenInfo(hotelScreenOpenInfo);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        Highlights highlights = null;
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.favoritesRepository = new FavoritesRepository(getMainActivity().getHelper());
        this.viewSizeCalculator = new ViewSizeCalculator(getActivity());
        this.hotelCardSize = this.viewSizeCalculator.calculateHotelScreenPhotoSize(getActivity());
        this.standardOffset = getResources().getDimensionPixelSize(C1178R.dimen.standard_offset);
        Search search = getComponent().searchKeeper().lastSearch();
        this.filters = null;
        this.badges = null;
        if (!(search == null || search.searchData() == null)) {
            this.filters = search.filters();
            this.badges = search.badges();
            SearchData searchData = search.searchData();
            this.discounts = searchData == null ? null : searchData.discounts();
            if (searchData != null) {
                highlights = searchData.highlights();
            }
            this.highlights = highlights;
        }
        this.discounts = this.discounts == null ? new Discounts(Collections.emptyMap()) : this.discounts;
        this.highlights = this.highlights == null ? new Highlights(Collections.emptyMap()) : this.highlights;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.layout = inflater.inflate(C1178R.layout.fragment_hotel, container, false);
        if (hasInitialSnapshot()) {
            restoreFromSnapshot((Snapshot) initialSnapshot());
        }
        this.tabBar = (SlidingTabLayout) this.layout.findViewById(C1178R.id.tabs);
        this.tabBarShadow = this.layout.findViewById(C1178R.id.tab_shadow);
        this.hotelCard = (HotelScreenCardView) this.layout.findViewById(C1178R.id.hotel_info);
        this.scrollableLayout = this.layout.findViewById(C1178R.id.scrollable_layout);
        this.viewPagerLoader = this.layout.findViewById(C1178R.id.pager_loading);
        this.hotelCardOverlay = this.layout.findViewById(C1178R.id.hotel_card_overlay);
        this.viewPager = (ViewPagerWithExtendedMotionInterception) this.layout.findViewById(C1178R.id.pager);
        this.toolbarShadow = this.layout.findViewById(C1178R.id.overlay_shadow);
        this.photoControls = this.layout.findViewById(C1178R.id.controls);
        this.viewPagerBkg = this.layout.findViewById(C1178R.id.viewpager_bkg);
        this.previousPhotoBtn = this.photoControls.findViewById(C1178R.id.previous_photo);
        this.nextPhotoBtn = this.photoControls.findViewById(C1178R.id.next_photo);
        this.standardOffset = getResources().getDimensionPixelSize(C1178R.dimen.standard_offset);
        setUpUiOffsets();
        this.gridItemSize = this.viewSizeCalculator.calculateGridImageSize(this.maxContentWidth);
        if (this.hotelDataSource.hasBasicData()) {
            setUpBasicFeatures();
        } else {
            subscribeToBasicData();
            cleanToolbar();
        }
        HotellookApplication.eventBus().register(this);
        return this.layout;
    }

    private void subscribeToBasicData() {
        keepUntilDestroyView(Observable.combineLatest(this.hotelDataSource.basicHotelDataObservable(), this.hotelDataSource.locationObservable(), HotelFragment$$Lambda$1.lambdaFactory$()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(HotelFragment$$Lambda$2.lambdaFactory$(this), HotelFragment$$Lambda$3.lambdaFactory$(this)));
    }

    /* synthetic */ void lambda$subscribeToBasicData$0(Pair basicHotelDataCityInfoPair) {
        setUpBasicFeatures();
    }

    /* synthetic */ void lambda$subscribeToBasicData$1(Throwable error) {
        showLoadingHotelDataErrorDialog();
    }

    private RequestFlags generateRequestFlags(SearchParams updatedSearchParams) {
        return new RequestFlagsGenerator().searchFromHotelScreenSearchForm(updatedSearchParams);
    }

    private void setHotelDataSource(HotelDataSource hotelDataSource) {
        this.hotelDataSource = hotelDataSource;
    }

    private void sendOnOpenHotelFragmentEvent() {
        List<Offer> inLocation;
        DiscountsByRooms discountsByRooms;
        HighlightsByRooms highlightsByRooms = null;
        if (hasPrices()) {
            inLocation = getPrices();
        } else {
            inLocation = null;
        }
        long hotelId = this.hotelDataSource.basicHotelData().id();
        HotelScreenOpenInfo hotelScreenOpenInfo = this.openInfo;
        Badges badges = this.badges;
        SearchParams searchParams = this.hotelDataSource.searchParams();
        if (this.discounts != null) {
            discountsByRooms = this.discounts.get(hotelId);
        } else {
            discountsByRooms = null;
        }
        if (this.highlights != null) {
            highlightsByRooms = this.highlights.get(hotelId);
        }
        HotellookApplication.eventBus().post(new HotelFragmentCreatedEvent(hotelScreenOpenInfo, hotelId, badges, inLocation, searchParams, discountsByRooms, highlightsByRooms));
    }

    private boolean hasPrices() {
        return CollectionUtils.isNotEmpty(this.hotelDataSource.offers());
    }

    private void startAppIndexingViewing(BasicHotelData basicHotelData) {
        Observable.fromCallable(HotelFragment$$Lambda$4.lambdaFactory$(this, basicHotelData)).compose(bindUntilDestroyView()).subscribeOn(Schedulers.io()).subscribe();
    }

    /* synthetic */ PendingResult lambda$startAppIndexingViewing$2(BasicHotelData basicHotelData) throws Exception {
        return makeViewAction(basicHotelData);
    }

    private PendingResult<Status> makeViewAction(BasicHotelData basicHotelData) {
        this.appIndexingViewAction = Action.newAction("http://schema.org/ViewAction", basicHotelData.name(), Uri.parse(String.format(HOTEL_LINK_TEMPLATE, new Object[]{Long.valueOf(basicHotelData.id())})));
        return AppIndex.AppIndexApi.start(getMainActivity().getGoogleClient(), this.appIndexingViewAction);
    }

    private void setUpUiOffsets() {
        computeMaxContentWidth();
        int sideMargin = (AndroidUtils.getDisplaySize(getContext()).x - this.maxContentWidth) / 2;
        this.viewPager.setPaddingWithInterception(sideMargin, 0, sideMargin, 0);
        this.viewPager.setPageMargin(getResources().getDimensionPixelOffset(C1178R.dimen.standard_offset));
        this.layout.getViewTreeObserver().addOnGlobalLayoutListener(new C12991());
    }

    private void computeMaxOffsetForPhotoOverlay() {
        int toolbarHeight = AndroidUtils.getToolbarHeight(getActivity());
        int statusBarHeight = AndroidUtils.getStatusBarHeight(getActivity());
        this.layoutOverPhotoOffset = Math.min(getResources().getDimensionPixelSize(C1178R.dimen.layout_over_photo_offset), ((this.hotelCardSize.getHeight() - this.hotelCard.getContentHeight()) - toolbarHeight) - statusBarHeight);
        this.maxScrollForLayout = ((this.hotelCardSize.getHeight() - statusBarHeight) - toolbarHeight) - this.layoutOverPhotoOffset;
    }

    private void computeMaxContentWidth() {
        this.maxContentWidth = Math.min(getResources().getDimensionPixelSize(C1178R.dimen.hotel_max_content_width), (int) (((float) AndroidUtils.getDisplaySize(getActivity()).x) - ((this.previousPhotoBtn.getX() + ((float) this.previousPhotoBtn.getWidth())) * 2.0f)));
    }

    private void limitViewWidthToMax(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.width = this.maxContentWidth;
        layoutParams.gravity = 1;
    }

    private void setUpHotelCard() {
        ViewGroup.LayoutParams hotelCardLayoutParams = this.hotelCard.getLayoutParams();
        hotelCardLayoutParams.width = this.hotelCardSize.getWidth();
        hotelCardLayoutParams.height = this.hotelCardSize.getHeight();
        this.hotelCard.setLayoutParams(hotelCardLayoutParams);
        ViewGroup.LayoutParams hotelCardOverlayLayoutParams = this.hotelCardOverlay.getLayoutParams();
        hotelCardOverlayLayoutParams.width = this.hotelCardSize.getWidth();
        if (AndroidUtils.isLandscape(getActivity())) {
            hotelCardOverlayLayoutParams.height = AndroidUtils.getToolbarHeight(getActivity()) + AndroidUtils.getStatusBarHeight(getActivity());
        } else {
            hotelCardOverlayLayoutParams.height = this.hotelCardSize.getHeight();
        }
        this.hotelCardOverlay.setLayoutParams(hotelCardOverlayLayoutParams);
    }

    private void cleanToolbar() {
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), new ToolbarSettings().toggleColor(ContextCompat.getColor(getContext(), 17170443)).navigationMode(getToolbarSourceDependantNavigationMode()));
    }

    private int getToolbarSourceDependantNavigationMode() {
        return this.openInfo.source == Source.INTENT ? 0 : 1;
    }

    private void setUpScrollingOnGlobalLayout() {
        this.layout.getViewTreeObserver().addOnGlobalLayoutListener(new C13002());
    }

    private void restoreScrollProgress() {
        this.scrollableLayout.post(HotelFragment$$Lambda$5.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$restoreScrollProgress$3() {
        this.scrollableLayout.setTranslationY((float) (-computeScroll(this.scrollProgress)));
    }

    private int computeScroll(float scrollProgress) {
        return (int) (((float) this.maxScrollForLayout) * scrollProgress);
    }

    private void setUpBasicFeatures() {
        sendOnOpenHotelFragmentEvent();
        BasicHotelData basicHotelData = this.hotelDataSource.basicHotelData();
        startAppIndexingViewing(this.hotelDataSource.basicHotelData());
        this.viewPagerLoader.setVisibility(8);
        setUpPhotoControls(basicHotelData);
        setUpTabs(basicHotelData);
        if (!getMainActivity().isOverlayAttached()) {
            setUpToolbar(basicHotelData);
        }
        this.hotelCard.setUpData(basicHotelData, this.hotelCardSize, this.initImageIndex);
        setUpMenuItems(basicHotelData);
        showInfoHiderSafely();
        setUpScrollingOnGlobalLayout();
    }

    private void setUpMenuItems(BasicHotelData basicHotelData) {
        if (this.favoriteMenuItem != null) {
            showHotelScreenMenuItems();
            setUpFavoritesMenuItemDrawable(basicHotelData.id());
        }
    }

    private void setUpPhotoControls(BasicHotelData basicHotelData) {
        if (basicHotelData.photoCount() <= 1) {
            this.photoControls.setVisibility(8);
        }
        this.previousPhotoBtn.setOnClickListener(new C13013());
        this.nextPhotoBtn.setOnClickListener(new C13024());
    }

    private void showInfoHiderSafely() {
        if (this.hotelCard != null) {
            this.hotelCard.showContent();
        }
    }

    private void launchGalleryScreen(int index) {
        TransitionData transitionData = AndroidUtils.isPortrait(getContext()) ? new TransitionData(OnScreenLocation.create(this.hotelCard.getPhotoPager()), this.viewSizeCalculator.calculateHotelScreenPhotoSize(getContext()), this.hotelCardSize) : null;
        this.hotelCard.hideContentInstantly();
        launchGalleryScreen(index, transitionData);
    }

    private void launchGalleryScreen(int index, TransitionData transitionData) {
        BasicHotelData basicHotelData = this.hotelDataSource.basicHotelData();
        showOverlay(PhotosGalleryFragment.create(basicHotelData.id(), basicHotelData.photoCount(), index, this.gridItemSize, transitionData), true);
        this.hotelCard.hidePhotoCounter();
    }

    private void setUpControlsOnPageSelected(int position) {
        if (position == 0) {
            enableControlBtn(this.previousPhotoBtn, false);
        } else {
            enableControlBtn(this.previousPhotoBtn, true);
        }
        if (position == this.hotelCard.getPhotoPager().getAdapter().getCount() - 1) {
            enableControlBtn(this.nextPhotoBtn, false);
        } else {
            enableControlBtn(this.nextPhotoBtn, true);
        }
    }

    private void enableControlBtn(View view, boolean enabled) {
        if (enabled != view.isEnabled()) {
            view.setEnabled(enabled);
            view.setAlpha(enabled ? 1.0f : 0.4f);
            view.setClickable(enabled);
        }
    }

    private void setUpTabs(BasicHotelData basicHotelData) {
        this.tabBar.setDistributeEvenly(false);
        this.tabBar.setCustomTabView(C1178R.layout.view_hotel_tab_item, C1178R.id.tv);
        this.tabBar.setTabsStripMargins(getResources().getDimensionPixelSize(C1178R.dimen.hotel_tabs_strip_margin), 0, 0, 0);
        this.tabsAdapter = new HotelTabsAdapter(getActivity(), getChildFragmentManager(), this.viewPager.getId(), this.openInfo.source, this.hotelDataSource, this.gridItemSize, this.maxContentWidth);
        if (this.savedTabsFragmentsStates != null) {
            this.tabsAdapter.restoreFragmentsStates(this.savedTabsFragmentsStates);
        }
        this.viewPager.setOffscreenPageLimit(1);
        this.viewPager.setAdapter(this.tabsAdapter);
        if (this.tabsAdapter.hasRatingTab()) {
            this.viewPager.addOnPageChangeListener(new OnPageChangeListenerForRatingAnimation());
        }
        this.viewPager.addOnPageChangeListener(new C13035(basicHotelData));
        this.tabBar.setViewPager(this.viewPager);
    }

    private void applyStatisticsPage(int tabId) {
        switch (tabId) {
            case C1822R.styleable.MapAttrs_uiZoomGestures /*12*/:
                this.userActions.setTyShown();
            case C1822R.styleable.MapAttrs_useViewLifecycle /*13*/:
                this.userActions.setFqShown();
            default:
        }
    }

    private List<Offer> getPrices() {
        return this.hotelDataSource.offers();
    }

    private void setUpToolbar(BasicHotelData basicHotelData) {
        View wrappedToolbarTitle = LayoutInflater.from(getActivity()).inflate(C1178R.layout.wraped_toolbar_title, getMainActivity().getToolbarManager().getToolbar(), false);
        this.toolbarTitle = (TextView) wrappedToolbarTitle.findViewById(C1759R.id.title);
        this.toolbarTitle.setText(basicHotelData.name());
        this.toolbarTitle.setTextColor(ContextCompat.getColor(getContext(), 17170443));
        if (this.hotelCardCollapsed) {
            this.toolbarTitle.setAlpha(1.0f);
        } else {
            this.toolbarTitle.setAlpha(0.0f);
        }
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), new ToolbarSettings().toggleColor(ContextCompat.getColor(getContext(), 17170443)).navigationMode(getToolbarSourceDependantNavigationMode()).withCustomView(wrappedToolbarTitle));
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!this.underOtherFragment) {
            inflater.inflate(C1178R.menu.hotel_actions, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        if (!(this.underOtherFragment || getActivity() == null)) {
            this.favoriteMenuItem = menu.findItem(C1178R.id.action_like);
            if (this.favoriteMenuItem != null) {
                this.favoriteTransitionDrawable = (TransitionDrawable) this.favoriteMenuItem.getIcon();
                this.favoriteTransitionDrawable.setCrossFadeEnabled(true);
                this.shareMenuItem = menu.findItem(C1178R.id.action_share);
                this.favoriteMenuItem.setVisible(false);
                this.shareMenuItem.setVisible(false);
                if (this.hotelDataSource.hasBasicData()) {
                    setUpMenuItems(this.hotelDataSource.basicHotelData());
                }
            } else {
                return;
            }
        }
        super.onPrepareOptionsMenu(menu);
    }

    private void setUpFavoritesMenuItemDrawable(long hotelId) {
        if (this.favoritesRepository.isInFavorites(hotelId)) {
            this.favoriteTransitionDrawable.startTransition(0);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.optionsDoubleClickPreventer.preventDoubleClick()) {
            return true;
        }
        long hotelId = this.hotelDataSource.basicHotelData().id();
        switch (item.getItemId()) {
            case C1178R.id.action_share /*2131690084*/:
                HotellookApplication.eventBus().post(new HotelShareEvent(this.openInfo.source));
                showSharingLoadingDialog();
                new HotelLinkGenerator(this.hotelDataSource.searchParams(), hotelId, AndroidUtils.isTablet(getContext())).postShortUrlRequest();
                break;
            case C1178R.id.action_like /*2131690085*/:
                if (!this.favoritesRepository.isInFavorites(hotelId)) {
                    addHotelToFavorites();
                    this.favoriteTransitionDrawable.startTransition(MENU_ICON_ANIMATION_DURATION);
                    Toasts.showHotelAddedToFavorites(getActivity());
                    HotellookApplication.eventBus().post(new HotelAddedToFavoritesEvent(this.openInfo.source));
                    break;
                }
                removeHotelFromFavorites(hotelId);
                this.favoriteTransitionDrawable.reverseTransition(MENU_ICON_ANIMATION_DURATION);
                Toasts.showHotelRemovedFromFavorites(getActivity());
                HotellookApplication.eventBus().post(new HotelRemovedFromFavoritesEvent(this.openInfo.source));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHotelScreenMenuItems() {
        showMenuItemsWithStandardDelay(this.favoriteMenuItem, this.shareMenuItem);
    }

    private void showSharingLoadingDialog() {
        if (this.sharingLoadingDialog == null) {
            this.sharingLoadingDialog = new LoadingDialogFactory(getActivity()).createDialog(new LoadingDialogContent(C1178R.string.sharing_loading_message));
            this.sharingLoadingDialog.setCancelable(false);
            this.sharingLoadingDialog.setOnDismissListener(HotelFragment$$Lambda$6.lambdaFactory$(this));
            AlertDialogFragment.create(this.sharingLoadingDialog).show(getActivity().getSupportFragmentManager(), SHARING_LOADING_DIALOG_TAG);
        }
    }

    /* synthetic */ void lambda$showSharingLoadingDialog$4(DialogInterface dialog) {
        this.sharingLoadingDialog = null;
    }

    private void addHotelToFavorites() {
        BasicHotelData basicHotelData = this.hotelDataSource.basicHotelData();
        CityInfo cityInfo = this.hotelDataSource.cityInfo();
        SearchParams searchParams;
        if (this.hotelDataSource.offers() == null) {
            searchParams = this.hotelDataSource.searchParams();
            if (searchParams != null) {
                this.favoritesRepository.addToFavorites(basicHotelData, cityInfo, searchParams);
                return;
            } else {
                this.favoritesRepository.addToFavorites(basicHotelData, cityInfo);
                return;
            }
        }
        searchParams = this.hotelDataSource.searchParams();
        if (searchParams != null) {
            this.favoritesRepository.addToFavorites(basicHotelData, cityInfo, searchParams);
        }
    }

    private void removeHotelFromFavorites(long hotelId) {
        this.favoritesRepository.removeFromFavorites(hotelId);
    }

    private void setInitImageIndex(int initImageIndex) {
        this.initImageIndex = initImageIndex;
    }

    public void onStop() {
        super.onStop();
        if (this.appIndexingViewAction != null) {
            AppIndex.AppIndexApi.end(getMainActivity().getGoogleClient(), this.appIndexingViewAction);
            this.appIndexingViewAction = null;
        }
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        this.viewPager = null;
        if (this.loadingFromIntentErrorAlertDialog != null && this.loadingFromIntentErrorAlertDialog.isShowing()) {
            this.loadingFromIntentErrorAlertDialog.dismiss();
        }
        super.onDestroyView();
    }

    @Subscribe
    public void onChangeSearchParams(ChangeSearchParamsEvent event) {
        getMainActivity().returnToSearchForm();
    }

    @Subscribe
    public void onBestOfferFindPricesClick(BestOfferFindPricesClickEvent event) {
        showPricesTab();
    }

    private void showPricesTab() {
        int pricesTabPosition = this.tabsAdapter.getPricesTabPosition();
        if (pricesTabPosition >= 0) {
            this.viewPager.setCurrentItem(pricesTabPosition, true);
        }
    }

    @Subscribe
    public void onBestOfferUpdateClick(BestOfferUpdateClickEvent event) {
        showPricesTab();
    }

    @Subscribe
    public void onBestOfferChangeSearchParamsClick(BestOfferChangeSearchParamsClickEvent event) {
        if (this.openInfo.source == Source.FAVORITES || this.openInfo.source == Source.INTENT) {
            showPricesTab();
        } else {
            HotellookApplication.eventBus().post(new ChangeSearchParamsEvent());
        }
    }

    public boolean onBackPressedHandled() {
        if (this.openInfo.source == Source.INTENT) {
            if (this.loadingFromIntentErrorAlertDialog != null && this.loadingFromIntentErrorAlertDialog.isShowing()) {
                this.loadingFromIntentErrorAlertDialog.dismiss();
            }
            getActivity().finish();
            return true;
        } else if (this.bookingObservable != null) {
            this.bookingObservable = null;
            RxUtil.safeUnsubscribe(this.bookingSubscription);
            dismissBookingLoadingDialog();
            return true;
        } else if (this.sharingLoadingDialog != null) {
            this.sharingLoadingDialog.dismiss();
            this.sharingLoadingDialog = null;
            return true;
        } else {
            BasicHotelData basicHotelData = this.hotelDataSource.basicHotelData();
            if (basicHotelData != null) {
                HotellookApplication.eventBus().post(new HotelFragmentCloseEvent(basicHotelData.id()));
            }
            return false;
        }
    }

    @Subscribe
    public void onBookRequest(BookRequestEvent event) {
        if (this.bookingSubscription != null && !this.bookingSubscription.isUnsubscribed()) {
            Timber.m751d("ignoring book request event. prevent", new Object[0]);
        } else if (checkOutOfDateConditions()) {
            showOutOfDateDialog();
        } else {
            Offer offer = event.roomData;
            this.bookingObservable = Observable.combineLatest(createBookingObservable(offer), Observable.timer(1500, TimeUnit.MILLISECONDS), HotelFragment$$Lambda$7.lambdaFactory$());
            showBookingLoadingDialog(offer);
            subscribeToBooking();
            sendOnPurchaseEvent(event);
        }
    }

    static /* synthetic */ Booking lambda$onBookRequest$5(Booking booking, Long timerFinished) {
        return booking;
    }

    @NonNull
    private Observable<Booking> createBookingObservable(Offer offer) {
        SearchParams searchParams = this.hotelDataSource.searchParams();
        RequestFlags requestFlags = this.hotelDataSource.requestFlags();
        return this.hotelDataSource.searchInfoForBooking().flatMap(HotelFragment$$Lambda$8.lambdaFactory$(this, this.hotelDataSource.basicHotelData().id(), searchParams, requestFlags, this.hotelDataSource.cityInfo().getId(), offer)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).cache();
    }

    /* synthetic */ Observable lambda$createBookingObservable$6(long hotelId, SearchParams searchParams, RequestFlags requestFlags, int locationId, Offer offer, SearchInfoForBooking searchInfoForBooking) {
        List<Badge> badgeList = null;
        if (this.badges != null) {
            badgeList = this.badges.getBadges(hotelId);
        }
        return getComponent().bookingSource().observe(searchParams, requestFlags, hotelId, locationId, offer, this.openInfo.source, badgeList, searchInfoForBooking);
    }

    private void showBookingLoadingDialog(Offer offer) {
        Timber.m751d("Booking data loading dialog", new Object[0]);
        Dialogs.showBrowserLoadingDialog(getMainActivity(), offer.getGateName());
    }

    private void subscribeToBooking() {
        this.bookingSubscription = this.bookingObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(HotelFragment$$Lambda$9.lambdaFactory$(this), HotelFragment$$Lambda$10.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$subscribeToBooking$7(Throwable error) {
        onBookingLoadFailed();
    }

    public void onPause() {
        super.onPause();
        RxUtil.safeUnsubscribe(this.bookingSubscription);
    }

    public void onResume() {
        super.onResume();
        if (this.bookingObservable != null) {
            subscribeToBooking();
        }
    }

    private void showOutOfDateDialog() {
        Dialogs.showOutOfDateDialog(getMainActivity(), createNewSearchListener());
        HotellookApplication.eventBus().post(new OutOfDateResultsEvent());
    }

    private void showLoadingHotelDataErrorDialog() {
        this.viewPagerLoader.animate().alpha(0.0f);
        Dialogs.showLoadingIntentErrorDialog(getMainActivity(), createRepeatBasicHotelDataObservableListener(), createCancelHotelSearchListener());
    }

    private DismissDialogListener createCancelHotelSearchListener() {
        return new C13046();
    }

    private DismissDialogListener createRepeatBasicHotelDataObservableListener() {
        return new C13057();
    }

    private DismissDialogListener createNewSearchListener() {
        return new C13068();
    }

    private boolean haveSearchDatesPassed(SearchParams searchParams) {
        Calendar checkIn = searchParams.checkIn();
        Calendar today = DateUtils.getTodayInLastTimezone();
        if (checkIn.get(1) < today.get(1)) {
            return true;
        }
        if (checkIn.get(1) != today.get(1) || checkIn.get(6) >= today.get(6)) {
            return false;
        }
        return true;
    }

    private boolean checkOutOfDateConditions() {
        return System.currentTimeMillis() - this.hotelDataSource.searchStartTimestamp() > 3600000;
    }

    public void onBookingLoadFinished(Booking booking) {
        if (getView() != null) {
            this.bookingObservable = null;
            dismissBookingLoadingDialog();
            openBrowser(booking);
        }
    }

    @Subscribe
    public void onPoiMapFragmentClosed(PoiMapFragmentCloseEvent event) {
        this.underOtherFragment = false;
        getMainActivity().invalidateOptionsMenu();
    }

    @Subscribe
    public void onPoiMapFragmentOpened(PoiMapFragmentOpenEvent event) {
        this.underOtherFragment = true;
        getMainActivity().invalidateOptionsMenu();
    }

    private void openBrowser(Booking booking) {
        if (getActivity() != null) {
            if (VERSION.SDK_INT >= 19) {
                showOverlay(BrowserFragment.create(booking), false);
            } else if (booking.deeplink() == null) {
                showDeeplinkLoadingErrorToast();
            } else {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(booking.deeplink()));
                getActivity().startActivity(intent);
            }
        }
    }

    private void sendOnPurchaseEvent(BookRequestEvent event) {
        HotelDetailData hotelDetailData = this.hotelDataSource.detailHotelData();
        HotellookApplication.eventBus().post(new PurchaseEvent(this.hotelDataSource.basicHotelData(), hotelDetailData, this.userActions, event.searchParams, event.roomData, this.bestOffer, event.openSource, this.openInfo.source, this.tabsAdapter.getTabId(this.viewPager.getCurrentItem()), this.discounts, this.highlights, this.filters, this.badges));
    }

    public void onBookingLoadFailed() {
        if (getView() != null) {
            this.bookingObservable = null;
            dismissBookingLoadingDialog();
            getView().postDelayed(HotelFragment$$Lambda$11.lambdaFactory$(this), 250);
        }
    }

    /* synthetic */ void lambda$onBookingLoadFailed$8() {
        showDeeplinkLoadingErrorToast();
    }

    private void showDeeplinkLoadingErrorToast() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Toasts.showDeeplinkError(activity);
        }
    }

    private void dismissBookingLoadingDialog() {
        Timber.m751d("dismissing booking loading dialog", new Object[0]);
        Fragment dialog = getFragmentManager().findFragmentByTag(BookingLoadingDialogFragment.TAG);
        if (dialog != null) {
            ((DialogFragment) dialog).dismiss();
        }
    }

    @Subscribe
    public void onHotelLinkGenerated(HotelLinkGeneratedEvent event) {
        if (this.sharingLoadingDialog != null) {
            this.sharingLoadingDialog.dismiss();
            this.sharingLoadingDialog = null;
            BasicHotelData basicHotelData = this.hotelDataSource.basicHotelData();
            CityInfo cityInfo = this.hotelDataSource.cityInfo();
            if (basicHotelData != null && cityInfo != null && event.hotelId == basicHotelData.id()) {
                shareHotelViaIntent(event.hotelUrl, basicHotelData.id(), basicHotelData.name(), cityInfo.getId());
            }
        }
    }

    private void shareHotelViaIntent(String hotelUrl, long hotelId, String hotelName, int locationId) {
        StringBuilder sb = new StringBuilder();
        sb.append(hotelName);
        if (CollectionUtils.isNotEmpty(this.hotelDataSource.offers())) {
            Offer bestResult = (Offer) Collections.min(getPrices(), CompareUtils.getComparatorByRoomPrice(this.discounts.get(hotelId), this.highlights.get(hotelId)));
            sb.append(STRING_DELIMITER);
            sb.append(getFormattedPrice(bestResult));
        }
        sb.append(STRING_DELIMITER);
        sb.append(hotelUrl);
        HotellookApplication.eventBus().post(new ShareHotelEvent());
        Intent txtIntent = new Intent("android.intent.action.SEND");
        txtIntent.setType("text/plain");
        txtIntent.putExtra("android.intent.extra.TEXT", sb.toString());
        startActivity(Intent.createChooser(txtIntent, getActivity().getString(C1178R.string.hotel_share_dialog_title)));
    }

    private String getFormattedPrice(Offer bestResult) {
        String currency;
        SearchParams searchParams = this.hotelDataSource.searchParams();
        if (searchParams == null) {
            currency = getComponent().currencyRepository().currencyCode();
        } else {
            currency = searchParams.currency();
        }
        return new CurrencyFormatter(getComponent().currencyRepository()).formatPrice(bestResult.getPrice(), currency);
    }

    @Subscribe
    public void onChildFragmentScrollEvent(HotelScreenChildNewScrollEvent event) {
        if (event.fragmentPositionInViewPager == this.viewPager.getCurrentItem()) {
            this.lastScrollY = event.scrollY;
        }
    }

    private void transformUiOnScroll(int scrollY) {
        this.lastScrollY = scrollY;
        float slowMovingViewsYMotion = ((float) scrollY) * SKIP_FACTOR_FOR_SLOW_REACTING_VIEWS;
        if (scrollY >= 0) {
            this.hotelCard.getPhotoPager().setTranslationY(slowMovingViewsYMotion);
            this.hotelCard.getPhotoPlaceHolder().setTranslationY(slowMovingViewsYMotion);
        } else {
            this.hotelCard.getPhotoPager().setTranslationY(0.0f);
            this.hotelCard.getPhotoPlaceHolder().setTranslationY(0.0f);
        }
        float startHidePoint = ((float) this.maxScrollForLayout) * SKIP_FACTOR_FOR_SLOW_REACTING_VIEWS;
        float collapsingProgress = (((float) scrollY) - startHidePoint) / (((float) this.maxScrollForLayout) - startHidePoint);
        if (((float) scrollY) >= startHidePoint) {
            this.hotelCardOverlay.setVisibility(0);
            this.hotelCardOverlay.setAlpha(collapsingProgress);
            this.hotelCard.getHotelNameLine().setAlpha(1.0f - collapsingProgress);
            this.hotelCard.getRatingBar().setAlpha(1.0f - collapsingProgress);
        } else {
            this.hotelCard.getHotelNameLine().setAlpha(1.0f);
            this.hotelCard.getRatingBar().setAlpha(1.0f);
            this.hotelCardOverlay.setVisibility(8);
        }
        if (AndroidUtils.isLandscape(getActivity()) && scrollY >= 0) {
            this.hotelCardOverlay.setTranslationY((float) scrollY);
            this.toolbarShadow.setY((float) ((AndroidUtils.getStatusBarHeight(getActivity()) + AndroidUtils.getToolbarHeight(getActivity())) + scrollY));
            this.toolbarShadow.setAlpha(((float) scrollY) / ((float) this.maxScrollForLayout));
            float skipLimit = ((float) this.maxScrollForLayout) * SKIP_FACTOR_FOR_SLOW_REACTING_VIEWS;
            if (((float) scrollY) >= skipLimit) {
                this.hotelCard.setAlpha(1.0f - ((((float) scrollY) - skipLimit) / (((float) this.maxScrollForLayout) - skipLimit)));
            } else {
                this.hotelCard.setAlpha(1.0f);
            }
            this.photoControls.setAlpha(1.0f - (((float) scrollY) / ((float) ((this.maxScrollForLayout - AndroidUtils.getStatusBarHeight(getContext())) - AndroidUtils.getToolbarHeight(getContext())))));
        }
        if (this.toolbarTitle != null) {
            int toolbarTitleY = AndroidUtils.getViewYLocationOnScreen(this.toolbarTitle);
            int hotelCardNameY = this.hotelCard.getHotelNameYLocationOnScreen();
            if (!this.hotelCardCollapsed && this.toolbarTitle.getHeight() + toolbarTitleY > hotelCardNameY) {
                this.toolbarTitle.animate().alpha(1.0f);
                this.hotelCard.getHideableContent().animate().alpha(0.0f);
                this.hotelCardCollapsed = true;
            } else if (this.hotelCardCollapsed && this.toolbarTitle.getHeight() + toolbarTitleY < hotelCardNameY) {
                this.toolbarTitle.animate().alpha(0.0f);
                this.hotelCard.getHideableContent().animate().alpha(1.0f);
                this.hotelCardCollapsed = false;
            }
        }
    }

    @Subscribe
    public void onHotelScreenScrollEvent(HotelScreenScrollEvent event) {
        transformUiOnScroll(event.scrollY);
    }

    @Subscribe
    public void onPhotoSelected(OnPhotoSelectedEvent event) {
        if (this.hotelCard != null && this.hotelCard.getPhotoPager() != null && this.hotelCard.getPhotoPager().getAdapter() != null) {
            this.userActions.setShownPhoto(event.photoIdx);
            setUpControlsOnPageSelected(event.photoIdx);
        }
    }

    @Subscribe
    public void onPhotoClick(OnPhotoClickEvent event) {
        launchGalleryScreen(event.photoIdx);
    }

    @Subscribe
    public void onGalleryItemClicked(GalleryGridItemClickEvent event) {
        launchGalleryScreen(event.index, event.transitionData);
    }

    @Subscribe
    public void onShowStreetView(StreetViewBtnClickEvent event) {
        BasicHotelData basicHotelData = this.hotelDataSource.basicHotelData();
        showOverlay(StreetViewFragment.create(basicHotelData.location(), basicHotelData.name()), false);
    }

    @Subscribe
    public void onHotelMap(HotelMapClickEvent event) {
        CityInfo cityInfo = this.hotelDataSource.cityInfo();
        BasicHotelData basicHotelData = this.hotelDataSource.basicHotelData();
        HotelDetailData hotelDetailData = this.hotelDataSource.detailHotelData();
        if (cityInfo != null && basicHotelData != null && hotelDetailData != null) {
            getMainActivity().showFragment(MapFragment.create(hotelDetailData, cityInfo, locationFromSearch(), HotelGeneralFragment.GOOD_LOOKING_ZOOM_LEVEL));
            HotellookApplication.eventBus().post(new HotelMapOpenedEvent(basicHotelData.id()));
        }
    }

    @Nullable
    private Location locationFromSearch() {
        if (isSearchByHotel()) {
            return null;
        }
        SearchParams searchParams = this.hotelDataSource.searchParams();
        if (searchParams != null) {
            return searchParams.location();
        }
        return null;
    }

    private boolean isSearchByHotel() {
        return this.openInfo.source == Source.HOTEL_SEARCH || this.openInfo.source == Source.INTENT;
    }

    @Subscribe
    public void onOverlayClosed(OverlayClosedEvent event) {
        this.underOtherFragment = false;
        setUpToolbar(this.hotelDataSource.basicHotelData());
    }

    public void showOverlay(@NonNull Fragment fragment, boolean instantly) {
        this.underOtherFragment = true;
        getMainActivity().showOverlay(fragment, instantly);
    }

    public void setOpenInfo(HotelScreenOpenInfo openInfo) {
        this.openInfo = openInfo;
    }

    @Nullable
    public Object takeSnapshot() {
        int i;
        if (this.hotelCard == null) {
            i = 0;
        } else {
            i = this.hotelCard.getCurrentPhotoIdx();
        }
        return new Snapshot(i, this.openInfo, this.lastScrollY, this.maxScrollForLayout, this.underOtherFragment, this.hotelDataSource, this.bookingObservable, this.tabsAdapter != null ? this.tabsAdapter.saveFragmentsStates() : null);
    }

    private void restoreFromSnapshot(@NonNull Snapshot snapshot) {
        this.initImageIndex = snapshot.initImageIndex;
        this.openInfo = snapshot.openInfo;
        this.scrollProgress = snapshot.scrollProgress;
        this.underOtherFragment = snapshot.underOtherFragment;
        this.hotelDataSource = snapshot.hotelDataSource;
        this.bookingObservable = snapshot.bookingObservable;
        this.savedTabsFragmentsStates = snapshot.tabsFragmentsStates;
    }
}
