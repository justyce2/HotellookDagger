package com.hotellook.ui.screen.currency;

import com.hotellook.currency.CurrencyRepository;
import com.hotellook.utils.CommonPreferences;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class CurrenciesPresenter_Factory implements Factory<CurrenciesPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<CommonPreferences> commonPreferencesProvider;
    private final MembersInjector<CurrenciesPresenter> currenciesPresenterMembersInjector;
    private final Provider<CurrencyRepository> currencyRepositoryProvider;

    static {
        $assertionsDisabled = !CurrenciesPresenter_Factory.class.desiredAssertionStatus();
    }

    public CurrenciesPresenter_Factory(MembersInjector<CurrenciesPresenter> currenciesPresenterMembersInjector, Provider<CurrencyRepository> currencyRepositoryProvider, Provider<CommonPreferences> commonPreferencesProvider) {
        if ($assertionsDisabled || currenciesPresenterMembersInjector != null) {
            this.currenciesPresenterMembersInjector = currenciesPresenterMembersInjector;
            if ($assertionsDisabled || currencyRepositoryProvider != null) {
                this.currencyRepositoryProvider = currencyRepositoryProvider;
                if ($assertionsDisabled || commonPreferencesProvider != null) {
                    this.commonPreferencesProvider = commonPreferencesProvider;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public CurrenciesPresenter get() {
        return (CurrenciesPresenter) MembersInjectors.injectMembers(this.currenciesPresenterMembersInjector, new CurrenciesPresenter((CurrencyRepository) this.currencyRepositoryProvider.get(), (CommonPreferences) this.commonPreferencesProvider.get()));
    }

    public static Factory<CurrenciesPresenter> create(MembersInjector<CurrenciesPresenter> currenciesPresenterMembersInjector, Provider<CurrencyRepository> currencyRepositoryProvider, Provider<CommonPreferences> commonPreferencesProvider) {
        return new CurrenciesPresenter_Factory(currenciesPresenterMembersInjector, currencyRepositoryProvider, commonPreferencesProvider);
    }
}
