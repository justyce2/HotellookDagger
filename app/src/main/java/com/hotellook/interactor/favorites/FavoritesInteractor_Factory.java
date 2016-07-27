package com.hotellook.interactor.favorites;

import com.hotellook.db.FavoritesRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class FavoritesInteractor_Factory implements Factory<FavoritesInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<FavoritesRepository> favoritesRepositoryProvider;

    static {
        $assertionsDisabled = !FavoritesInteractor_Factory.class.desiredAssertionStatus();
    }

    public FavoritesInteractor_Factory(Provider<FavoritesRepository> favoritesRepositoryProvider) {
        if ($assertionsDisabled || favoritesRepositoryProvider != null) {
            this.favoritesRepositoryProvider = favoritesRepositoryProvider;
            return;
        }
        throw new AssertionError();
    }

    public FavoritesInteractor get() {
        return new FavoritesInteractor((FavoritesRepository) this.favoritesRepositoryProvider.get());
    }

    public static Factory<FavoritesInteractor> create(Provider<FavoritesRepository> favoritesRepositoryProvider) {
        return new FavoritesInteractor_Factory(favoritesRepositoryProvider);
    }
}
