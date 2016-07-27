package com.hotellook.ui.screen.favorite.view;

import android.content.Context;
import com.hotellook.search.SearchKeeper;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import com.hotellook.utils.CommonPreferences;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class FavoriteHotelsAdapter_Factory implements Factory<FavoriteHotelsAdapter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<CommonPreferences> commonPreferencesProvider;
    private final Provider<Context> contextProvider;
    private final MembersInjector<FavoriteHotelsAdapter> favoriteHotelsAdapterMembersInjector;
    private final Provider<ItemLayoutParamsGenerator> itemLayoutParamsGeneratorProvider;
    private final Provider<SearchKeeper> searchKeeperProvider;

    static {
        $assertionsDisabled = !FavoriteHotelsAdapter_Factory.class.desiredAssertionStatus();
    }

    public FavoriteHotelsAdapter_Factory(MembersInjector<FavoriteHotelsAdapter> favoriteHotelsAdapterMembersInjector, Provider<Context> contextProvider, Provider<CommonPreferences> commonPreferencesProvider, Provider<ItemLayoutParamsGenerator> itemLayoutParamsGeneratorProvider, Provider<SearchKeeper> searchKeeperProvider) {
        if ($assertionsDisabled || favoriteHotelsAdapterMembersInjector != null) {
            this.favoriteHotelsAdapterMembersInjector = favoriteHotelsAdapterMembersInjector;
            if ($assertionsDisabled || contextProvider != null) {
                this.contextProvider = contextProvider;
                if ($assertionsDisabled || commonPreferencesProvider != null) {
                    this.commonPreferencesProvider = commonPreferencesProvider;
                    if ($assertionsDisabled || itemLayoutParamsGeneratorProvider != null) {
                        this.itemLayoutParamsGeneratorProvider = itemLayoutParamsGeneratorProvider;
                        if ($assertionsDisabled || searchKeeperProvider != null) {
                            this.searchKeeperProvider = searchKeeperProvider;
                            return;
                        }
                        throw new AssertionError();
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public FavoriteHotelsAdapter get() {
        return (FavoriteHotelsAdapter) MembersInjectors.injectMembers(this.favoriteHotelsAdapterMembersInjector, new FavoriteHotelsAdapter((Context) this.contextProvider.get(), (CommonPreferences) this.commonPreferencesProvider.get(), (ItemLayoutParamsGenerator) this.itemLayoutParamsGeneratorProvider.get(), (SearchKeeper) this.searchKeeperProvider.get()));
    }

    public static Factory<FavoriteHotelsAdapter> create(MembersInjector<FavoriteHotelsAdapter> favoriteHotelsAdapterMembersInjector, Provider<Context> contextProvider, Provider<CommonPreferences> commonPreferencesProvider, Provider<ItemLayoutParamsGenerator> itemLayoutParamsGeneratorProvider, Provider<SearchKeeper> searchKeeperProvider) {
        return new FavoriteHotelsAdapter_Factory(favoriteHotelsAdapterMembersInjector, contextProvider, commonPreferencesProvider, itemLayoutParamsGeneratorProvider, searchKeeperProvider);
    }
}
