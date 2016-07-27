package com.hotellook.ui.screen.hotel.prices;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.RequestFlags;
import com.hotellook.api.RequestFlagsGenerator;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.backstack.Savable;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.databinding.FragmentHotelPricesBinding;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.events.BestOfferChangeSearchParamsClickEvent;
import com.hotellook.events.BestOfferUpdateClickEvent;
import com.hotellook.events.ChangeSearchParamsEvent;
import com.hotellook.events.MyHotelRefreshPriceEvent;
import com.hotellook.events.SearchButtonClickEvent;
import com.hotellook.events.SearchFailEvent;
import com.hotellook.events.SearchFormCollapseEvent;
import com.hotellook.events.SearchFormExpandEvent;
import com.hotellook.events.SearchResultsRetryEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.hotel.OffersLoaderStateModel;
import com.hotellook.ui.screen.hotel.Source;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.view.SearchFormView;
import com.hotellook.utils.Animators;
import com.squareup.otto.Subscribe;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pl.charmas.android.reactivelocation.C1822R;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OffersFragment extends BaseFragment implements Savable {
    public static final int SEARCH_FORM_ID = 2131689918;
    private FragmentHotelPricesBinding binding;
    private Animator changingStateAnimator;
    private Set<Integer> expandedItems;
    private FavoritesRepository favoritesRepository;
    private HotelDataSource hotelDataSource;
    private OffersLoaderStateModel offersLoaderStateModel;
    private int placeHolderOffset;
    private RoomsAdapter roomsAdapter;
    private SearchFormData searchFormData;
    private Source source;

    /* renamed from: com.hotellook.ui.screen.hotel.prices.OffersFragment.1 */
    class C13271 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$viewToRemove;

        C13271(View view) {
            this.val$viewToRemove = view;
        }

        public void onAnimationEnd(Animator animation) {
            if (this.val$viewToRemove != null) {
                OffersFragment.this.binding.contentContainer.removeView(this.val$viewToRemove);
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.prices.OffersFragment.2 */
    class C13282 extends MonkeySafeClickListener {
        C13282() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new SearchResultsRetryEvent());
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.prices.OffersFragment.3 */
    class C13293 extends MonkeySafeClickListener {
        C13293() {
        }

        public void onSafeClick(View v) {
            OffersFragment.this.offersLoaderStateModel.onUpdateOutdated();
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.prices.OffersFragment.4 */
    class C13304 extends MonkeySafeClickListener {
        C13304() {
        }

        public void onSafeClick(View v) {
            Source source = OffersFragment.this.offersLoaderStateModel.getSource();
            if (source == Source.FAVORITES || source == Source.INTENT) {
                OffersFragment.this.offersLoaderStateModel.onChangeParams();
            } else {
                HotellookApplication.eventBus().post(new ChangeSearchParamsEvent());
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.prices.OffersFragment.5 */
    static /* synthetic */ class C13315 {
        static final /* synthetic */ int[] f731x4df94459;

        static {
            f731x4df94459 = new int[OffersLoaderState.values().length];
            try {
                f731x4df94459[OffersLoaderState.HAS_ROOMS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f731x4df94459[OffersLoaderState.LOADING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f731x4df94459[OffersLoaderState.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f731x4df94459[OffersLoaderState.NO_ROOMS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f731x4df94459[OffersLoaderState.OUTDATED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f731x4df94459[OffersLoaderState.EMPTY.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f731x4df94459[OffersLoaderState.UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    static class Snapshot {
        final Set<Integer> expandedItems;
        final HotelDataSource hotelDataSource;
        final OffersLoaderStateModel offersLoaderStateModel;
        final SearchFormData searchFormData;
        final Source source;

        Snapshot(OffersLoaderStateModel offersLoaderStateModel, SearchFormData searchFormData, Set<Integer> expandedItems, HotelDataSource hotelDataSource, Source source) {
            this.offersLoaderStateModel = offersLoaderStateModel;
            this.searchFormData = searchFormData;
            this.expandedItems = expandedItems;
            this.hotelDataSource = hotelDataSource;
            this.source = source;
        }
    }

    public OffersFragment() {
        this.expandedItems = new HashSet();
    }

    public static Fragment create(HotelDataSource hotelDataSource, Source source) {
        OffersFragment fragment = new OffersFragment();
        fragment.setHotelDataSource(hotelDataSource);
        fragment.setSource(source);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.favoritesRepository = new FavoritesRepository(getMainActivity().getHelper());
        this.placeHolderOffset = getResources().getDimensionPixelSize(C1178R.dimen.hotel_tab_place_holders_offset);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = (FragmentHotelPricesBinding) DataBindingUtil.inflate(inflater, C1178R.layout.fragment_hotel_prices, container, false);
        HotellookApplication.eventBus().register(this);
        return this.binding.getRoot();
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpSearchFormData();
        if (this.offersLoaderStateModel == null) {
            this.offersLoaderStateModel = new OffersLoaderStateModel(this.hotelDataSource, this.source);
        }
        subscribeToOffersLoadingStates();
        setUpRecyclerView(this.binding.recyclerView);
        setUpAdapter(this.binding.recyclerView);
    }

    private void setUpSearchFormData() {
        if (this.searchFormData == null) {
            SearchParams searchParams = this.hotelDataSource.searchParams();
            if (searchParams == null) {
                this.searchFormData = new SearchFormData(this.hotelDataSource.launchParams(), this.hotelDataSource.basicHotelData(), this.hotelDataSource.cityInfo());
            } else {
                this.searchFormData = new SearchFormData(searchParams, this.hotelDataSource.basicHotelData(), this.hotelDataSource.cityInfo());
            }
        }
    }

    private void subscribeToOffersLoadingStates() {
        keepUntilDestroyView(this.offersLoaderStateModel.observeState().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(OffersFragment$$Lambda$1.lambdaFactory$(this), OffersFragment$$Lambda$2.lambdaFactory$()));
    }

    public void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setPadding(0, getListTopPadding(), 0, getStandardOffset());
        recyclerView.setClipToPadding(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private int getListTopPadding() {
        return this.offersLoaderStateModel.getSource() == Source.FAVORITES ? getStandardOffset() : 0;
    }

    private void setUpAdapter(RecyclerView recyclerView) {
        this.roomsAdapter = new RoomsAdapter(this.hotelDataSource.searchParams(), this.searchFormData, this.expandedItems);
        List<Offer> hotelPrices = this.hotelDataSource.offers();
        if (hotelPrices != null && hotelPrices.size() > 0) {
            setDataForRoomsAdapter(hotelPrices);
        }
        recyclerView.setAdapter(this.roomsAdapter);
    }

    private void showState(OffersLoaderState state) {
        if (getActivity() != null) {
            View viewToHide;
            View viewToShow;
            finishRunningAnimation();
            View contentView = this.binding.contentContainer.getChildAt(0);
            if (state == OffersLoaderState.HAS_ROOMS) {
                viewToHide = contentView;
            } else if (contentView == null || contentView.getVisibility() != 0) {
                viewToHide = this.binding.recyclerView;
            } else {
                viewToHide = contentView;
            }
            View viewToRemove = contentView;
            switch (C13315.f731x4df94459[state.ordinal()]) {
                case C1822R.styleable.SignInButton_colorScheme /*1*/:
                    setUpAdapter(this.binding.recyclerView);
                    viewToShow = this.binding.recyclerView;
                    break;
                case C1822R.styleable.SignInButton_scopeUris /*2*/:
                    viewToShow = inflateLoadingView();
                    addViewToContent(viewToShow, true);
                    break;
                case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                    viewToShow = inflateErrorView();
                    addViewToContent(viewToShow, true);
                    break;
                case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                    viewToShow = inflateNoResultsView();
                    addViewToContent(viewToShow, true);
                    break;
                case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                    viewToShow = inflateOutdatedView();
                    addViewToContent(viewToShow, true);
                    break;
                case C1822R.styleable.MapAttrs_liteMode /*6*/:
                    viewToShow = inflateSearchFormView();
                    addViewToContent(viewToShow, false);
                    break;
                case C1822R.styleable.MapAttrs_uiCompass /*7*/:
                    viewToShow = inflateLoadingView();
                    addViewToContent(viewToShow, true);
                    break;
                default:
                    throw new RuntimeException("No such state known: " + state.name());
            }
            animateChangingState(viewToShow, viewToHide, viewToRemove);
        }
    }

    private void finishRunningAnimation() {
        if (this.changingStateAnimator != null && this.changingStateAnimator.isRunning()) {
            this.changingStateAnimator.end();
        }
    }

    private void animateChangingState(View viewToShow, View viewToHide, View viewToRemove) {
        if (viewToHide == null) {
            animateShow(viewToShow);
        } else {
            animateShowWithCrossFade(viewToShow, viewToHide, viewToRemove);
        }
    }

    private void animateShowWithCrossFade(View viewToShow, View viewToHide, View viewToRemove) {
        this.changingStateAnimator = Animators.createCrossFadeAnimator(viewToShow, viewToHide, 8);
        this.changingStateAnimator.addListener(new C13271(viewToRemove));
        this.changingStateAnimator.start();
    }

    private void animateShow(View viewToShow) {
        viewToShow.setVisibility(0);
        viewToShow.setAlpha(0.0f);
        this.changingStateAnimator = ObjectAnimator.ofFloat(viewToShow, View.ALPHA, new float[]{0.0f, 1.0f});
        this.changingStateAnimator.start();
    }

    private View inflateErrorView() {
        View errorView = LayoutInflater.from(getActivity()).inflate(C1178R.layout.view_hotel_info_error, this.binding.contentContainer, false);
        errorView.findViewById(C1178R.id.btn_retry).setOnClickListener(new C13282());
        return errorView;
    }

    private View inflateOutdatedView() {
        View outDatedView = LayoutInflater.from(getActivity()).inflate(C1178R.layout.hotel_prices_old_results_layout, this.binding.contentContainer, false);
        outDatedView.findViewById(C1178R.id.btn_update).setOnClickListener(new C13293());
        return outDatedView;
    }

    private void addViewToContent(View view, boolean addTopOffset) {
        this.binding.contentContainer.addView(view);
        if (addTopOffset) {
            ((MarginLayoutParams) view.getLayoutParams()).topMargin = this.placeHolderOffset;
        }
    }

    private SearchFormView inflateSearchFormView() {
        SearchFormView searchFormView = (SearchFormView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.hotel_embedded_search_form, this.binding.contentContainer, false);
        searchFormView.setUpData(this.searchFormData);
        return searchFormView;
    }

    private View inflateLoadingView() {
        return LayoutInflater.from(getActivity()).inflate(C1178R.layout.hotel_prices_loading_layout, this.binding.contentContainer, false);
    }

    private View inflateNoResultsView() {
        View noResultsView = LayoutInflater.from(getActivity()).inflate(C1178R.layout.hotel_prices_no_results_layout, this.binding.contentContainer, false);
        noResultsView.findViewById(C1178R.id.btn_change_params).setOnClickListener(new C13304());
        return noResultsView;
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    private void newSearch(SearchParams params, RequestFlags requestFlags) {
        this.hotelDataSource.updateOffers(params, requestFlags);
        postSearchStartEvent(params);
    }

    public void setDataForRoomsAdapter(List<Offer> pricesForHotel) {
        this.roomsAdapter.setData(this.hotelDataSource.basicHotelData().id(), pricesForHotel, this.hotelDataSource.roomTypes(), isListWithSearchForm(), this.hotelDataSource.discounts(), this.hotelDataSource.highlights());
    }

    private boolean isListWithSearchForm() {
        return this.offersLoaderStateModel.getSource() == Source.FAVORITES;
    }

    private void postSearchStartEvent(SearchParams searchParams) {
        HotellookApplication.eventBus().post(new SearchStartEvent(searchParams, Constants.Source.MY_HOTELS, null));
    }

    @Subscribe
    public void onSearchResultsFailed(SearchFailEvent event) {
        this.offersLoaderStateModel.onSearchFailed();
    }

    @Subscribe
    public void onSearchRetry(SearchResultsRetryEvent event) {
        newSearch(this.hotelDataSource.searchParams(), this.hotelDataSource.requestFlags());
    }

    @Subscribe
    public void onSearchButtonClickEvent(SearchButtonClickEvent event) {
        SearchParams params = event.params;
        newSearch(params, new RequestFlagsGenerator().searchFromHotelScreenSearchForm(params));
        this.favoritesRepository.update(this.hotelDataSource.basicHotelData(), this.hotelDataSource.cityInfo(), params);
        HotellookApplication.eventBus().post(new MyHotelRefreshPriceEvent(params));
    }

    @Subscribe
    public void onSearchStarted(SearchStartEvent event) {
        this.offersLoaderStateModel.onSearchStarted();
    }

    @Subscribe
    public void onSearchFormExpandEvent(SearchFormExpandEvent event) {
        this.expandedItems.add(Integer.valueOf(SEARCH_FORM_ID));
    }

    @Subscribe
    public void onSearchFormCollapseEvent(SearchFormCollapseEvent event) {
        this.expandedItems.remove(Integer.valueOf(SEARCH_FORM_ID));
    }

    @Subscribe
    public void onBestOfferChangeSearchParamsClick(BestOfferChangeSearchParamsClickEvent event) {
        this.offersLoaderStateModel.onChangeSearchParams();
    }

    @Subscribe
    public void onBestOfferUpdateClick(BestOfferUpdateClickEvent event) {
        this.offersLoaderStateModel.onUpdateOutdated();
    }

    public void setHotelDataSource(HotelDataSource hotelDataSource) {
        this.hotelDataSource = hotelDataSource;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @NonNull
    public Object saveState() {
        return new Snapshot(this.offersLoaderStateModel, this.searchFormData, this.expandedItems, this.hotelDataSource, this.source);
    }

    public void restoreState(@NonNull Object state) {
        Snapshot snapshot = (Snapshot) state;
        this.source = snapshot.source;
        this.expandedItems = snapshot.expandedItems;
        this.searchFormData = snapshot.searchFormData;
        this.hotelDataSource = snapshot.hotelDataSource;
        this.offersLoaderStateModel = snapshot.offersLoaderStateModel;
    }
}
