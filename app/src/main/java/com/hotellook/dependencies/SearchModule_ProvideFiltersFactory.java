package com.hotellook.dependencies;

import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SearchModule_ProvideFiltersFactory implements Factory<Filters> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final SearchModule module;
    private final Provider<PersistentFilters> persistentFiltersProvider;

    static {
        $assertionsDisabled = !SearchModule_ProvideFiltersFactory.class.desiredAssertionStatus();
    }

    public SearchModule_ProvideFiltersFactory(SearchModule module, Provider<PersistentFilters> persistentFiltersProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || persistentFiltersProvider != null) {
                this.persistentFiltersProvider = persistentFiltersProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public Filters get() {
        return (Filters) Preconditions.checkNotNull(this.module.provideFilters((PersistentFilters) this.persistentFiltersProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Filters> create(SearchModule module, Provider<PersistentFilters> persistentFiltersProvider) {
        return new SearchModule_ProvideFiltersFactory(module, persistentFiltersProvider);
    }
}
