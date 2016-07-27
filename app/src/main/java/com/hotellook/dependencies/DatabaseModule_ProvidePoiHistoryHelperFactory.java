package com.hotellook.dependencies;

import com.hotellook.db.DatabaseHelper;
import com.hotellook.db.PoiHistoryDBHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DatabaseModule_ProvidePoiHistoryHelperFactory implements Factory<PoiHistoryDBHelper> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<DatabaseHelper> databaseHelperProvider;
    private final DatabaseModule module;

    static {
        $assertionsDisabled = !DatabaseModule_ProvidePoiHistoryHelperFactory.class.desiredAssertionStatus();
    }

    public DatabaseModule_ProvidePoiHistoryHelperFactory(DatabaseModule module, Provider<DatabaseHelper> databaseHelperProvider) {
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

    public PoiHistoryDBHelper get() {
        return (PoiHistoryDBHelper) Preconditions.checkNotNull(this.module.providePoiHistoryHelper((DatabaseHelper) this.databaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PoiHistoryDBHelper> create(DatabaseModule module, Provider<DatabaseHelper> databaseHelperProvider) {
        return new DatabaseModule_ProvidePoiHistoryHelperFactory(module, databaseHelperProvider);
    }
}
