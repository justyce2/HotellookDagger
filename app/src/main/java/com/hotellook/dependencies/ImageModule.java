package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.ui.screen.searchresults.CityImageHierarchyFactory;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ImageModule {
    @Singleton
    @Provides
    public CityImageHierarchyFactory provideCityImageHierarchyFactory(Application app) {
        return new CityImageHierarchyFactory(app.getResources());
    }
}
