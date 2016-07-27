package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.filters.PersistentFilters;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SearchModule_ProvideGlobalFiltersFactory implements Factory<PersistentFilters> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final SearchModule module;

    static {
        $assertionsDisabled = !SearchModule_ProvideGlobalFiltersFactory.class.desiredAssertionStatus();
    }

    public SearchModule_ProvideGlobalFiltersFactory(SearchModule module, Provider<Application> appProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || appProvider != null) {
                this.appProvider = appProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PersistentFilters get() {
        return (PersistentFilters) Preconditions.checkNotNull(this.module.provideGlobalFilters((Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PersistentFilters> create(SearchModule module, Provider<Application> appProvider) {
        return new SearchModule_ProvideGlobalFiltersFactory(module, appProvider);
    }
}
