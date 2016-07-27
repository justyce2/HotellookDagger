package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.HotellookApplication;
import com.hotellook.backstack.BackStackManager;
import com.hotellook.backstack.SnapshotKeeper;
import com.hotellook.utils.EventBus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {
    private final HotellookApplication app;

    public AppModule(HotellookApplication app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public Application provideApp() {
        return this.app;
    }

    @Singleton
    @Provides
    public EventBus provideEventBus() {
        return new EventBus();
    }

    @Singleton
    @Provides
    public SnapshotKeeper provideSnapshotKeeper() {
        return new SnapshotKeeper();
    }

    @Singleton
    @Provides
    public BackStackManager provideBackStackManager() {
        return new BackStackManager();
    }
}
