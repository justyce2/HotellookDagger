package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.api.data.SearchFormPreferences;
import com.hotellook.api.exchange.CurrencyConverter;
import com.hotellook.api.trackers.RateDialogConditionsTracker;
import com.hotellook.api.trackers.RequestFlagsHelperTracker;
import com.hotellook.api.trackers.SearchTracker;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.utils.CommonPreferences;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class StorageModule {
    @Provides
    public SearchFormPreferences provideSearchFormPreferences(Application app) {
        return new SearchFormPreferences(app);
    }

    @Provides
    public SearchFormData provideSearchFormData(Application app, SearchFormPreferences preferences) {
        return new SearchFormData(app, preferences);
    }

    @Singleton
    @Provides
    public CommonPreferences provideCommonPreferences(Application app) {
        return new CommonPreferences(app);
    }

    @Provides
    public CurrencyRepository provideCurrencyRepository(CommonPreferences commonPreferences) {
        return new CurrencyRepository(commonPreferences);
    }

    @Provides
    public AppVersionRepository provideAppVersionRepository(Application app) {
        return new AppVersionRepository(app);
    }

    @Singleton
    @Provides
    public SearchTracker provideSearchTracker(Application app) {
        return new SearchTracker(app);
    }

    @Singleton
    @Provides
    public RateDialogConditionsTracker provideRateDialogConditionsTracker(CommonPreferences preferences) {
        return new RateDialogConditionsTracker(preferences);
    }

    @Singleton
    @Provides
    public RequestFlagsHelperTracker provideRequestFlagsHelperTracker() {
        return new RequestFlagsHelperTracker();
    }

    @Singleton
    @Provides
    public CurrencyConverter currencyConverter() {
        return new CurrencyConverter();
    }
}
