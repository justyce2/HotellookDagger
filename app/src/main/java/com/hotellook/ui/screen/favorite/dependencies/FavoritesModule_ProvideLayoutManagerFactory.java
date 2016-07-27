package com.hotellook.ui.screen.favorite.dependencies;

import android.app.Application;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.LayoutManagerWrapper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class FavoritesModule_ProvideLayoutManagerFactory implements Factory<LayoutManagerWrapper> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> contextProvider;
    private final FavoritesModule module;

    static {
        $assertionsDisabled = !FavoritesModule_ProvideLayoutManagerFactory.class.desiredAssertionStatus();
    }

    public FavoritesModule_ProvideLayoutManagerFactory(FavoritesModule module, Provider<Application> contextProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || contextProvider != null) {
                this.contextProvider = contextProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public LayoutManagerWrapper get() {
        return (LayoutManagerWrapper) Preconditions.checkNotNull(this.module.provideLayoutManager((Application) this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<LayoutManagerWrapper> create(FavoritesModule module, Provider<Application> contextProvider) {
        return new FavoritesModule_ProvideLayoutManagerFactory(module, contextProvider);
    }
}
