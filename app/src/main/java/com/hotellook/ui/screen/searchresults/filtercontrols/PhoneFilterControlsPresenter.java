package com.hotellook.ui.screen.searchresults.filtercontrols;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.hotellook.C1178R;
import com.hotellook.events.OpenFiltersEvent;
import com.hotellook.events.OpenSortingMenuEvent;
import com.hotellook.filters.Filters;
import com.hotellook.search.SearchData;
import com.hotellook.ui.screen.filters.FilterOpenSource;
import com.hotellook.ui.screen.gallery.ViewSizeCalculator;
import com.hotellook.ui.screen.searchresults.HotelsListView;
import com.hotellook.ui.screen.searchresults.QuickFiltersPresenter;
import com.hotellook.ui.screen.searchresults.ViewHorizontalAligner;
import com.hotellook.ui.screen.searchresults.adapters.cards.controller.Cards;
import com.hotellook.ui.view.viewmovers.ScrollableViewsSynchronizer;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.EventBus;
import com.hotellook.utils.ViewUtils;

public class PhoneFilterControlsPresenter implements FilterControlsPresenter {
    public static final int ANIMATION_DURATION = 300;
    @BindView
    View btnMapFilter;
    @BindView
    View btnReset;
    @BindView
    View btnSorting;
    private final Cards cards;
    private final EventBus eventBus;
    private final Filters filters;
    @BindView
    View filtersOnIcon;
    private final boolean hasGMS;
    @BindView
    View listControlsRoot;
    @BindView
    View mapFiltersOnIcon;
    @Nullable
    @BindView
    View quickFiltersContainer;
    private QuickFiltersPresenter quickFiltersPresenter;
    @Nullable
    @BindView
    View quickFiltersShadow;
    private final SearchData searchData;
    private Unbinder unbinder;

    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.PhoneFilterControlsPresenter.1 */
    class C13821 implements OnGlobalLayoutListener {
        final /* synthetic */ View val$view;

        C13821(View view) {
            this.val$view = view;
        }

        public void onGlobalLayout() {
            HotelsListView hotelsListView = (HotelsListView) this.val$view.findViewById(C1178R.id.results_view);
            AndroidUtils.removeOnGlobalLayoutListener(this.val$view, this);
            ScrollableViewsSynchronizer.with(hotelsListView.recyclerView()).addViewToTranslateAsync(PhoneFilterControlsPresenter.this.listControlsRoot, PhoneFilterControlsPresenter.this.listControlsRoot, this.val$view.getResources().getDimensionPixelSize(C1178R.dimen.default_toolbar_height));
        }
    }

    public PhoneFilterControlsPresenter(Filters filters, Cards cards, SearchData searchData, EventBus eventBus, boolean hasGMS) {
        this.filters = filters;
        this.cards = cards;
        this.searchData = searchData;
        this.eventBus = eventBus;
        this.hasGMS = hasGMS;
    }

    @OnClick
    public void onSortBtnClicked() {
        this.eventBus.post(new OpenSortingMenuEvent(this.filters.getSortingCategory()));
    }

    @OnClick
    public void onFilterBtnClicked() {
        this.eventBus.post(new OpenFiltersEvent(FilterOpenSource.LIST));
    }

    @OnClick
    public void onResetBtnClicked() {
        this.filters.reset();
    }

    @OnClick
    public void onFilterBtnMapClicked() {
        this.eventBus.post(new OpenFiltersEvent(FilterOpenSource.LIST));
    }

    public void attachView(View view) {
        this.unbinder = ButterKnife.bind(this, view);
        this.quickFiltersPresenter = new QuickFiltersPresenter(this.filters);
        if (this.quickFiltersContainer != null) {
            this.quickFiltersPresenter.addView(LayoutInflater.from(view.getContext()), (ViewGroup) this.quickFiltersContainer);
            AndroidUtils.addMarginToPlaceViewBelowToolbar(this.quickFiltersContainer);
        }
        setUpFiltersButtonStateInstantly(view);
        new ViewHorizontalAligner(view.getContext(), getFiltersControlsWidth(view.getContext())).alignWithPaddings(this.listControlsRoot);
        view.setVisibility(areConditionsToShowFilterUiMet() ? 0 : 8);
        this.eventBus.register(this);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new C13821(view));
        setFiltersIconVisibility();
    }

    public void onDetachFromView() {
        this.unbinder.unbind();
        this.eventBus.unregister(this);
    }

    private boolean areConditionsToShowFilterUiMet() {
        return hasSomethingToFilter();
    }

    private int getFiltersControlsWidth(Context context) {
        return new ViewSizeCalculator(context).calculateControlsWidth();
    }

    public void onFiltersChanged() {
        setFiltersIconVisibility();
        if (resultsAreEmpty()) {
            ViewUtils.goneView(this.btnSorting);
            ViewUtils.showView(this.btnReset);
        } else {
            ViewUtils.goneView(this.btnReset);
            ViewUtils.showView(this.btnSorting);
        }
        this.quickFiltersPresenter.onFiltersUpdated();
    }

    private void setFiltersIconVisibility() {
        int i;
        int i2 = 8;
        View view = this.filtersOnIcon;
        if (this.filters.inInittedState()) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        View view2 = this.mapFiltersOnIcon;
        if (!this.filters.inInittedState()) {
            i2 = 0;
        }
        view2.setVisibility(i2);
    }

    public void switchToListControls(boolean instantly) {
        if (hasSomethingToFilter()) {
            ViewUtils.showView(this.listControlsRoot, instantly);
        }
        hideQuickFilters(instantly);
        ViewUtils.hideView(this.btnMapFilter, instantly);
    }

    public void switchToMapControls(boolean instantly) {
        ViewUtils.goneView(this.listControlsRoot, instantly);
        if (hasSomethingToFilter()) {
            showQuickFilters(instantly);
            ViewUtils.showView(this.btnMapFilter, instantly);
        }
    }

    public Object saveState() {
        return null;
    }

    public void restoreState(Object object) {
    }

    public int additionalTopOffsetForMap() {
        if (this.quickFiltersContainer == null) {
            return 0;
        }
        this.quickFiltersContainer.measure(-1, -2);
        return this.quickFiltersContainer.getMeasuredHeight() / 2;
    }

    private void setUpFiltersButtonStateInstantly(View view) {
        view.findViewById(C1178R.id.filters_on).setVisibility(this.filters.inInittedState() ? 8 : 0);
        if (resultsAreEmpty()) {
            this.btnSorting.setVisibility(8);
            this.btnReset.setVisibility(0);
            return;
        }
        this.btnSorting.setVisibility(0);
        this.btnReset.setVisibility(8);
    }

    private boolean resultsAreEmpty() {
        return CollectionUtils.isEmpty(this.filters.getSortedHotels()) && CollectionUtils.isEmpty(this.cards.getCards());
    }

    private boolean hasSomethingToFilter() {
        return this.searchData.hotels().all().size() > 1;
    }

    private void hideQuickFilters(boolean instantly) {
        if (this.quickFiltersContainer != null) {
            ViewUtils.hideView(this.quickFiltersContainer, instantly);
            ViewUtils.hideView(this.quickFiltersShadow, instantly);
        }
    }

    private void showQuickFilters(boolean instantly) {
        if (this.quickFiltersContainer != null && this.hasGMS) {
            ViewUtils.showView(this.quickFiltersContainer, instantly);
            ViewUtils.showView(this.quickFiltersShadow, instantly);
        }
    }
}
