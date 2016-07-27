package com.hotellook;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.os.StrictMode.VmPolicy;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import com.crashlytics.android.Crashlytics;
import com.facebook.common.soloader.SoLoaderShim;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.getkeepsafe.relinker.ReLinker;
import com.hotellook.common.fresco.BitmapMemoryCacheParamsSupplier;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.dependencies.HotellookComponent.Initializer;
import com.hotellook.utils.CrashlyticsTree;
import com.hotellook.utils.EventBus;
import io.fabric.sdk.android.Fabric;
import java.lang.Thread.UncaughtExceptionHandler;
import timber.log.Timber;

public class HotellookApplication extends Application {
    private static HotellookApplication app;
    public boolean appLaunchedAsExpected;
    private HotellookComponent component;

    public HotellookApplication() {
        this.appLaunchedAsExpected = false;
    }

    public static HotellookApplication from(Context context) {
        return (HotellookApplication) context.getApplicationContext();
    }

    @Deprecated
    public static EventBus eventBus() {
        return app.component.eventBus();
    }

    @Deprecated
    public static HotellookApplication getApp() {
        return app;
    }

    public void onCreate() {
        if (BuildConfig.MULTIDEX.booleanValue()) {
            MultiDex.install(this);
        }
        super.onCreate();
        app = this;
        setDefaultUncaughtExceptionHandler();
        if (this.component == null) {
            this.component = Initializer.init(this);
        }
        if (BuildConfig.CRASHLYTICS.booleanValue()) {
            enableCrashlytics();
        }
        initTimber();
        initFresco();
        registerBusSubscribers();
        FragmentManager.enableDebugLogging(false);
    }

    private void initFresco() {
        SoLoaderShim.setHandler(HotellookApplication$$Lambda$1.lambdaFactory$(this));
        Fresco.initialize(this, OkHttpImagePipelineConfigFactory.newBuilder(this, this.component.okHttpClient()).setBitmapMemoryCacheParamsSupplier(new BitmapMemoryCacheParamsSupplier()).build());
    }

    /* synthetic */ void lambda$initFresco$0(String libraryName) {
        ReLinker.loadLibrary(this, libraryName);
    }

    private void initTimber() {
        Timber.plant(new CrashlyticsTree());
    }

    private void enableCrashlytics() {
        Fabric.with(this, new Crashlytics());
        Crashlytics.setUserIdentifier(this.component.token());
    }

    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    private void registerBusSubscribers() {
        EventBus bus = this.component.eventBus();
        bus.register(this.component.getSearchTracker());
        bus.register(this.component.getRateDialogConditionsTracker());
        bus.register(this.component.getRequestFlagsHelperTracker());
        bus.register(this.component.getMixPanelEventsKeeper());
        bus.register(this.component.getStatistics());
        bus.register(this.component.lastKnownLocationProvider());
        bus.register(this.component.locationRequestResolver());
        bus.register(this.component.dumpCacher());
        bus.register(this.component.getLocationFavoritesCache());
        bus.register(this.component.nearestLocationsProvider());
    }

    private void setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(HotellookApplication$$Lambda$2.lambdaFactory$(this, Thread.getDefaultUncaughtExceptionHandler()));
    }

    /* synthetic */ void lambda$setDefaultUncaughtExceptionHandler$1(UncaughtExceptionHandler defaultUncaughtExceptionHandler, Thread thread, Throwable e) {
        handleUncaughtException(thread, e);
        defaultUncaughtExceptionHandler.uncaughtException(thread, e);
    }

    public HotellookComponent getComponent() {
        return this.component;
    }

    public void setComponent(HotellookComponent component) {
        this.component = component;
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        getComponent().getRateDialogConditionsTracker().onCrash();
    }

    public boolean wasAppLaunchedAsExpected() {
        return this.appLaunchedAsExpected;
    }

    public void setAppLaunchedAsExpected() {
        this.appLaunchedAsExpected = true;
    }

    public String getHost() {
        return this.component.host();
    }
}
