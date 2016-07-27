package com.hotellook.ui.screen.favorite.dependencies;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.dependencies.ActivityModule;
import com.hotellook.dependencies.ActivityModule_ProvideContextFactory;
import com.hotellook.dependencies.ActivityModule_ProvideMainActivityFactory;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.interactor.favorites.FavoritesInteractor;
import com.hotellook.interactor.favorites.FavoritesInteractor_Factory;
import com.hotellook.search.SearchKeeper;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.favorite.presenter.FavoritesPresenter;
import com.hotellook.ui.screen.favorite.presenter.FavoritesPresenter_Factory;
import com.hotellook.ui.screen.favorite.router.FavoritesRouter;
import com.hotellook.ui.screen.favorite.router.FavoritesRouter_Factory;
import com.hotellook.ui.screen.favorite.view.FavoriteHotelsAdapter;
import com.hotellook.ui.screen.favorite.view.FavoriteHotelsAdapter_Factory;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.LayoutManagerWrapper;
import com.hotellook.utils.CommonPreferences;
import com.hotellook.utils.EventBus;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerFavoritesComponent implements FavoritesComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Provider<Application> appProvider;
    private Provider<CurrencyRepository> currencyRepositoryProvider;
    private Provider<EventBus> eventBusProvider;
    private Provider<FavoriteHotelsAdapter> favoriteHotelsAdapterProvider;
    private Provider<FavoritesInteractor> favoritesInteractorProvider;
    private Provider<FavoritesPresenter> favoritesPresenterProvider;
    private Provider<FavoritesRepository> favoritesRepositoryProvider;
    private Provider<FavoritesRouter> favoritesRouterProvider;
    private Provider<CommonPreferences> getCommonPreferencesProvider;
    private Provider<HotelDataSource> hotelDataSourceProvider;
    private Provider<Context> provideContextProvider;
    private Provider<ItemLayoutParamsGenerator> provideItemLayoutParamsGeneratorProvider;
    private Provider<LayoutManagerWrapper> provideLayoutManagerProvider;
    private Provider<ItemAnimator> provideListItemAnimatorProvider;
    private Provider<MainActivity> provideMainActivityProvider;
    private Provider<SearchKeeper> searchKeeperProvider;

    /* renamed from: com.hotellook.ui.screen.favorite.dependencies.DaggerFavoritesComponent.1 */
    class C12451 implements Factory<FavoritesRepository> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C12451(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public FavoritesRepository get() {
            return (FavoritesRepository) Preconditions.checkNotNull(this.hotellookComponent.favoritesRepository(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.favorite.dependencies.DaggerFavoritesComponent.2 */
    class C12462 implements Factory<SearchKeeper> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C12462(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public SearchKeeper get() {
            return (SearchKeeper) Preconditions.checkNotNull(this.hotellookComponent.searchKeeper(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.favorite.dependencies.DaggerFavoritesComponent.3 */
    class C12473 implements Factory<EventBus> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C12473(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public EventBus get() {
            return (EventBus) Preconditions.checkNotNull(this.hotellookComponent.eventBus(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.favorite.dependencies.DaggerFavoritesComponent.4 */
    class C12484 implements Factory<HotelDataSource> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C12484(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public HotelDataSource get() {
            return (HotelDataSource) Preconditions.checkNotNull(this.hotellookComponent.hotelDataSource(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.favorite.dependencies.DaggerFavoritesComponent.5 */
    class C12495 implements Factory<CommonPreferences> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C12495(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public CommonPreferences get() {
            return (CommonPreferences) Preconditions.checkNotNull(this.hotellookComponent.getCommonPreferences(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.favorite.dependencies.DaggerFavoritesComponent.6 */
    class C12506 implements Factory<CurrencyRepository> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C12506(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public CurrencyRepository get() {
            return (CurrencyRepository) Preconditions.checkNotNull(this.hotellookComponent.currencyRepository(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.favorite.dependencies.DaggerFavoritesComponent.7 */
    class C12517 implements Factory<Application> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C12517(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public Application get() {
            return (Application) Preconditions.checkNotNull(this.hotellookComponent.app(), "Cannot return null from a non-@Nullable component method");
        }
    }

    public static final class Builder {
        private ActivityModule activityModule;
        private FavoritesModule favoritesModule;
        private HotellookComponent hotellookComponent;

        private Builder() {
        }

        public FavoritesComponent build() {
            if (this.activityModule == null) {
                throw new IllegalStateException(ActivityModule.class.getCanonicalName() + " must be set");
            }
            if (this.favoritesModule == null) {
                this.favoritesModule = new FavoritesModule();
            }
            if (this.hotellookComponent != null) {
                return new DaggerFavoritesComponent();
            }
            throw new IllegalStateException(HotellookComponent.class.getCanonicalName() + " must be set");
        }

        public Builder favoritesModule(FavoritesModule favoritesModule) {
            this.favoritesModule = (FavoritesModule) Preconditions.checkNotNull(favoritesModule);
            return this;
        }

        public Builder activityModule(ActivityModule activityModule) {
            this.activityModule = (ActivityModule) Preconditions.checkNotNull(activityModule);
            return this;
        }

        public Builder hotellookComponent(HotellookComponent hotellookComponent) {
            this.hotellookComponent = (HotellookComponent) Preconditions.checkNotNull(hotellookComponent);
            return this;
        }
    }

    static {
        $assertionsDisabled = !DaggerFavoritesComponent.class.desiredAssertionStatus();
    }

    private DaggerFavoritesComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.favoritesRepositoryProvider = new C12451(builder);
        this.favoritesInteractorProvider = FavoritesInteractor_Factory.create(this.favoritesRepositoryProvider);
        this.searchKeeperProvider = new C12462(builder);
        this.eventBusProvider = new C12473(builder);
        this.hotelDataSourceProvider = new C12484(builder);
        this.getCommonPreferencesProvider = new C12495(builder);
        this.currencyRepositoryProvider = new C12506(builder);
        this.provideMainActivityProvider = ActivityModule_ProvideMainActivityFactory.create(builder.activityModule);
        this.favoritesRouterProvider = FavoritesRouter_Factory.create(this.provideMainActivityProvider);
        this.favoritesPresenterProvider = FavoritesPresenter_Factory.create(MembersInjectors.noOp(), this.favoritesInteractorProvider, this.searchKeeperProvider, this.eventBusProvider, this.hotelDataSourceProvider, this.getCommonPreferencesProvider, this.currencyRepositoryProvider, this.favoritesRouterProvider);
        this.appProvider = new C12517(builder);
        this.provideLayoutManagerProvider = FavoritesModule_ProvideLayoutManagerFactory.create(builder.favoritesModule, this.appProvider);
        this.provideContextProvider = ActivityModule_ProvideContextFactory.create(builder.activityModule);
        this.provideItemLayoutParamsGeneratorProvider = FavoritesModule_ProvideItemLayoutParamsGeneratorFactory.create(builder.favoritesModule, this.provideContextProvider);
        this.favoriteHotelsAdapterProvider = FavoriteHotelsAdapter_Factory.create(MembersInjectors.noOp(), this.provideContextProvider, this.getCommonPreferencesProvider, this.provideItemLayoutParamsGeneratorProvider, this.searchKeeperProvider);
        this.provideListItemAnimatorProvider = FavoritesModule_ProvideListItemAnimatorFactory.create(builder.favoritesModule, this.appProvider);
    }

    public FavoritesPresenter presenter() {
        return (FavoritesPresenter) this.favoritesPresenterProvider.get();
    }

    public LayoutManagerWrapper layoutManager() {
        return (LayoutManagerWrapper) this.provideLayoutManagerProvider.get();
    }

    public FavoriteHotelsAdapter hotelsAdapter() {
        return (FavoriteHotelsAdapter) this.favoriteHotelsAdapterProvider.get();
    }

    public ItemAnimator listItemAnimator() {
        return (ItemAnimator) this.provideListItemAnimatorProvider.get();
    }
}
