package com.hotellook.dependencies;

import com.hotellook.db.FavoritesRepository;
import com.hotellook.db.SearchDestinationFavoritesCache;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DatabaseModule_ProvideLocationFavoritesCacheFactory implements Factory<SearchDestinationFavoritesCache> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final DatabaseModule module;
    private final Provider<FavoritesRepository> repositoryProvider;

    static {
        $assertionsDisabled = !DatabaseModule_ProvideLocationFavoritesCacheFactory.class.desiredAssertionStatus();
    }

    public DatabaseModule_ProvideLocationFavoritesCacheFactory(DatabaseModule module, Provider<FavoritesRepository> repositoryProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || repositoryProvider != null) {
                this.repositoryProvider = repositoryProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public SearchDestinationFavoritesCache get() {
        return (SearchDestinationFavoritesCache) Preconditions.checkNotNull(this.module.provideLocationFavoritesCache((FavoritesRepository) this.repositoryProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SearchDestinationFavoritesCache> create(DatabaseModule module, Provider<FavoritesRepository> repositoryProvider) {
        return new DatabaseModule_ProvideLocationFavoritesCacheFactory(module, repositoryProvider);
    }
}
