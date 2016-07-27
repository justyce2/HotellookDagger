package com.hotellook.ui.screen.favorite.view;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import com.hotellook.C1178R;
import com.hotellook.common.view.OnScreenLocation;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.dependencies.ActivityModule;
import com.hotellook.interactor.favorites.FavoriteCityData;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseMvpFragment;
import com.hotellook.ui.screen.favorite.dependencies.DaggerFavoritesComponent;
import com.hotellook.ui.screen.favorite.dependencies.FavoritesComponent;
import com.hotellook.ui.screen.favorite.dependencies.FavoritesModule;
import com.hotellook.ui.screen.favorite.presenter.FavoritesPresenter;
import com.hotellook.ui.screen.searchresults.HotelItemDecoration;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.LayoutManagerWrapper;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.ViewUtils;
import com.hotellook.utils.sdk.SpinnerDropDownUtils;
import java.util.List;

public class FavoritesFragment extends BaseMvpFragment<FavoritesView, FavoritesPresenter> implements OnItemSelectedListener, FavoritesView {
    public static final double MAX_SPINNER_HEIGHT_IN_ITEMS = 5.5d;
    @BindView
    TextView bottomArrowMessage;
    @BindView
    Spinner citiesSpinner;
    private CitiesSpinnerAdapter citiesSpinnerAdapter;
    private FavoritesComponent component;
    private FavoriteHotelsAdapter hotelsAdapter;
    @BindView
    RecyclerView hotelsList;
    private LayoutManagerWrapper layoutManager;
    @BindView
    View messageArrowDown;
    @BindView
    View messageArrowUp;
    @BindView
    View placeHolder;
    @BindView
    View placeHolderIcon;
    @BindView
    TextView placeHolderMessage;
    @BindView
    View progressBar;
    @BindView
    View spinnerContainer;
    @BindView
    View spinnerShadow;

