package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.ui.screen.searchresults.adapters.cards.controller.Cards;
import com.hotellook.utils.CommonPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SearchModule_ProvideCardsFactory implements Factory<Cards> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final Provider<CommonPreferences> commonPreferencesProvider;
    private final SearchModule module;
    private final Provider<PersistentFilters> persistentFiltersProvider;

    static {
        $assertionsDisabled = !SearchModule_ProvideCardsFactory.class.desiredAssertionStatus();
    }

    public SearchModule_ProvideCardsFactory(SearchModule module, Provider<Application> appProvider, Provider<CommonPreferences> commonPreferencesProvider, Provider<PersistentFilters> persistentFiltersProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || appProvider != null) {
                this.appProvider = appProvider;
                if ($assertionsDisabled || commonPreferencesProvider != null) {
                    this.commonPreferencesProvider = commonPreferencesProvider;
                    if ($assertionsDisabled || persistentFiltersProvider != null) {
                        this.persistentFiltersProvider = persistentFiltersProvider;
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

    public Cards get() {
        return (Cards) Preconditions.checkNotNull(this.module.provideCards((Application) this.appProvider.get(), (CommonPreferences) this.commonPreferencesProvider.get(), (PersistentFilters) this.persistentFiltersProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Cards> create(SearchModule module, Provider<Application> appProvider, Provider<CommonPreferences> commonPreferencesProvider, Provider<PersistentFilters> persistentFiltersProvider) {
        return new SearchModule_ProvideCardsFactory(module, appProvider, commonPreferencesProvider, persistentFiltersProvider);
    }
}
