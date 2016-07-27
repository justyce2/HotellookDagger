package com.hotellook.ui.screen.searchresults.filtercontrols;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.DestinationInSortingSpinnerClickedEvent;
import com.hotellook.events.FiltersSortingChangedEvent;
import com.hotellook.events.OpenDistanceSelectorEvent;
import com.hotellook.events.OpenRightDrawerEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.items.SortingItem;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.EventBus;
import com.hotellook.utils.ViewUtils;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class TabletFiltersControlsPresenter implements FilterControlsPresenter {
    private SortingTypeSpinnerAdapter adapter;
    @BindView
    View container;
    private final EventBus eventBus;
    private final Filters filters;
    @Nullable
    @BindView
    View filtersOnIcon;
    private final FiltersWorkArownds filtersWorkarownds;
    @BindView
    View sortingShadow;
    @BindView
    Spinner spinner;
    @BindView
    View spinnerContainer;
    private ArrayList<SortingSpinnerItem> spinnerItems;
    @Nullable
    @BindView
    View toolbarSortingShadow;
    private Unbinder unbinder;

    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.TabletFiltersControlsPresenter.1 */
    class C13841 extends MonkeySafeClickListener {
        C13841() {
        }

        public void onSafeClick(View v) {
            TabletFiltersControlsPresenter.this.eventBus.post(new OpenRightDrawerEvent());
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.TabletFiltersControlsPresenter.2 */
    class C13852 implements OnItemSelectedListener {
        final /* synthetic */ SortingItem val$sortingItem;

        C13852(SortingItem sortingItem) {
            this.val$sortingItem = sortingItem;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            TabletFiltersControlsPresenter.this.adapter.setSelectedPosition(position);
            this.val$sortingItem.setAlgoId(((SortingSpinnerItem) TabletFiltersControlsPresenter.this.spinnerItems.get(position)).id);
            HotellookApplication.eventBus().post(new FiltersSortingChangedEvent(this.val$sortingItem));
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public TabletFiltersControlsPresenter(Filters filters, EventBus eventBus, FiltersWorkArownds filtersWorkarownds) {
        this.filters = filters;
        this.eventBus = eventBus;
        this.filtersWorkarownds = filtersWorkarownds;
    }

    public void attachView(View view) {
        this.unbinder = ButterKnife.bind(this, view);
        setUpFiltersOnIcon();
        setUpSortingSpinner();
        View filterBtn = view.findViewById(C1178R.id.btn_filter);
        if (filterBtn != null) {
            filterBtn.setOnClickListener(new C13841());
        }
        AndroidUtils.addMarginToPlaceViewBelowToolbar(this.container);
        if (this.toolbarSortingShadow != null) {
            AndroidUtils.addMarginToPlaceViewBelowToolbar(this.toolbarSortingShadow);
        }
        this.eventBus.register(this);
    }

    public void onDetachFromView() {
        this.eventBus.unregister(this);
        this.unbinder.unbind();
    }

    @Subscribe
    public void onOpenDestinationPicker(DestinationInSortingSpinnerClickedEvent event) {
        this.filtersWorkarownds.closeSpinner(this.spinner);
        this.spinner.post(TabletFiltersControlsPresenter$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onOpenDestinationPicker$0() {
        this.eventBus.post(new OpenDistanceSelectorEvent(0));
    }

    public void onFiltersChanged() {
        setUpFiltersOnIcon();
        setUpSortingSpinner();
    }

    public void switchToListControls(boolean instantly) {
        if (AndroidUtils.isPortrait(this.spinnerContainer.getContext())) {
            ViewUtils.showView(this.spinnerContainer, instantly);
            return;
        }
        ViewUtils.showView(this.container, instantly);
        ViewUtils.showView(this.sortingShadow, instantly);
        ViewUtils.hideView(this.toolbarSortingShadow, instantly);
    }

    public void switchToMapControls(boolean instantly) {
        if (AndroidUtils.isPortrait(this.spinnerContainer.getContext())) {
            ViewUtils.hideView(this.spinnerContainer, instantly);
            return;
        }
        ViewUtils.hideView(this.container, instantly);
        ViewUtils.hideView(this.sortingShadow, instantly);
        ViewUtils.showView(this.toolbarSortingShadow, instantly);
    }

    public Object saveState() {
        return null;
    }

    public void restoreState(Object object) {
    }

    public int additionalTopOffsetForMap() {
        return 0;
    }

    private void setUpSortingSpinner() {
        this.spinner.setOnItemSelectedListener(null);
        SortingItem sortingItem = this.filters.getSortingCategory().getSortingItem();
        Resources resources = this.spinner.getResources();
        this.spinnerItems = new ArrayList();
        this.spinnerItems.add(new SortingSpinnerItem(C1178R.id.sorting_popularity, resources.getString(C1178R.string.popularity)));
        this.spinnerItems.add(new SortingSpinnerItem(C1178R.id.sorting_ratings, resources.getString(C1178R.string.guest_ratings)));
        this.spinnerItems.add(new SortingSpinnerItem(C1178R.id.sorting_price, resources.getString(C1178R.string.price)));
        DistanceTarget distanceTarget = sortingItem.getDistanceTarget();
        this.spinnerItems.add(new DistanceSortingSpinnerItem(C1178R.id.sorting_distance, resources.getString(C1178R.string.distance_from), distanceTarget.getTitle()));
        if (sortingItem.hasDiscounts()) {
            this.spinnerItems.add(new SortingSpinnerItem(C1178R.id.sorting_discount, resources.getString(C1178R.string.discount_sorting)));
        }
        this.adapter = new SortingTypeSpinnerAdapter(this.spinner.getContext(), this.spinnerItems);
        this.spinner.setAdapter(this.adapter);
        int selectedPosition = findSelectedPosition(this.spinnerItems, sortingItem.getAlgoId());
        this.spinner.setSelection(selectedPosition);
        this.adapter.setSelectedPosition(selectedPosition);
        this.spinner.post(TabletFiltersControlsPresenter$$Lambda$2.lambdaFactory$(this, sortingItem));
    }

    /* synthetic */ void lambda$setUpSortingSpinner$1(SortingItem sortingItem) {
        setUpSpinnerItemSelectedListener(sortingItem);
    }

    private void setUpSpinnerItemSelectedListener(SortingItem sortingItem) {
        if (this.spinner != null) {
            this.spinner.setOnItemSelectedListener(new C13852(sortingItem));
        }
    }

    private int findSelectedPosition(List<SortingSpinnerItem> spinnerItems, int algoId) {
        for (int i = 0; i < spinnerItems.size(); i++) {
            if (algoId == ((SortingSpinnerItem) spinnerItems.get(i)).id) {
                return i;
            }
        }
        return 0;
    }

    private void setUpFiltersOnIcon() {
        if (this.filtersOnIcon != null) {
            this.filtersOnIcon.setVisibility(this.filters.inInittedState() ? 8 : 0);
        }
    }
}
