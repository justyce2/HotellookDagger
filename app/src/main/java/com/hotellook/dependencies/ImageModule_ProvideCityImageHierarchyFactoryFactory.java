package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.ui.screen.searchresults.CityImageHierarchyFactory;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ImageModule_ProvideCityImageHierarchyFactoryFactory implements Factory<CityImageHierarchyFactory> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final ImageModule module;

    static {
        $assertionsDisabled = !ImageModule_ProvideCityImageHierarchyFactoryFactory.class.desiredAssertionStatus();
    }

    public ImageModule_ProvideCityImageHierarchyFactoryFactory(ImageModule module, Provider<Application> appProvider) {
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

    public CityImageHierarchyFactory get() {
        return (CityImageHierarchyFactory) Preconditions.checkNotNull(this.module.provideCityImageHierarchyFactory((Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<CityImageHierarchyFactory> create(ImageModule module, Provider<Application> appProvider) {
        return new ImageModule_ProvideCityImageHierarchyFactoryFactory(module, appProvider);
    }
}
