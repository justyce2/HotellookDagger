package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.search.DumpCacher;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideDumpCacherFactory implements Factory<DumpCacher> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideDumpCacherFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideDumpCacherFactory(DataSourceModule module, Provider<Application> appProvider) {
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

    public DumpCacher get() {
        return (DumpCacher) Preconditions.checkNotNull(this.module.provideDumpCacher((Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DumpCacher> create(DataSourceModule module, Provider<Application> appProvider) {
        return new DataSourceModule_ProvideDumpCacherFactory(module, appProvider);
    }
}
