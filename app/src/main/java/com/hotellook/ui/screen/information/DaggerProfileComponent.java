package com.hotellook.ui.screen.information;

import com.hotellook.currency.CurrencyRepository;
import com.hotellook.dependencies.AppVersionRepository;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.SearchKeeper;
import com.hotellook.utils.CommonPreferences;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerProfileComponent implements ProfileComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Provider<AppVersionRepository> appVersionRepositoryProvider;
    private Provider<CurrencyRepository> currencyRepositoryProvider;
    private Provider<CommonPreferences> getCommonPreferencesProvider;
    private Provider<PersistentFilters> persistentFiltersProvider;
    private Provider<ProfilePresenter> profilePresenterProvider;
    private Provider<SearchKeeper> searchKeeperProvider;

    /* renamed from: com.hotellook.ui.screen.information.DaggerProfileComponent.1 */
    class C13371 implements Factory<CommonPreferences> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C13371(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public CommonPreferences get() {
            return (CommonPreferences) Preconditions.checkNotNull(this.hotellookComponent.getCommonPreferences(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.DaggerProfileComponent.2 */
    class C13382 implements Factory<SearchKeeper> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C13382(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public SearchKeeper get() {
            return (SearchKeeper) Preconditions.checkNotNull(this.hotellookComponent.searchKeeper(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.DaggerProfileComponent.3 */
    class C13393 implements Factory<PersistentFilters> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C13393(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public PersistentFilters get() {
            return (PersistentFilters) Preconditions.checkNotNull(this.hotellookComponent.persistentFilters(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.DaggerProfileComponent.4 */
    class C13404 implements Factory<CurrencyRepository> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C13404(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public CurrencyRepository get() {
            return (CurrencyRepository) Preconditions.checkNotNull(this.hotellookComponent.currencyRepository(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.DaggerProfileComponent.5 */
    class C13415 implements Factory<AppVersionRepository> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C13415(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public AppVersionRepository get() {
            return (AppVersionRepository) Preconditions.checkNotNull(this.hotellookComponent.appVersionRepository(), "Cannot return null from a non-@Nullable component method");
        }
    }

    public static final class Builder {
        private HotellookComponent hotellookComponent;

        private Builder() {
        }

        public ProfileComponent build() {
            if (this.hotellookComponent != null) {
                return new DaggerProfileComponent();
            }
            throw new IllegalStateException(HotellookComponent.class.getCanonicalName() + " must be set");
        }

        public Builder hotellookComponent(HotellookComponent hotellookComponent) {
            this.hotellookComponent = (HotellookComponent) Preconditions.checkNotNull(hotellookComponent);
            return this;
        }
    }

    static {
        $assertionsDisabled = !DaggerProfileComponent.class.desiredAssertionStatus();
    }

    private DaggerProfileComponent(Builder builder) {
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
        this.getCommonPreferencesProvider = new C13371(builder);
        this.searchKeeperProvider = new C13382(builder);
        this.persistentFiltersProvider = new C13393(builder);
        this.currencyRepositoryProvider = new C13404(builder);
        this.appVersionRepositoryProvider = new C13415(builder);
        this.profilePresenterProvider = ProfilePresenter_Factory.create(MembersInjectors.noOp(), this.getCommonPreferencesProvider, this.searchKeeperProvider, this.persistentFiltersProvider, this.currencyRepositoryProvider, this.appVersionRepositoryProvider);
    }

    public ProfilePresenter presenter() {
        return (ProfilePresenter) this.profilePresenterProvider.get();
    }
}
