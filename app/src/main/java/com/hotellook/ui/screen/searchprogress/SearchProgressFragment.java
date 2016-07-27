package com.hotellook.ui.screen.searchprogress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;

import com.hotellook.C1178R;
import com.hotellook.events.BackPressEvent;
import com.hotellook.search.Search;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.dialog.DismissDialogListener;
import com.hotellook.ui.screen.AlertDialogFragment;
import com.hotellook.ui.screen.Dialogs;
import com.hotellook.ui.screen.OnBackPressHandler;
import com.hotellook.ui.screen.common.ButterKnifeFragment;
import com.hotellook.ui.screen.information.RateDialogFragment.Source;
import com.hotellook.ui.screen.searchresults.SearchPlaceHolderFragment;
import com.hotellook.ui.screen.searchresults.SearchResultsFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.EventBus;
import com.hotellook.utils.ParamsToStringFormatter;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import me.zhanghai.android.materialprogressbar.C1759R;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit.RetrofitError;

public class SearchProgressFragment extends ButterKnifeFragment implements OnBackPressHandler {
    private static final long AVERAGE_SEARCH_DURATION_IN_MLS;
    private static final long MAX_PROGRESS_TIME_IN_MLS;
    private static final int PROGRESS_DIVISIONS = 10000;
    private GatesAdapter adapter;
    private AlertDialogFragment errorDialog;
    private EventBus eventBus;
    private boolean gatesAppearingAnimationPlayed;
    @BindView
    MaterialProgressBar gatesListWaiter;
    private ValueAnimator progressAnimator;
    @BindView
    ProgressBar progressBar;
    @BindView
    RecyclerView recyclerView;
    private Search search;

    /* renamed from: com.hotellook.ui.screen.searchprogress.SearchProgressFragment.1 */
    class C13521 extends AnimatorListenerAdapter {
        final /* synthetic */ SearchData val$searchData;

        C13521(SearchData searchData) {
            this.val$searchData = searchData;
        }

        public void onAnimationEnd(Animator animation) {
            SearchProgressFragment.this.showResults(this.val$searchData);
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchprogress.SearchProgressFragment.2 */
    class C13532 extends DismissDialogListener {
        C13532() {
        }

        public void onClick(View v) {
            super.onClick(v);
            SearchProgressFragment.this.eventBus.post(new BackPressEvent());
        }
    }

    public SearchProgressFragment() {
        this.gatesAppearingAnimationPlayed = false;
    }

    static {
        AVERAGE_SEARCH_DURATION_IN_MLS = TimeUnit.SECONDS.toMillis(20);
        MAX_PROGRESS_TIME_IN_MLS = TimeUnit.MINUTES.toMillis(1);
    }

    public static Fragment create() {
        return new SearchProgressFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.eventBus = getComponent().eventBus();
        this.search = getComponent().searchKeeper().lastSearchOrThrowException();
    }

    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(C1178R.layout.fragment_search_progress, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (hasInitialSnapshot()) {
            restore(initialSnapshot());
        }
        setUpToolbar();
        setUpRecyclerView();
        setUpViewOffsets(view);
        setUpProgressBar();
        if (this.search.searchData() != null) {
            showResults(this.search.searchData());
            return;
        }
        this.search.observe().compose(subscribeOnIoObserveOnMain()).compose(bindUntilDestroyView()).subscribe(SearchProgressFragment$$Lambda$1.lambdaFactory$(this), SearchProgressFragment$$Lambda$2.lambdaFactory$(this));
        this.search.offersSearchLaunchObservable().map(SearchProgressFragment$$Lambda$3.lambdaFactory$()).compose(subscribeOnIoObserveOnMain()).compose(bindUntilDestroyView()).subscribe(SearchProgressFragment$$Lambda$4.lambdaFactory$(this), SearchProgressFragment$$Lambda$5.lambdaFactory$());
        this.search.respondedGatesObservable().compose(subscribeOnIoObserveOnMain()).compose(bindUntilDestroyView()).subscribe(SearchProgressFragment$$Lambda$6.lambdaFactory$(this), SearchProgressFragment$$Lambda$7.lambdaFactory$());
        showSearchProgress();
        showRateDialogIfConditionsMet();
        this.eventBus.register(this);
    }

    /* synthetic */ void lambda$onViewCreated$0(SearchData searchData) {
        onSearchFinished(searchData);
    }

    /* synthetic */ void lambda$onViewCreated$1(Throwable error) {
        onSearchFail(error);
    }

    /* synthetic */ void lambda$onViewCreated$3(List gates) {
        onGatesListReceived(gates);
    }

    /* synthetic */ void lambda$onViewCreated$5(Set gates) {
        onGatesLoaded(gates);
    }

    private void restore(Object snapshot) {
        this.gatesAppearingAnimationPlayed = ((Boolean) snapshot).booleanValue();
    }

    private void onSearchFinished(SearchData searchData) {
        if (progressBarReachedMax() || this.progressBar == null) {
            showResults(searchData);
        } else {
            animateProgressToMaxAndShowResults(searchData);
        }
    }

    private void animateProgressToMaxAndShowResults(SearchData searchData) {
        if (this.progressAnimator != null && this.progressAnimator.isRunning()) {
            this.progressAnimator.cancel();
        }
        this.progressAnimator = ValueAnimator.ofInt(this.progressBar.getProgress(), this.progressBar.getMax());
        this.progressAnimator.setDuration(500);
        this.progressAnimator.setInterpolator(new AccelerateInterpolator());
        this.progressAnimator.addUpdateListener(SearchProgressFragment$$Lambda$8.lambdaFactory$(this));
        this.progressAnimator.addListener(new C13521(searchData));
        this.progressAnimator.start();
    }

    /* synthetic */ void lambda$animateProgressToMaxAndShowResults$7(ValueAnimator animation) {
        this.progressBar.setProgress(((Integer) animation.getAnimatedValue()).intValue());
    }

    private boolean progressBarReachedMax() {
        return this.progressBar != null && this.progressBar.getProgress() == this.progressBar.getMax();
    }

    private void setUpProgressBar() {
        this.progressBar.setMax(PROGRESS_DIVISIONS);
    }

    private void showSearchProgress() {
        long searchTimestamp = this.search.startTimestamp();
        long expectedSearchTime = AVERAGE_SEARCH_DURATION_IN_MLS;
        long currentPlayTime = System.currentTimeMillis() - searchTimestamp;
        if (((float) currentPlayTime) / ((float) MAX_PROGRESS_TIME_IN_MLS) >= 1.0f) {
            this.progressBar.setProgress(this.progressBar.getMax());
            return;
        }
        this.progressAnimator = ValueAnimator.ofInt(0, PROGRESS_DIVISIONS);
        this.progressAnimator.setDuration(MAX_PROGRESS_TIME_IN_MLS);
        this.progressAnimator.setInterpolator(new SearchProgressInterpolator(expectedSearchTime, MAX_PROGRESS_TIME_IN_MLS));
        this.progressAnimator.addUpdateListener(SearchProgressFragment$$Lambda$9.lambdaFactory$(this));
        this.progressAnimator.start();
        this.progressAnimator.setCurrentPlayTime(currentPlayTime);
    }

    /* synthetic */ void lambda$showSearchProgress$8(ValueAnimator animation) {
        this.progressBar.setProgress((Integer) animation.getAnimatedValue());
    }

    private void onGatesLoaded(Set<Integer> loadedGates) {
        this.adapter.updateLoadedGates(loadedGates);
    }

    private void setUpRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GatesListAnimator animator = new GatesListAnimator();
        if (!this.gatesAppearingAnimationPlayed) {
            this.recyclerView.setItemAnimator(animator);
        }
        this.adapter = new GatesAdapter(getContext());
        this.recyclerView.setAdapter(this.adapter);
    }