    /* renamed from: com.hotellook.ui.screen.favorite.view.FavoritesFragment.1 */
    class C12541 extends OnScrollListener {
        C12541() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
            FavoritesFragment.this.onScrollStateChanged(recyclerView, scrollState);
        }
    }

    /* renamed from: com.hotellook.ui.screen.favorite.view.FavoritesFragment.2 */
    class C12552 implements OnGlobalLayoutListener {
        C12552() {
        }

        public void onGlobalLayout() {
            View layout = FavoritesFragment.this.getView();
            if (layout != null) {
                AndroidUtils.removeOnGlobalLayoutListener(layout, this);
            }
            if (FavoritesFragment.this.messageArrowDown != null) {
                MarginLayoutParams params = (MarginLayoutParams) FavoritesFragment.this.messageArrowDown.getLayoutParams();
                params.setMargins(OnScreenLocation.create(FavoritesFragment.this.mainActivity().findViewById(C1178R.id.bn_search)).contentCenterX, params.topMargin, params.rightMargin, params.bottomMargin);
                FavoritesFragment.this.messageArrowDown.requestLayout();
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createComponent();
    }

    private void createComponent() {
        this.component = DaggerFavoritesComponent.builder().hotellookComponent(appComponent()).activityModule(new ActivityModule(mainActivity())).favoritesModule(new FavoritesModule()).build();
    }

    protected View inflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(C1178R.layout.fragment_favorites, container, false);
    }

    @NonNull
    public FavoritesPresenter createPresenter() {
        return this.component.presenter();
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity().unlockDrawer();
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(view);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(view);
        setUpToolbar();
        setUpHeartIconVisibility();
        this.hotelsAdapter = this.component.hotelsAdapter();
        this.layoutManager = this.component.layoutManager();
        this.hotelsList.setHasFixedSize(true);
        ItemAnimator animator = this.component.listItemAnimator();
        if (animator != null) {
            this.hotelsList.setItemAnimator(animator);
        }
        this.hotelsList.setLayoutManager(this.layoutManager.get());
        this.hotelsList.addItemDecoration(new HotelItemDecoration(getContext().getResources()));
        this.hotelsList.addOnScrollListener(new C12541());
        this.hotelsList.setAdapter(this.hotelsAdapter);
        if (AndroidUtils.isPhone(getContext()) && AndroidUtils.isPortrait(getContext())) {
            this.citiesSpinner.getLayoutParams().width = -1;
        }
        if (hasInitialSnapshot()) {
            restoreFromSnapshot((Snapshot) initialSnapshot());
        } else {
            ((FavoritesPresenter) getPresenter()).loadData();
        }
    }

    private void setUpHeartIconVisibility() {
        this.placeHolderIcon.setVisibility(AndroidUtils.isPhoneLandscape(getContext()) ? 8 : 0);
    }

    private void setUpSpinnerHeight(int itemsCount) {
        if (!AndroidUtils.isPhoneLandscape(getContext())) {
            double dropDownViewHeight = Math.min((double) itemsCount, MAX_SPINNER_HEIGHT_IN_ITEMS) * ((double) getResources().getDimensionPixelOffset(C1178R.dimen.spinner_city_drop_down_item_height));
            if (VERSION.SDK_INT < 21) {
                dropDownViewHeight += (double) (getResources().getDimensionPixelOffset(C1178R.dimen.city_spinner_down_offset) * 2);
            }
            SpinnerDropDownUtils.applyHeight(this.citiesSpinner, (int) dropDownViewHeight);
        }
    }

    private void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
        if (scrollState == 0 || scrollState == 1) {
            this.hotelsAdapter.enablePrecache(this.layoutManager.findFirstVisibleItemPosition(), this.layoutManager.findLastVisibleItemPosition());
        } else {
            this.hotelsAdapter.disablePrecache();
        }
    }

    public void showCitiesSelector(List<FavoriteCityData> cities, int selectedPosition) {
        setUpSpinnerHeight(cities.size());
        mainActivity().getToolbarManager().shadow().setVisibility(8);
        this.spinnerShadow.setVisibility(0);
        this.spinnerContainer.setVisibility(0);
        this.citiesSpinner.setOnItemSelectedListener(null);
        this.citiesSpinnerAdapter = new CitiesSpinnerAdapter(getContext(), cities);
        this.citiesSpinnerAdapter.setSelectedPosition(selectedPosition);
        this.citiesSpinner.setAdapter(this.citiesSpinnerAdapter);
        this.citiesSpinner.setSelection(selectedPosition);
        this.citiesSpinner.post(FavoritesFragment$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$showCitiesSelector$0() {
        if (this.citiesSpinner != null) {
            this.citiesSpinner.setOnItemSelectedListener(this);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        this.citiesSpinnerAdapter.setSelectedPosition(position);
        ((FavoritesPresenter) getPresenter()).onCitySelected(position);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void showHotels(List<FavoriteHotelDataEntity> hotels) {
        hideAll();
        int currentSize = this.hotelsAdapter.getItemCount();
        if (this.hotelsAdapter.isDataDifferent(hotels)) {
            this.hotelsAdapter.setData(hotels);
            this.hotelsAdapter.notifyItemRangeRemoved(0, currentSize);
        }
        this.hotelsList.setVisibility(0);
    }

    public void hideCitySelector() {
        mainActivity().getToolbarManager().shadow().setVisibility(0);
        this.spinnerShadow.setVisibility(8);
        this.spinnerContainer.setVisibility(8);
    }

    public void showPlaceHolderAddFirstFromResults() {
        showPlaceHolder();
    }

    private void showPlaceHolder() {
        hideAll();
        this.placeHolderMessage.setText(C1178R.string.favorites_placeholder_list_tip);
        this.bottomArrowMessage.setText(C1178R.string.add_hotels_from_results);
        ViewUtils.showView(this.placeHolder);
        placeDownArrowAboveSearchTab();
        ViewUtils.showView(this.messageArrowDown);
    }

    public void showPlaceHolderAddFirstHotelAndStartSearch() {
        showPlaceHolder();
    }

    private void placeDownArrowAboveSearchTab() {
        getView().getViewTreeObserver().addOnGlobalLayoutListener(new C12552());
    }

    public void showProgress() {
    }

    public void showPlaceholderSwitchCity() {
        hideAll();
        this.placeHolderMessage.setText(C1178R.string.start_search_or_choose_city);
        ViewUtils.showView(this.placeHolder);
        ViewUtils.showView(this.messageArrowUp);
    }

    private void hideAll() {
        this.placeHolder.setVisibility(8);
        this.messageArrowUp.setVisibility(8);
        this.messageArrowDown.setVisibility(8);
        this.progressBar.setVisibility(8);
        this.hotelsList.setVisibility(8);
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title_white, mainActivity().getToolbarManager().getToolbar(), false);
        title.setText(getString(C1178R.string.favorite_cities_title));
        MainActivity activity = mainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().navigationMode(0).bkgColor(getResources().getColor(C1178R.color.toolbar_green_bkg)).statusBarColor(getResources().getColor(C1178R.color.spf_statusbar_bkg)).toggleColor(getResources().getColor(17170443)).withCustomView(title).withShadow());
    }

    @Nullable
    public Object takeSnapshot() {
        return new Snapshot(this.layoutManager.onSaveInstanceState(), this.hotelsAdapter.getPagersIndexes(), ((FavoritesPresenter) this.presenter).saveState());
    }

    private void restoreFromSnapshot(@NonNull Snapshot snapshot) {
        ((FavoritesPresenter) getPresenter()).restoreState(snapshot.presenterSate);
        this.hotelsAdapter.setPagersIndexes(snapshot.pageIndexes);
        this.layoutManager.onRestoreInstanceState(snapshot.listState, this.hotelsList);
    }
}
