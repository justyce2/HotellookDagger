package com.hotellook.ui.screen.information;

import com.hotellook.currency.CurrencyRepository;
import com.hotellook.dependencies.AppVersionRepository;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.SearchKeeper;
import com.hotellook.utils.CommonPreferences;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class ProfilePresenter_Factory implements Factory<ProfilePresenter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<AppVersionRepository> appVersionRepositoryProvider;
    private final Provider<CommonPreferences> commonPreferencesProvider;
    private final Provider<CurrencyRepository> currencyRepositoryProvider;
    private final Provider<PersistentFilters> persistentFiltersProvider;
    private final MembersInjector<ProfilePresenter> profilePresenterMembersInjector;
    private final Provider<SearchKeeper> searchKeeperProvider;

    static {
        $assertionsDisabled = !ProfilePresenter_Factory.class.desiredAssertionStatus();
    }

    public ProfilePresenter_Factory(MembersInjector<ProfilePresenter> profilePresenterMembersInjector, Provider<CommonPreferences> commonPreferencesProvider, Provider<SearchKeeper> searchKeeperProvider, Provider<PersistentFilters> persistentFiltersProvider, Provider<CurrencyRepository> currencyRepositoryProvider, Provider<AppVersionRepository> appVersionRepositoryProvider) {
        if ($assertionsDisabled || profilePresenterMembersInjector != null) {
            this.profilePresenterMembersInjector = profilePresenterMembersInjector;
            if ($assertionsDisabled || commonPreferencesProvider != null) {
                this.commonPreferencesProvider = commonPreferencesProvider;
                if ($assertionsDisabled || searchKeeperProvider != null) {
                    this.searchKeeperProvider = searchKeeperProvider;
                    if ($assertionsDisabled || persistentFiltersProvider != null) {
                        this.persistentFiltersProvider = persistentFiltersProvider;
                        if ($assertionsDisabled || currencyRepositoryProvider != null) {
                            this.currencyRepositoryProvider = currencyRepositoryProvider;
                            if ($assertionsDisabled || appVersionRepositoryProvider != null) {
                                this.appVersionRepositoryProvider = appVersionRepositoryProvider;
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
        throw new AssertionError();
    }

    public ProfilePresenter get() {
        return (ProfilePresenter) MembersInjectors.injectMembers(this.profilePresenterMembersInjector, new ProfilePresenter((CommonPreferences) this.commonPreferencesProvider.get(), (SearchKeeper) this.searchKeeperProvider.get(), (PersistentFilters) this.persistentFiltersProvider.get(), (CurrencyRepository) this.currencyRepositoryProvider.get(), (AppVersionRepository) this.appVersionRepositoryProvider.get()));
    }

    public static Factory<ProfilePresenter> create(MembersInjector<ProfilePresenter> profilePresenterMembersInjector, Provider<CommonPreferences> commonPreferencesProvider, Provider<SearchKeeper> searchKeeperProvider, Provider<PersistentFilters> persistentFiltersProvider, Provider<CurrencyRepository> currencyRepositoryProvider, Provider<AppVersionRepository> appVersionRepositoryProvider) {
        return new ProfilePresenter_Factory(profilePresenterMembersInjector, commonPreferencesProvider, searchKeeperProvider, persistentFiltersProvider, currencyRepositoryProvider, appVersionRepositoryProvider);
    }
}
