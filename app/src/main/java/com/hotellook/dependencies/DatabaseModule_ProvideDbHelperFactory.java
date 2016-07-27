package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.db.DatabaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DatabaseModule_ProvideDbHelperFactory implements Factory<DatabaseHelper> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final DatabaseModule module;

    static {
        $assertionsDisabled = !DatabaseModule_ProvideDbHelperFactory.class.desiredAssertionStatus();
    }

    public DatabaseModule_ProvideDbHelperFactory(DatabaseModule module, Provider<Application> appProvider) {
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

    public DatabaseHelper get() {
        return (DatabaseHelper) Preconditions.checkNotNull(this.module.provideDbHelper((Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DatabaseHelper> create(DatabaseModule module, Provider<Application> appProvider) {
        return new DatabaseModule_ProvideDbHelperFactory(module, appProvider);
    }
}
