package com.hotellook.ui.screen.favorite.dependencies;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class FavoritesModule_ProvideListItemAnimatorFactory implements Factory<ItemAnimator> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> contextProvider;
    private final FavoritesModule module;

    static {
        $assertionsDisabled = !FavoritesModule_ProvideListItemAnimatorFactory.class.desiredAssertionStatus();
    }

    public FavoritesModule_ProvideListItemAnimatorFactory(FavoritesModule module, Provider<Application> contextProvider) {
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

    @Nullable
    public ItemAnimator get() {
        return this.module.provideListItemAnimator((Application) this.contextProvider.get());
    }

    public static Factory<ItemAnimator> create(FavoritesModule module, Provider<Application> contextProvider) {
        return new FavoritesModule_ProvideListItemAnimatorFactory(module, contextProvider);
    }
}
