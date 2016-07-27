package com.hotellook.ui.screen.favorite.router;

import com.hotellook.ui.activity.MainActivity;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class FavoritesRouter_Factory implements Factory<FavoritesRouter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<MainActivity> activityProvider;

    static {
        $assertionsDisabled = !FavoritesRouter_Factory.class.desiredAssertionStatus();
    }

    public FavoritesRouter_Factory(Provider<MainActivity> activityProvider) {
        if ($assertionsDisabled || activityProvider != null) {
            this.activityProvider = activityProvider;
            return;
        }
        throw new AssertionError();
    }

    public FavoritesRouter get() {
        return new FavoritesRouter((MainActivity) this.activityProvider.get());
    }

    public static Factory<FavoritesRouter> create(Provider<MainActivity> activityProvider) {
        return new FavoritesRouter_Factory(activityProvider);
    }
}
