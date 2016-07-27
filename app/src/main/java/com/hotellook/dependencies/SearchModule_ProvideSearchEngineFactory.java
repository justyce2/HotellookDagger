package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.search.DumpCacher;
import com.hotellook.search.SearchEngine;
import com.hotellook.utils.EventBus;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SearchModule_ProvideSearchEngineFactory implements Factory<SearchEngine> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final Provider<DumpCacher> dumpCacherProvider;
    private final Provider<EventBus> eventBusProvider;
    private final Provider<FavoritesRepository> favoritesRepositoryProvider;
    private final SearchModule module;

    static {
        $assertionsDisabled = !SearchModule_ProvideSearchEngineFactory.class.desiredAssertionStatus();
    }

    public SearchModule_ProvideSearchEngineFactory(SearchModule module, Provider<Application> appProvider, Provider<DumpCacher> dumpCacherProvider, Provider<EventBus> eventBusProvider, Provider<FavoritesRepository> favoritesRepositoryProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || appProvider != null) {
                this.appProvider = appProvider;
                if ($assertionsDisabled || dumpCacherProvider != null) {
                    this.dumpCacherProvider = dumpCacherProvider;
                    if ($assertionsDisabled || eventBusProvider != null) {
                        this.eventBusProvider = eventBusProvider;
                        if ($assertionsDisabled || favoritesRepositoryProvider != null) {
                            this.favoritesRepositoryProvider = favoritesRepositoryProvider;
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

    public SearchEngine get() {
        return (SearchEngine) Preconditions.checkNotNull(this.module.provideSearchEngine((Application) this.appProvider.get(), (DumpCacher) this.dumpCacherProvider.get(), (EventBus) this.eventBusProvider.get(), (FavoritesRepository) this.favoritesRepositoryProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SearchEngine> create(SearchModule module, Provider<Application> appProvider, Provider<DumpCacher> dumpCacherProvider, Provider<EventBus> eventBusProvider, Provider<FavoritesRepository> favoritesRepositoryProvider) {
        return new SearchModule_ProvideSearchEngineFactory(module, appProvider, dumpCacherProvider, eventBusProvider, favoritesRepositoryProvider);
    }
}
