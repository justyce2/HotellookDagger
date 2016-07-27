package com.hotellook.ui.screen.browser;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.events.BookPredictedEvent;
import com.hotellook.events.BrowserTimeoutEvent;
import com.hotellook.events.FrameCloseEvent;
import com.hotellook.statistics.mixpanel.OnScreenTimeDuration;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.OnBackPressHandler;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.hotel.api.Booking;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CommonPreferences;
import com.squareup.otto.Subscribe;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class BrowserFragment extends BaseFragment implements OnBackPressHandler {
    private static final long FRAME_PREDICTED_DURATION;
    private static final long LOADING_TIMEOUT = 30000;
    private static final long SCREEN_PREDICTION_PERIOD;
    private Booking booking;
    private MenuItem btnBack;
    private MenuItem btnForward;
    private CommonPreferences commonPreferences;
    private boolean finishedLoading;
    private long framePredictedDuration;
    private int lastProgress;
    private TimerTask loadingTimeOutTask;
    private OnScreenTimeDuration onScreenTimeDuration;
    private View placeHolder;
    private ProgressBar progressbar;
    private long screenPredictedPeriod;
    private Timer timer;
    private WebView webView;
    private FrameLayout webViewContainer;

    /* renamed from: com.hotellook.ui.screen.browser.BrowserFragment.1 */
    class C12161 extends TimerTask {
        C12161() {
        }

        public void run() {
            if (BrowserFragment.this.onScreenTimeDuration.isDurationReached(BrowserFragment.this.framePredictedDuration)) {
                BrowserFragment.this.onScreenTimeDuration.onScreenFinishedOrPaused();
                HotellookApplication.eventBus().post(new BookPredictedEvent(BrowserFragment.this.onScreenTimeDuration, BrowserFragment.this.booking.searchParams(), BrowserFragment.this.booking.offer(), BrowserFragment.this.booking.hotelId()));
                cancel();
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.browser.BrowserFragment.2 */
    class C12182 extends WebChromeClient {

        /* renamed from: com.hotellook.ui.screen.browser.BrowserFragment.2.1 */
        class C12171 extends AnimatorListenerAdapter {
            C12171() {
            }

            public void onAnimationEnd(Animator animation) {
                BrowserFragment.this.progressbar.setVisibility(8);
            }
        }

        C12182() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            BrowserFragment.this.lastProgress = newProgress;
            if (BrowserFragment.this.progressbar.getAlpha() == 0.0f) {
                BrowserFragment.this.progressbar.setVisibility(0);
                BrowserFragment.this.progressbar.animate().alpha(1.0f).setDuration(200).start();
            }
            BrowserFragment.this.progressbar.setProgress(newProgress);
            if (newProgress == 100) {
                BrowserFragment.this.progressbar.animate().alpha(0.0f).setDuration(200).setListener(new C12171()).start();
            }
        }
    }

    private class GateWebViewClient extends WebViewClient {
        private GateWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            BrowserFragment.this.setUpBrowserNav();
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            BrowserFragment.this.setUpBrowserNav();
            BrowserFragment.this.hidePlaceHolder();
            BrowserFragment.this.finishedLoading = true;
        }
    }

    private static class TimeoutTimerTask extends TimerTask {
        private TimeoutTimerTask() {
        }

        public void run() {
            HotellookApplication.eventBus().post(new BrowserTimeoutEvent());
        }
    }

    public BrowserFragment() {
        this.onScreenTimeDuration = new OnScreenTimeDuration();
    }

    static {
        FRAME_PREDICTED_DURATION = TimeUnit.SECONDS.toMillis(5);
        SCREEN_PREDICTION_PERIOD = TimeUnit.SECONDS.toMillis(1);
    }

    public static BrowserFragment create(Booking booking) {
        BrowserFragment fragment = new BrowserFragment();
        fragment.setBooking(booking);
        return fragment;
    }

    public void onPause() {
        this.onScreenTimeDuration.onScreenFinishedOrPaused();
        super.onPause();
    }

    public void onResume() {
        this.onScreenTimeDuration.onScreenStartedOrResumed();
        super.onResume();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        this.commonPreferences = new CommonPreferences(getContext());
        this.screenPredictedPeriod = null != null ? SCREEN_PREDICTION_PERIOD / 60 : SCREEN_PREDICTION_PERIOD;
        this.framePredictedDuration = null != null ? FRAME_PREDICTED_DURATION / 60 : FRAME_PREDICTED_DURATION;
        this.loadingTimeOutTask = new TimeoutTimerTask();
        this.timer = new Timer();
        this.timer.schedule(this.loadingTimeOutTask, LOADING_TIMEOUT);
        this.timer.scheduleAtFixedRate(new C12161(), this.screenPredictedPeriod, this.screenPredictedPeriod);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(C1178R.layout.browser_fragment, container, false);
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(layout);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(layout);
        AndroidUtils.addMarginToOffsetNavBarBottom(layout);
        setUpViews(layout);
        setUpToolbar();
        getMainActivity().lockDrawer();
        if (this.finishedLoading) {
            hidePlaceHolder();
        }
        layout.postDelayed(BrowserFragment$$Lambda$1.lambdaFactory$(this), 100);
        HotellookApplication.eventBus().register(this);
        return layout;
    }

    /* synthetic */ void lambda$onCreateView$0() {
        setUpWebView(this.booking.deeplink());
    }

    public void onDestroyView() {
        this.timer.cancel();
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onTimeout(BrowserTimeoutEvent event) {
        hidePlaceHolder();
    }

    private void setUpViews(ViewGroup layout) {
        this.webViewContainer = (FrameLayout) layout.findViewById(C1178R.id.webview_container);
        this.progressbar = (ProgressBar) layout.findViewById(C1178R.id.progressbar);
        this.placeHolder = layout.findViewById(C1178R.id.place_holder);
        if (this.lastProgress == 0 || this.lastProgress == 100) {
            this.progressbar.setAlpha(0.0f);
        } else {
            this.progressbar.setProgress(this.lastProgress);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void setUpWebView(String url) {
        if (getActivity() != null || this.webView != null) {
            if (this.webView == null && getActivity() != null) {
                this.webView = new WebView(getActivity());
                this.webView.setLayoutParams(createWebViewLayoutParams());
                this.webView.setWebChromeClient(getWebChromeClient());
                this.webView.setWebViewClient(new GateWebViewClient());
                WebSettings settings = this.webView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setUseWideViewPort(true);
                settings.setLoadWithOverviewMode(true);
                settings.setSupportZoom(true);
                settings.setJavaScriptCanOpenWindowsAutomatically(true);
                settings.setBuiltInZoomControls(true);
                settings.setDisplayZoomControls(false);
                settings.setDomStorageEnabled(true);
                this.webView.loadUrl(url);
            } else if (this.webView.getParent() != null) {
                ((ViewGroup) this.webView.getParent()).removeView(this.webView);
            }
            this.webViewContainer.removeAllViews();
            this.webViewContainer.addView(this.webView);
        }
    }

    @NonNull
    private WebChromeClient getWebChromeClient() {
        return new C12182();
    }

    private LayoutParams createWebViewLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public boolean onBackPressedHandled() {
        if (this.webView != null) {
            this.webView.setVisibility(4);
            ((ViewGroup) this.webView.getParent()).removeAllViews();
            this.webView.clearHistory();
            this.webView.clearCache(true);
            this.webView.destroy();
            this.webView = null;
        }
        this.loadingTimeOutTask.cancel();
        this.onScreenTimeDuration.onScreenFinishedOrPaused();
        HotellookApplication.eventBus().post(new FrameCloseEvent(this.onScreenTimeDuration));
        return false;
    }

    private void setUpBrowserNav() {
        if (this.btnBack != null && this.btnForward != null) {
            if (this.webView != null) {
                this.btnBack.setEnabled(this.webView.canGoBack());
                this.btnForward.setEnabled(this.webView.canGoForward());
                return;
            }
            this.btnBack.setEnabled(false);
            this.btnForward.setEnabled(false);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(C1178R.menu.browser_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        this.btnBack = menu.findItem(C1178R.id.back);
        this.btnForward = menu.findItem(C1178R.id.forward);
        setUpBrowserNav();
        super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C1178R.id.back /*2131690081*/:
                this.webView.goBack();
                return true;
            case C1178R.id.forward /*2131690082*/:
                this.webView.goForward();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void hidePlaceHolder() {
        this.placeHolder.setVisibility(8);
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, getMainActivity().getToolbarManager().getToolbar(), false);
        title.setText(this.booking.gateName());
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().navigationMode(2).bkgColor(getResources().getColor(C1178R.color.gray_FAFAFA)).statusBarColor(getResources().getColor(C1178R.color.gray_C8C8C8)).toggleColor(getResources().getColor(C1178R.color.dp_toggle)).withCustomView(title).withShadow());
    }

    private void setBooking(Booking booking) {
        this.booking = booking;
    }
}
