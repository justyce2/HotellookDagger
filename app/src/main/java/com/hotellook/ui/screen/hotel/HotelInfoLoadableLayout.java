package com.hotellook.ui.screen.hotel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.utils.RxUtil;
import pl.charmas.android.reactivelocation.C1822R;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HotelInfoLoadableLayout extends LoadableLayout {
    private View content;
    private View errorView;
    private Subscription lceSubscription;
    private View loading;

    /* renamed from: com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout.1 */
    class C13081 extends MonkeySafeClickListener {
        C13081() {
        }

        public void onSafeClick(View v) {
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout.2 */
    static /* synthetic */ class C13092 {
        static final /* synthetic */ int[] f729x491420ea;

        static {
            f729x491420ea = new int[LCE.values().length];
            try {
                f729x491420ea[LCE.CONTENT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f729x491420ea[LCE.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f729x491420ea[LCE.LOADING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public enum LCE {
        LOADING,
        CONTENT,
        ERROR
    }

    public HotelInfoLoadableLayout(Context context) {
        super(context);
    }

    public HotelInfoLoadableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.content = findViewById(C1178R.id.loadable_content);
        this.content.setVisibility(8);
        if (this.content == null) {
            throw new RuntimeException("Provide content view with id loadable_content");
        }
        this.loading = LayoutInflater.from(getContext()).inflate(C1178R.layout.view_hotel_info_loading, this, false);
        this.loading.setVisibility(8);
        addView(this.loading);
        this.errorView = LayoutInflater.from(getContext()).inflate(C1178R.layout.view_hotel_info_error, this, false);
        this.errorView.setVisibility(8);
        this.errorView.findViewById(C1178R.id.btn_retry).setOnClickListener(new C13081());
        addView(this.errorView);
        if (isInEditMode()) {
            showView(this.content);
        }
    }

    public Observable<LCE> observeState(HotelDataSource hotelDataSource) {
        Observable<LCE> lceObservable = Observable.combineLatest(hotelDataSource.basicHotelDataObservable(), hotelDataSource.locationObservable(), hotelDataSource.hotelDetailObservable(), HotelInfoLoadableLayout$$Lambda$1.lambdaFactory$()).map(HotelInfoLoadableLayout$$Lambda$2.lambdaFactory$()).startWith(LCE.LOADING).onErrorReturn(HotelInfoLoadableLayout$$Lambda$3.lambdaFactory$());
        this.lceSubscription = lceObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(HotelInfoLoadableLayout$$Lambda$4.lambdaFactory$(this), HotelInfoLoadableLayout$$Lambda$5.lambdaFactory$(this));
        return lceObservable;
    }

    /* synthetic */ void lambda$observeState$3(LCE lce) {
        switch (C13092.f729x491420ea[lce.ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                showView(this.content);
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                showView(this.errorView);
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                showView(this.loading);
            default:
        }
    }

    /* synthetic */ void lambda$observeState$4(Throwable error) {
        showView(this.errorView);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        HotellookApplication.eventBus().register(this);
    }

    protected void onDetachedFromWindow() {
        RxUtil.safeUnsubscribe(this.lceSubscription);
        HotellookApplication.eventBus().unregister(this);
        super.onDetachedFromWindow();
    }

    public void enableAnimation() {
        setUp();
    }
}
