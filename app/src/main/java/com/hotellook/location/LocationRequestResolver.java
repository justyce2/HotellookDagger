package com.hotellook.location;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest.Builder;
import com.google.android.gms.location.LocationSettingsResult;
import com.hotellook.events.LocationPermissionRequestResultEvent;
import com.hotellook.events.LocationSettingsRequestResultEvent;
import com.hotellook.utils.AndroidUtils;
import com.squareup.otto.Subscribe;
import me.zhanghai.android.materialprogressbar.BuildConfig;
import pl.charmas.android.reactivelocation.C1822R;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public class LocationRequestResolver {
    private final Context appContext;
    private PublishSubject<Boolean> locationPermissionRequestResult;
    private PublishSubject<Boolean> locationSettingsRequestResult;
    private final ReactiveLocationProvider reactiveLocationProvider;

    public LocationRequestResolver(Context context, ReactiveLocationProvider reactiveLocationProvider) {
        this.locationSettingsRequestResult = PublishSubject.create();
        this.locationPermissionRequestResult = PublishSubject.create();
        this.appContext = context.getApplicationContext();
        this.reactiveLocationProvider = reactiveLocationProvider;
    }

    public Observable<Boolean> resolveLocationRequest(LocationRequest locationRequest) {
        return Observable.just(Boolean.valueOf(true)).compose(ensureGooglePlayServices()).compose(ensureLocationPermission()).compose(ensureLocationSettings(locationRequest, this.reactiveLocationProvider));
    }

    @NonNull
    private Transformer<Boolean, Boolean> ensureLocationPermission() {
        return LocationRequestResolver$$Lambda$1.lambdaFactory$(this);
    }

    /* synthetic */ Observable lambda$ensureLocationPermission$2(Observable observable) {
        return observable.flatMap(LocationRequestResolver$$Lambda$13.lambdaFactory$(this)).doOnNext(LocationRequestResolver$$Lambda$14.lambdaFactory$());
    }

    /* synthetic */ Observable lambda$null$0(Boolean ok) {
        return createLocationPermissionObservable();
    }

    @NonNull
    private Transformer<Boolean, Boolean> ensureLocationSettings(LocationRequest locationRequest, ReactiveLocationProvider locationProvider) {
        return LocationRequestResolver$$Lambda$2.lambdaFactory$(this, locationRequest, locationProvider);
    }

    /* synthetic */ Observable lambda$ensureLocationSettings$4(LocationRequest locationRequest, ReactiveLocationProvider locationProvider, Observable observable) {
        return observable.flatMap(LocationRequestResolver$$Lambda$12.lambdaFactory$(this, locationRequest, locationProvider));
    }

    /* synthetic */ Observable lambda$null$3(LocationRequest locationRequest, ReactiveLocationProvider locationProvider, Boolean ok) {
        return createLocationSettingsObservable(locationRequest, locationProvider);
    }

    @NonNull
    private Observable<Boolean> createLocationSettingsObservable(LocationRequest locationRequest, ReactiveLocationProvider locationProvider) {
        return locationProvider.checkLocationSettings(new Builder().addLocationRequest(locationRequest).setAlwaysShow(true).build()).flatMap(handleLocationSettingsResult()).flatMap(returnSettingsErrorOnFalse());
    }

    @NonNull
    private Func1<LocationSettingsResult, Observable<? extends Boolean>> handleLocationSettingsResult() {
        return LocationRequestResolver$$Lambda$3.lambdaFactory$(this);
    }

    /* synthetic */ Observable lambda$handleLocationSettingsResult$5(LocationSettingsResult locationSettingsResult) {
        switch (locationSettingsResult.getStatus().getStatusCode()) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                return Observable.just(Boolean.valueOf(true));
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
                Timber.m751d(BuildConfig.FLAVOR, new Object[0]);
                startShadowActivity(2, locationSettingsResult.getStatus());
                return this.locationSettingsRequestResult;
            default:
                return Observable.just(Boolean.valueOf(false));
        }
    }

    @NonNull
    private Func1<Boolean, Observable<? extends Boolean>> returnSettingsErrorOnFalse() {
        return LocationRequestResolver$$Lambda$4.lambdaFactory$();
    }

    static /* synthetic */ Observable lambda$returnSettingsErrorOnFalse$6(Boolean granted) {
        if (granted.booleanValue()) {
            return Observable.just(Boolean.valueOf(true));
        }
        return Observable.error(new InvalidLocationSettingsException());
    }

    @NonNull
    private Observable<Boolean> createLocationPermissionObservable() {
        return Observable.fromCallable(LocationRequestResolver$$Lambda$5.lambdaFactory$(this)).flatMap(LocationRequestResolver$$Lambda$6.lambdaFactory$(this)).flatMap(LocationRequestResolver$$Lambda$7.lambdaFactory$());
    }

    /* synthetic */ Boolean lambda$createLocationPermissionObservable$7() throws Exception {
        return Boolean.valueOf(hasLocationPermission());
    }

    /* synthetic */ Observable lambda$createLocationPermissionObservable$8(Boolean granted) {
        if (granted.booleanValue()) {
            return Observable.just(Boolean.valueOf(true));
        }
        startShadowActivity(1, null);
        return this.locationPermissionRequestResult;
    }

    static /* synthetic */ Observable lambda$createLocationPermissionObservable$9(Boolean granted) {
        if (granted.booleanValue()) {
            return Observable.just(Boolean.valueOf(true));
        }
        return Observable.error(new PermissionDeniedException());
    }

    private void startShadowActivity(int requestType, Status status) {
        Intent intent = new Intent(this.appContext, ShadowActivity.class);
        intent.putExtra(ShadowActivity.REQUEST_TYPE, requestType);
        if (status != null) {
            intent.putExtra(ShadowActivity.STATUS, status);
        }
        intent.addFlags(268435456);
        this.appContext.startActivity(intent);
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this.appContext, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    @NonNull
    public Transformer<Boolean, Boolean> ensureGooglePlayServices() {
        return LocationRequestResolver$$Lambda$8.lambdaFactory$(this);
    }

    /* synthetic */ Observable lambda$ensureGooglePlayServices$12(Observable observable) {
        return observable.flatMap(LocationRequestResolver$$Lambda$10.lambdaFactory$(this)).doOnNext(LocationRequestResolver$$Lambda$11.lambdaFactory$());
    }

    /* synthetic */ Observable lambda$null$10(Boolean ok) {
        return observeGooglePlayServices();
    }

    private Observable<Boolean> observeGooglePlayServices() {
        return Observable.fromCallable(LocationRequestResolver$$Lambda$9.lambdaFactory$(this));
    }

    /* synthetic */ Boolean lambda$observeGooglePlayServices$13() throws Exception {
        return Boolean.valueOf(AndroidUtils.isGoogleServicesAvailable(this.appContext));
    }

    @Subscribe
    public void onLocationPermissionRequestResult(LocationPermissionRequestResultEvent event) {
        this.locationPermissionRequestResult.onNext(Boolean.valueOf(event.requestResult));
    }

    @Subscribe
    public void onLocationSettingRequestResult(LocationSettingsRequestResultEvent event) {
        this.locationSettingsRequestResult.onNext(Boolean.valueOf(event.requestResult));
    }
}
