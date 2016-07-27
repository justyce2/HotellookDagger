package com.hotellook.ui.screen.hotel.general;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.BestOfferChangeSearchParamsClickEvent;
import com.hotellook.events.BestOfferFindPricesClickEvent;
import com.hotellook.events.BestOfferUpdateClickEvent;
import com.hotellook.events.SearchResultsRetryEvent;
import com.hotellook.ui.screen.hotel.OffersLoaderStateModel;
import com.hotellook.ui.screen.hotel.prices.OfferView;
import com.hotellook.ui.screen.hotel.prices.OffersLoaderState;
import com.hotellook.utils.RxUtil;
import pl.charmas.android.reactivelocation.C1822R;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BestOfferView extends LinearLayout {
    private AnimatorSet appearingAnimator;
    private OffersLoaderStateModel model;
    private Subscription offersLoadingStateSubscription;

    /* renamed from: com.hotellook.ui.screen.hotel.general.BestOfferView.1 */
    class C13151 extends MonkeySafeClickListener {
        C13151() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new SearchResultsRetryEvent());
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.general.BestOfferView.2 */
    class C13162 extends MonkeySafeClickListener {
        C13162() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new BestOfferUpdateClickEvent());
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.general.BestOfferView.3 */
    class C13173 extends MonkeySafeClickListener {
        C13173() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new BestOfferFindPricesClickEvent());
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.general.BestOfferView.4 */
    class C13184 extends MonkeySafeClickListener {
        C13184() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new BestOfferChangeSearchParamsClickEvent());
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.general.BestOfferView.5 */
    static /* synthetic */ class C13195 {
        static final /* synthetic */ int[] f730x4df94459;

        static {
            f730x4df94459 = new int[OffersLoaderState.values().length];
            try {
                f730x4df94459[OffersLoaderState.LOADING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f730x4df94459[OffersLoaderState.HAS_ROOMS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f730x4df94459[OffersLoaderState.EMPTY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f730x4df94459[OffersLoaderState.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f730x4df94459[OffersLoaderState.OUTDATED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f730x4df94459[OffersLoaderState.NO_ROOMS.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f730x4df94459[OffersLoaderState.UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public BestOfferView(Context context) {
        super(context);
    }

    public BestOfferView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setModel(OffersLoaderStateModel offersLoaderStateModel) {
        this.model = offersLoaderStateModel;
        update();
    }

    private void update() {
        this.offersLoadingStateSubscription = this.model.observeState().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(BestOfferView$$Lambda$1.lambdaFactory$(this), BestOfferView$$Lambda$2.lambdaFactory$());
    }

    private void setState(OffersLoaderState state) {
        boolean showAppearingAnimation = false;
        if (getChildCount() > 0) {
            cancelRunningAnimations();
            removeAllViews();
            showAppearingAnimation = true;
        }
        switch (C13195.f730x4df94459[state.ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                attachLoadingView();
                break;
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                attachBestPriceView();
                break;
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                attachNewSearchView();
                break;
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                attachErrorView();
                break;
            case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                attachOutdatedView();
                break;
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
                attachChangeParamsView();
                break;
            case C1822R.styleable.MapAttrs_uiCompass /*7*/:
                attachLoadingView();
                break;
        }
        if (showAppearingAnimation) {
            animateAppearing();
        }
    }

    private void cancelRunningAnimations() {
        if (this.appearingAnimator != null) {
            this.appearingAnimator.cancel();
        }
    }

    private void animateAppearing() {
        int heightBeforeUpdate = getHeight();
        int heightAfterUpdate = getRemeasuredHeight();
        if (heightBeforeUpdate != 0) {
            Animator resizeAnimator = createResizeAnimator(heightBeforeUpdate, heightAfterUpdate);
            Animator alphaAnimator = createAlphaAnimator();
            this.appearingAnimator = new AnimatorSet();
            this.appearingAnimator.playTogether(new Animator[]{resizeAnimator, alphaAnimator});
            this.appearingAnimator.start();
        }
    }

    private ValueAnimator createAlphaAnimator() {
        ValueAnimator alphaAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        alphaAnimator.addUpdateListener(BestOfferView$$Lambda$3.lambdaFactory$(this));
        return alphaAnimator;
    }

    /* synthetic */ void lambda$createAlphaAnimator$1(ValueAnimator animation) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setAlpha(((Float) animation.getAnimatedValue()).floatValue());
        }
    }

    private int getRemeasuredHeight() {
        measure(MeasureSpec.makeMeasureSpec(getWidth(), RtlSpacingHelper.UNDEFINED), MeasureSpec.makeMeasureSpec(0, 0));
        return getMeasuredHeight();
    }

    private ValueAnimator createResizeAnimator(int heightBeforeUpdate, int heightAfterUpdate) {
        ValueAnimator resizeAnimation = ValueAnimator.ofInt(new int[]{heightBeforeUpdate, heightAfterUpdate});
        resizeAnimation.addUpdateListener(BestOfferView$$Lambda$4.lambdaFactory$(this));
        return resizeAnimation;
    }

    /* synthetic */ void lambda$createResizeAnimator$2(ValueAnimator animation) {
        getLayoutParams().height = ((Integer) animation.getAnimatedValue()).intValue();
        requestLayout();
    }

    private void attachErrorView() {
        addView(inflateAndSetUpMessageView(C1178R.string.best_offer_error_message, C1178R.string.hotel_error_btn, new C13151()));
    }

    private void attachOutdatedView() {
        addView(inflateAndSetUpMessageView(C1178R.string.hotel_prices_outdated_tip, C1178R.string.btn_update_prices, new C13162()));
    }

    private View inflateAndSetUpMessageView(@StringRes int messageRes, @StringRes int buttonTextRes, @NonNull OnClickListener onClickListener) {
        View view = inflateFromLayout(C1178R.layout.message_view);
        TextView button = (TextView) view.findViewById(C1178R.id.button);
        ((TextView) view.findViewById(C1178R.id.message)).setText(messageRes);
        button.setText(buttonTextRes);
        button.setOnClickListener(onClickListener);
        return view;
    }

    private void attachNewSearchView() {
        addView(inflateAndSetUpMessageView(C1178R.string.best_offer_new_search_message, C1178R.string.search_prices, new C13173()));
    }

    private void attachChangeParamsView() {
        addView(inflateAndSetUpMessageView(C1178R.string.hotel_no_rooms_tip, C1178R.string.hotel_change_search_params_btn, new C13184()));
    }

    private void attachBestPriceView() {
        OfferView gateView = (OfferView) inflateFromLayout(C1178R.layout.offer_view);
        gateView.setData(this.model.getSearchParams(), this.model.getBestOffer(), this.model.getRoomName(), true, true, this.model.discounts(), this.model.highlights());
        addView(gateView);
    }

    private void attachLoadingView() {
        addView(inflateFromLayout(C1178R.layout.loading_view));
    }

    private View inflateFromLayout(int layoutRes) {
        return LayoutInflater.from(getContext()).inflate(layoutRes, this, false);
    }

    public void notifyModelChanged() {
        this.model.onSearchStarted();
        update();
    }

    protected void onDetachedFromWindow() {
        RxUtil.safeUnsubscribe(this.offersLoadingStateSubscription);
        super.onDetachedFromWindow();
    }
}
