package com.hotellook.location;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.location.LocationRequest;
import com.hotellook.HotellookApplication;
import com.hotellook.events.LastKnownLocationUpdateEvent;
import com.hotellook.events.MainActivityOnResumeEvent;
import com.hotellook.events.SearchStartEvent;
import com.squareup.otto.Subscribe;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.util.concurrent.TimeUnit;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Observable.Transformer;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public class LastKnownLocationProvider {
    private boolean firstMainActivityLaunchSkipped;
    private Location lastKnownLocation;
    private final PublishSubject<Boolean> lastKnownLocationRequestTrigger;
    private final Observable<Location> locationObservable;
    private final ReactiveLocationProvider locationProvider;
    private Subscription locationSubscription;
    private Boolean noLocationPermissionOnLastRequest;
    private Boolean noPlayServicesOnLastRequest;
    private final RxPermissions rxPermissions;

    public LastKnownLocationProvider(Context context, LocationRequestResolver locationRequestResolver, ReactiveLocationProvider locationProvider) {
        this.noLocationPermissionOnLastRequest = null;
        this.noPlayServicesOnLastRequest = null;
        this.locationProvider = locationProvider;
        this.rxPermissions = RxPermissions.getInstance(context);
        this.lastKnownLocationRequestTrigger = PublishSubject.create();
        this.locationObservable = this.lastKnownLocationRequestTrigger.compose(ensureLocationPermission()).compose(locationRequestResolver.ensureGooglePlayServices()).flatMap(handlePlayServicesAvailability()).flatMap(LastKnownLocationProvider$$Lambda$1.lambdaFactory$(this)).replay(1).autoConnect();
        subscribeToLocationUpdates();
        triggerNextLocationRequest();
    }

    /* synthetic */ Observable lambda$new$0(Boolean ok) {
        return Observable.mergeDelayError(observeLastKnownLocation(), observeFineLocation());
    }

    @NonNull
    private Func1<Boolean, Observable<? extends Boolean>> handlePlayServicesAvailability() {
        return LastKnownLocationProvider$$Lambda$2.lambdaFactory$(this);
    }

    /* synthetic */ Observable lambda$handlePlayServicesAvailability$1(Boolean hasPlayServices) {
        if (hasPlayServices.booleanValue()) {
            this.noPlayServicesOnLastRequest = Boolean.valueOf(false);
            return Observable.just(Boolean.valueOf(true));
        }
        this.noPlayServicesOnLastRequest = Boolean.valueOf(true);
        Timber.m756w("Google play services not available", new Object[0]);
        return Observable.empty();
    }

    private Transformer<? super Boolean, Boolean> ensureLocationPermission() {
        return LastKnownLocationProvider$$Lambda$3.lambdaFactory$(this);
    }

    /* synthetic */ Observable lambda$ensureLocationPermission$4(Observable observable) {
        return observable.flatMap(LastKnownLocationProvider$$Lambda$15.lambdaFactory$(this));
    }

    /* synthetic */ Observable lambda$null$3(Boolean ok) {
        return observeLocationPermission().flatMap(LastKnownLocationProvider$$Lambda$16.lambdaFactory$(this));
    }

    /* synthetic */ Observable lambda$null$2(Boolean granted) {
        this.noLocationPermissionOnLastRequest = Boolean.valueOf(!granted.booleanValue());
        if (granted.booleanValue()) {
            return Observable.just(Boolean.valueOf(true));
        }
        Timber.m756w("No permission for location request", new Object[0]);
        return Observable.empty();
    }

    private void triggerNextLocationRequest() {
        this.lastKnownLocationRequestTrigger.onNext(Boolean.valueOf(true));
    }

    private void subscribeToLocationUpdates() {
        Timber.m755i("Start last known location update on create", new Object[0]);
        this.locationSubscription = this.locationObservable.subscribe(LastKnownLocationProvider$$Lambda$4.lambdaFactory$(this), LastKnownLocationProvider$$Lambda$5.lambdaFactory$(this));
    }

    private Observable<Location> observeLastKnownLocation() {
        return Observable.fromCallable(LastKnownLocationProvider$$Lambda$6.lambdaFactory$(this)).flatMap(LastKnownLocationProvider$$Lambda$7.lambdaFactory$(this));
    }

    /* synthetic */ Location lambda$observeLastKnownLocation$5() throws Exception {
        return this.lastKnownLocation;
    }

    /* synthetic */ Observable lambda$observeLastKnownLocation$9(Location lastKnownLocation) {
        if (lastKnownLocation == null) {
            return this.locationProvider.getLastKnownLocation().doOnSubscribe(LastKnownLocationProvider$$Lambda$12.lambdaFactory$()).doOnNext(LastKnownLocationProvider$$Lambda$13.lambdaFactory$()).doOnCompleted(LastKnownLocationProvider$$Lambda$14.lambdaFactory$()).subscribeOn(Schedulers.io());
        }
        Timber.m751d("Skip last known location detection. Already have one.", new Object[0]);
        return Observable.empty();
    }

    private Observable<Location> observeFineLocation() {
        return this.locationProvider.getUpdatedLocation(LocationRequest.create().setPriority(100).setInterval(TimeUnit.MINUTES.toMillis(1)).setNumUpdates(1)).first().doOnSubscribe(LastKnownLocationProvider$$Lambda$8.lambdaFactory$()).doOnNext(LastKnownLocationProvider$$Lambda$9.lambdaFactory$()).doOnCompleted(LastKnownLocationProvider$$Lambda$10.lambdaFactory$());
    }

    @NonNull
    private Observable<Boolean> observeLocationPermission() {
        return Observable.fromCallable(LastKnownLocationProvider$$Lambda$11.lambdaFactory$(this));
    }

    /* synthetic */ Boolean lambda$observeLocationPermission$13() throws Exception {
        return Boolean.valueOf(this.rxPermissions.isGranted("android.permission.ACCESS_FINE_LOCATION"));
    }

    private void onNewLocation(@NonNull Location location) {
        this.lastKnownLocation = location;
        Timber.m755i("Location updated: %s", location);
        HotellookApplication.eventBus().post(new LastKnownLocationUpdateEvent(location));
    }

    private void logError(Throwable error) {
        Timber.m754e(error, "Unable to retrieve location update", new Object[0]);
    }

    @Nullable
    public Location lastKnownLocation() {
        return this.lastKnownLocation;
    }

    @NonNull
    public Observable<Location> lastKnownLocationObservable() {
        return this.locationObservable;
    }

    @Subscribe
    public void onNewSearch(SearchStartEvent event) {
        Timber.m755i("Start last known location update on search start", new Object[0]);
        triggerNextLocationRequest();
    }

    @Subscribe
    public void onReturnToApp(MainActivityOnResumeEvent event) {
        if (!this.firstMainActivityLaunchSkipped) {
            this.firstMainActivityLaunchSkipped = true;
        } else if (noPermissionLastRequest() || noGpsLastRequest()) {
            Timber.m755i("Return to app detected. Let's try to request last known location again.", new Object[0]);
            triggerNextLocationRequest();
        }
    }

    private boolean noGpsLastRequest() {
        return this.noPlayServicesOnLastRequest != null && this.noPlayServicesOnLastRequest.booleanValue();
    }

    private boolean noPermissionLastRequest() {
        return this.noLocationPermissionOnLastRequest != null && this.noLocationPermissionOnLastRequest.booleanValue();
    }
}
