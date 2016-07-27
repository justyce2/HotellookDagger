package com.hotellook.dependencies;

import com.hotellook.db.DatabaseHelper;
import com.hotellook.db.FavoritesRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DatabaseModule_ProvideFavoritesRepositoryFactory implements Factory<FavoritesRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<DatabaseHelper> databaseHelperProvider;
    private final DatabaseModule module;

    static {
        $assertionsDisabled = !DatabaseModule_ProvideFavoritesRepositoryFactory.class.desiredAssertionStatus();
    }

    public DatabaseModule_ProvideFavoritesRepositoryFactory(DatabaseModule module, Provider<DatabaseHelper> databaseHelperProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || databaseHelperProvider != null) {
                this.databaseHelperProvider = databaseHelperProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public FavoritesRepository get() {
        return (FavoritesRepository) Preconditions.checkNotNull(this.module.provideFavoritesRepository((DatabaseHelper) this.databaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<FavoritesRepository> create(DatabaseModule module, Provider<DatabaseHelper> databaseHelperProvider) {
        return new DatabaseModule_ProvideFavoritesRepositoryFactory(module, databaseHelperProvider);
    }
}