    private void setUpViewOffsets(View layout) {
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(layout);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(layout);
        this.recyclerView.addItemDecoration(new GateListItemDecoration(getContext()));
    }

    private void onGatesListReceived(List<Integer> gates) {
        this.gatesAppearingAnimationPlayed = true;
        this.adapter.updateGates(gates);
        this.gatesListWaiter.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(150);
    }

    private void showRateDialogIfConditionsMet() {
        if (getComponent().getRateDialogConditionsTracker().areConditionsMet()) {
            Dialogs.showRateDialog(getActivity(), Source.AUTO);
        }
    }

    public void onDestroyView() {
        if (this.progressAnimator != null) {
            this.progressAnimator.cancel();
            this.progressAnimator = null;
            this.progressBar.animate().alpha(0.0f);
        }
        this.eventBus.unregister(this);
        super.onDestroyView();
    }

    private void showResults(SearchData searchData) {
        if (getActivity() != null && isAdded()) {
            if (searchData.hotels().isEmpty()) {
                getMainActivity().showFragment(new SearchPlaceHolderFragment(), false);
            } else {
                getMainActivity().showFragment(new SearchResultsFragment(), false);
            }
        }
    }

    private void cancelSearchWithDialog(RetrofitError error) {
        if (this.errorDialog == null && !isAfterOnPause()) {
            this.errorDialog = Dialogs.showNetworkErrorDialog(getActivity(), error, new C13532(), SearchProgressFragment$$Lambda$10.lambdaFactory$(this));
        }
    }

    /* synthetic */ boolean lambda$cancelSearchWithDialog$9(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        dialog.dismiss();
        this.eventBus.post(new BackPressEvent());
        return true;
    }

    public void onSearchFail(Throwable error) {
        if (this.progressAnimator != null) {
            this.progressAnimator.cancel();
        }
        if (getFragmentManager().findFragmentByTag(Dialogs.TAG_ERROR_DIALOG) == null) {
            cancelSearchWithDialog(error instanceof RetrofitError ? (RetrofitError) error : null);
        }
    }

    @Subscribe
    public void onBackPress(BackPressEvent event) {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    private void setUpToolbar() {
        View tbContent = LayoutInflater.from(getContext()).inflate(C1178R.layout.toolbar_with_two_lines, getToolbar(), false);
        TextView title = (TextView) tbContent.findViewById(C1759R.id.title);
        TextView subtitle = (TextView) tbContent.findViewById(C1178R.id.subtitle);
        SearchParams searchParams = this.search.searchParams();
        String city = searchParams.destinationName();
        if (searchParams.isDestinationTypeUserLocation()) {
            title.setText(C1178R.string.my_location);
        } else if (searchParams.isDestinationTypeNearbyCities()) {
            title.setText(getString(C1178R.string.neigbour_title, city));
        } else if (searchParams.isDestinationTypeMapPoint()) {
            title.setText(C1178R.string.search_type_name_location);
        } else {
            title.setText(city);
        }
        subtitle.setText(new ParamsToStringFormatter(getContext()).convertToResultsToolbarFormat(searchParams));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().navigationMode(1).bkgColor(ContextCompat.getColor(getContext(), C1178R.color.toolbar_green_bkg)).statusBarColor(ContextCompat.getColor(getContext(), C1178R.color.spf_statusbar_bkg)).toggleColor(ContextCompat.getColor(getContext(), 17170443)).withCustomView(tbContent));
    }

    public boolean onBackPressedHandled() {
        if (isAdded()) {
            this.search.cancel();
        }
        return false;
    }

    @Nullable
    public Object takeSnapshot() {
        return this.gatesAppearingAnimationPlayed;
    }
}
