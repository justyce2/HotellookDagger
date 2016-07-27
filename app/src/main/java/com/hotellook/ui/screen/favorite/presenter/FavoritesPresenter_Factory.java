package com.hotellook.ui.screen.favorite.presenter;

import com.hotellook.currency.CurrencyRepository;
import com.hotellook.interactor.favorites.FavoritesInteractor;
import com.hotellook.search.SearchKeeper;
import com.hotellook.ui.screen.favorite.router.FavoritesRouter;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.utils.CommonPreferences;
import com.hotellook.utils.EventBus;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class FavoritesPresenter_Factory implements Factory<FavoritesPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<CommonPreferences> commonPreferencesProvider;
    private final Provider<CurrencyRepository> currencyRepositoryProvider;
    private final Provider<EventBus> eventBusProvider;
    private final Provider<FavoritesInteractor> favoritesInteractorProvider;
    private final MembersInjector<FavoritesPresenter> favoritesPresenterMembersInjector;
    private final Provider<HotelDataSource> hotelDataSourceProvider;
    private final Provider<FavoritesRouter> routerProvider;
    private final Provider<SearchKeeper> searchKeeperProvider;

    static {
        $assertionsDisabled = !FavoritesPresenter_Factory.class.desiredAssertionStatus();
    }

    public FavoritesPresenter_Factory(MembersInjector<FavoritesPresenter> favoritesPresenterMembersInjector, Provider<FavoritesInteractor> favoritesInteractorProvider, Provider<SearchKeeper> searchKeeperProvider, Provider<EventBus> eventBusProvider, Provider<HotelDataSource> hotelDataSourceProvider, Provider<CommonPreferences> commonPreferencesProvider, Provider<CurrencyRepository> currencyRepositoryProvider, Provider<FavoritesRouter> routerProvider) {
        if ($assertionsDisabled || favoritesPresenterMembersInjector != null) {
            this.favoritesPresenterMembersInjector = favoritesPresenterMembersInjector;
            if ($assertionsDisabled || favoritesInteractorProvider != null) {
                this.favoritesInteractorProvider = favoritesInteractorProvider;
                if ($assertionsDisabled || searchKeeperProvider != null) {
                    this.searchKeeperProvider = searchKeeperProvider;
                    if ($assertionsDisabled || eventBusProvider != null) {
                        this.eventBusProvider = eventBusProvider;
                        if ($assertionsDisabled || hotelDataSourceProvider != null) {
                            this.hotelDataSourceProvider = hotelDataSourceProvider;
                            if ($assertionsDisabled || commonPreferencesProvider != null) {
                                this.commonPreferencesProvider = commonPreferencesProvider;
                                if ($assertionsDisabled || currencyRepositoryProvider != null) {
                                    this.currencyRepositoryProvider = currencyRepositoryProvider;
                                    if ($assertionsDisabled || routerProvider != null) {
                                        this.routerProvider = routerProvider;
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
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public FavoritesPresenter get() {
        return (FavoritesPresenter) MembersInjectors.injectMembers(this.favoritesPresenterMembersInjector, new FavoritesPresenter((FavoritesInteractor) this.favoritesInteractorProvider.get(), (SearchKeeper) this.searchKeeperProvider.get(), (EventBus) this.eventBusProvider.get(), (HotelDataSource) this.hotelDataSourceProvider.get(), (CommonPreferences) this.commonPreferencesProvider.get(), (CurrencyRepository) this.currencyRepositoryProvider.get(), (FavoritesRouter) this.routerProvider.get()));
    }

    public static Factory<FavoritesPresenter> create(MembersInjector<FavoritesPresenter> favoritesPresenterMembersInjector, Provider<FavoritesInteractor> favoritesInteractorProvider, Provider<SearchKeeper> searchKeeperProvider, Provider<EventBus> eventBusProvider, Provider<HotelDataSource> hotelDataSourceProvider, Provider<CommonPreferences> commonPreferencesProvider, Provider<CurrencyRepository> currencyRepositoryProvider, Provider<FavoritesRouter> routerProvider) {
        return new FavoritesPresenter_Factory(favoritesPresenterMembersInjector, favoritesInteractorProvider, searchKeeperProvider, eventBusProvider, hotelDataSourceProvider, commonPreferencesProvider, currencyRepositoryProvider, routerProvider);
    }
}
