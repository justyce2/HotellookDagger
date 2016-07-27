package com.hotellook.ui.screen.favorite.dependencies;

import android.content.Context;
import android.support.annotation.Nullable;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class FavoritesModule_ProvideItemLayoutParamsGeneratorFactory implements Factory<ItemLayoutParamsGenerator> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Context> contextProvider;
    private final FavoritesModule module;

    static {
        $assertionsDisabled = !FavoritesModule_ProvideItemLayoutParamsGeneratorFactory.class.desiredAssertionStatus();
    }

    public FavoritesModule_ProvideItemLayoutParamsGeneratorFactory(FavoritesModule module, Provider<Context> contextProvider) {
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
    public ItemLayoutParamsGenerator get() {
        return this.module.provideItemLayoutParamsGenerator((Context) this.contextProvider.get());
    }

    public static Factory<ItemLayoutParamsGenerator> create(FavoritesModule module, Provider<Context> contextProvider) {
        return new FavoritesModule_ProvideItemLayoutParamsGeneratorFactory(module, contextProvider);
    }
}
