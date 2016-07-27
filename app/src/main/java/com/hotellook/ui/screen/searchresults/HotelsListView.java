package com.hotellook.ui.screen.searchresults;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.backstack.Savable;
import com.hotellook.badges.Badges;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.events.FilteringFinishedEvent;
import com.hotellook.events.FiltersResetEvent;
import com.hotellook.events.RemoveCardEvent;
import com.hotellook.events.RestartCitySearchEvent;
import com.hotellook.events.ResultsScrolledToTopEvent;
import com.hotellook.events.ShowEnGatesQuestionDialogEvent;
import com.hotellook.filters.Filters;
import com.hotellook.search.Discounts;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.Highlights;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.Search;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.dialog.DismissDialogListener;
import com.hotellook.ui.screen.Dialogs;
import com.hotellook.ui.screen.hotel.Source;
import com.hotellook.ui.screen.searchresults.adapters.HotelsAdapter;
import com.hotellook.ui.screen.searchresults.adapters.ListItemLayoutFactory;
import com.hotellook.ui.screen.searchresults.adapters.animations.HotelListItemAnimator;
import com.hotellook.ui.screen.searchresults.adapters.cards.HotelsAdapterWithCards;
import com.hotellook.ui.screen.searchresults.adapters.cards.controller.Cards;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.LayoutManagerWrapper;
import com.hotellook.ui.view.placeholder.PlaceHolder;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.EventBus;
import com.hotellook.utils.ViewUtils;
import com.squareup.otto.Subscribe;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class HotelsListView extends FrameLayout implements Savable, Sourceable {
    public static final int UPDATE_LIST_DELAY = 410;
    private Badges badges;
    private Cards cards;
    private EventBus eventBus;
    private Filters filters;
    private HotelsAdapterWithCards hotelsAdapter;
    private RecyclerView hotelsRecycleView;
    private LayoutManagerWrapper layoutManager;
    private PlaceHolder placeHolder;
    private Search search;
    private SearchParams searchParams;
    private Snapshot snapshot;

    /* renamed from: com.hotellook.ui.screen.searchresults.HotelsListView.1 */
    class C13541 extends MonkeySafeClickListener {
        C13541() {
        }

        public void onSafeClick(View v) {
            HotelsListView.this.filters.reset();
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.HotelsListView.2 */
    class C13552 extends OnScrollListener {
        private boolean scrolled;

        C13552() {
            this.scrolled = false;
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
            if (scrollState == 0 || scrollState == 1) {
                HotelsListView.this.hotelsAdapter.enablePrecache(HotelsListView.this.layoutManager.findFirstVisibleItemPosition(), HotelsListView.this.layoutManager.findLastVisibleItemPosition());
            } else {
                HotelsListView.this.hotelsAdapter.disablePrecache();
            }
            if (!this.scrolled && scrollState != 0) {
                this.scrolled = true;
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.HotelsListView.3 */
    class C13563 extends DismissDialogListener {
        C13563() {
        }

        public void onSafeClick(View v) {
            super.onSafeClick(v);
            HotellookApplication.eventBus().post(new RestartCitySearchEvent());
        }
    }

    static class Snapshot {
        final int filtersHashcode;
        final Parcelable listState;
        final SparseIntArray pageIndexes;

        Snapshot(Parcelable listState, SparseIntArray pageIndexes, int filtersHashcode) {
            this.listState = listState;
            this.pageIndexes = pageIndexes;
            this.filtersHashcode = filtersHashcode;
        }
    }

    public HotelsListView(Context context) {
        super(context);
    }

    public HotelsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HotelsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            HotellookComponent component = HotellookApplication.from(getContext()).getComponent();
            this.search = component.searchKeeper().lastSearchOrThrowException();
            this.searchParams = this.search.searchParams();
            this.filters = this.search.filters();
            this.badges = this.search.badges();
            this.eventBus = component.eventBus();
            this.hotelsRecycleView = (RecyclerView) findViewById(C1178R.id.rv_hotels);
            this.layoutManager = ListItemLayoutFactory.createLayoutManager(getContext());
            this.hotelsRecycleView.setHasFixedSize(true);
            this.hotelsRecycleView.setLayoutManager(this.layoutManager.get());
            this.placeHolder = (PlaceHolder) findViewById(C1178R.id.place_holder);
            this.placeHolder.setOnButtonClickListener(new C13541());
            setUpList();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            HotellookApplication.eventBus().register(this);
        }
    }

    protected void onDetachedFromWindow() {
        HotellookApplication.eventBus().unregister(this);
        super.onDetachedFromWindow();
    }

    private void setUpList() {
        int nights = this.searchParams.nightsCount();
        List<HotelData> hotels = this.filters.getSortedHotels();
        Map<Long, List<Offer>> searchResults = this.filters.getFilteredOffers();
        Discounts discounts = extractDisctounts();
        Highlights highlights = extractHighlight();
        this.hotelsAdapter = new HotelsAdapterWithCards(HotelsAdapter.newBuilder().activity((Activity) getContext()).hotelData(hotels).searchResult(searchResults).bestResultDataCache(calculateBestResults(hotels, searchResults, discounts, highlights)).currency(this.searchParams.currency()).nightsCount(nights).badges(this.badges).discounts(discounts).highlights(highlights).build());
        checkFiltersChanged();
        this.hotelsRecycleView.setAdapter(this.hotelsAdapter);
        this.hotelsRecycleView.setHasFixedSize(true);
        this.hotelsRecycleView.setItemAnimator(new HotelListItemAnimator());
        this.hotelsRecycleView.addItemDecoration(new HotelItemDecoration(getContext().getResources()));
        this.hotelsRecycleView.addOnScrollListener(new C13552());
        this.cards = this.search.cards();
        this.hotelsAdapter.updateCards(this.cards.getCards());
        restoreState();
        updateListAndPlaceholderVisibility();
        if (AndroidUtils.isPhone(getContext())) {
            AndroidUtils.addPaddingToOffsetToolbar(this.hotelsRecycleView);
        }
        this.hotelsRecycleView.requestLayout();
    }

    private Highlights extractHighlight() {
        if (this.search == null || this.search.searchData() == null || this.search.searchData().highlights() == null) {
            return new Highlights(Collections.emptyMap());
        }
        return this.search.searchData().highlights();
    }

    @NonNull
    private Discounts extractDisctounts() {
        if (this.search == null || this.search.searchData() == null || this.search.searchData().discounts() == null) {
            return new Discounts(Collections.emptyMap());
        }
        return this.search.searchData().discounts();
    }

    private void restoreState() {
        if (this.snapshot != null) {
            this.layoutManager.onRestoreInstanceState(this.snapshot.listState, this.hotelsRecycleView);
            this.hotelsAdapter.setPagersIndexes(this.snapshot.pageIndexes);
        }
    }

    private void updateListAndPlaceholderVisibility() {
        if (this.hotelsAdapter.getItemCount() == 0) {
            ViewUtils.hideView(this.hotelsRecycleView);
            ViewUtils.showView(this.placeHolder);
            return;
        }
        ViewUtils.showView(this.hotelsRecycleView);
        ViewUtils.hideView(this.placeHolder);
    }

    private void updateList() {
        List<HotelData> hotels = this.filters.getSortedHotels();
        Discounts discounts = extractDisctounts();
        Highlights highlights = this.search.searchData().highlights();
        Map<Long, List<Offer>> searchResults = this.filters.getFilteredOffers();
        Map<Long, Offer> bestResults = calculateBestResults(hotels, searchResults, discounts, highlights);
        int prevCount = this.hotelsAdapter.getItemCount();
        this.hotelsAdapter.notifyCardsUpdate();
        TreeSet<Integer> notChangedIndexes = this.hotelsAdapter.updateCards(this.cards.getCards());
        this.hotelsAdapter.update(hotels, searchResults, bestResults);
        updateListAndPlaceholderVisibility();
        if (!AndroidUtils.isTablet(getContext())) {
            animateChanges(prevCount, notChangedIndexes);
        } else if (this.hotelsAdapter.getItemCount() == 0) {
            this.hotelsAdapter.notifyDataSetChanged();
        } else {
            this.hotelsAdapter.notifyItemRangeRemoved(0, prevCount);
        }
        if (this.hotelsAdapter.getItemCount() != 0) {
            this.layoutManager.scrollToTop();
            post(HotelsListView$$Lambda$1.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$updateList$0() {
        this.eventBus.post(new ResultsScrolledToTopEvent());
    }

    private void animateChanges(int prevCount, Set<Integer> notChangedIndexes) {
        if (notChangedIndexes.isEmpty() || this.hotelsAdapter.getItemCount() == 0) {
            this.hotelsAdapter.notifyDataSetChanged();
            return;
        }
        int prevIndex = prevCount;
        for (int i = prevCount; i >= 0; i--) {
            if (notChangedIndexes.contains(Integer.valueOf(i))) {
                if (prevIndex > i) {
                    this.hotelsAdapter.notifyItemRangeRemoved(i + 1, prevIndex - i);
                }
                prevIndex = i;
            }
        }
    }

    private void checkFiltersChanged() {
        if (this.snapshot != null && this.snapshot.filtersHashcode != this.filters.hashCode()) {
            this.snapshot = null;
        }
    }

    @Subscribe
    public void onCardRemoved(RemoveCardEvent event) {
        this.hotelsAdapter.removeCard(event.position);
        this.hotelsAdapter.notifyItemRemoved(event.position);
    }

    @Subscribe
    public void onShowENGatesDialog(ShowEnGatesQuestionDialogEvent event) {
        Dialogs.showEnGatesDialog((FragmentActivity) getContext(), new C13563());
    }

    @Subscribe
    public void onFiltered(FilteringFinishedEvent event) {
        this.snapshot = null;
        updateList();
    }

    @Subscribe
    public void onFiltersReset(FiltersResetEvent event) {
        this.snapshot = null;
        updateList();
    }

    private Map<Long, Offer> calculateBestResults(List<HotelData> hotels, Map<Long, List<Offer>> searchResults, Discounts discounts, Highlights highlights) {
        Map<Long, Offer> bestResults = new HashMap();
        for (HotelData hotel : hotels) {
            long id = hotel.getId();
            bestResults.put(Long.valueOf(id), getMinimumPrice((List) searchResults.get(Long.valueOf(id)), discounts.get(id), highlights.get(id)));
        }
        return bestResults;
    }

    @Nullable
    private Offer getMinimumPrice(@Nullable List<Offer> offers, DiscountsByRooms discountsByRooms, HighlightsByRooms highlightsByRooms) {
        if (offers == null) {
            return null;
        }
        return (Offer) Collections.min(offers, CompareUtils.getComparatorByRoomPrice(discountsByRooms, highlightsByRooms));
    }

    public Source getSource() {
        return Source.CITY_SEARCH_RESULTS;
    }

    public RecyclerView recyclerView() {
        return this.hotelsRecycleView;
    }

    @NonNull
    public Object saveState() {
        return new Snapshot(this.layoutManager.onSaveInstanceState(), this.hotelsAdapter.getPagersIndexes(), this.filters.hashCode());
    }

    public void restoreState(@NonNull Object state) {
        this.snapshot = (Snapshot) state;
        restoreState();
    }
}
