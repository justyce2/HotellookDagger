package com.hotellook.search;

import android.content.Context;
import android.support.annotation.Nullable;
import com.hotellook.HotellookApplication;
import com.hotellook.api.data.DestinationType;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.events.NewDestinationEvent;
import com.squareup.otto.Subscribe;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class DumpCacher {
    private final HotellookComponent component;
    private DumpCache dumpCache;
    private String language;
    private int locationId;

    public DumpCacher(Context context) {
        this.component = HotellookApplication.from(context).getComponent();
    }

    public void cache(int locationId, String language) {
        if (hasCache(locationId, language)) {
            Timber.m751d("Search. Location already cached/caching", new Object[0]);
            return;
        }
        Timber.m751d("Search. Start pre caching full dump on new destination with id: %d", Integer.valueOf(locationId));
        this.locationId = locationId;
        this.language = language;
        DumpSource dumpSource = this.component.dumpSource();
        dumpSource.observe(locationId, language).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnError(DumpCacher$$Lambda$1.lambdaFactory$(this)).subscribe(DumpCacher$$Lambda$2.lambdaFactory$(), DumpCacher$$Lambda$3.lambdaFactory$());
        this.dumpCache = new DumpCache(dumpSource.locationsObservable(), dumpSource.locationDumpObservable());
    }

    /* synthetic */ void lambda$cache$0(Throwable error) {
        this.dumpCache = null;
    }

    @Subscribe
    public void onNewDestination(NewDestinationEvent event) {
        if (event.type == DestinationType.CITY) {
            cache(event.locationId, event.language);
        }
    }

    private boolean hasCache(int locationId, String language) {
        return this.locationId == locationId && this.language.equals(language) && this.dumpCache != null;
    }

    @Nullable
    public DumpCache obtainCache(int locationId, String language) {
        if (hasCache(locationId, language)) {
            return this.dumpCache;
        }
        return null;
    }
}
