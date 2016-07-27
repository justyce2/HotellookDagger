package com.hotellook.ui.screen.currency;

import com.hotellook.currency.CurrencyRepository;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.utils.CommonPreferences;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerCurrenciesComponent implements CurrenciesComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Provider<CurrenciesPresenter> currenciesPresenterProvider;
    private Provider<CurrencyRepository> currencyRepositoryProvider;
    private Provider<CommonPreferences> getCommonPreferencesProvider;

    /* renamed from: com.hotellook.ui.screen.currency.DaggerCurrenciesComponent.1 */
    class C12201 implements Factory<CurrencyRepository> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C12201(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public CurrencyRepository get() {
            return (CurrencyRepository) Preconditions.checkNotNull(this.hotellookComponent.currencyRepository(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.hotellook.ui.screen.currency.DaggerCurrenciesComponent.2 */
    class C12212 implements Factory<CommonPreferences> {
        private final HotellookComponent hotellookComponent;
        final /* synthetic */ Builder val$builder;

        C12212(Builder builder) {
            this.val$builder = builder;
            this.hotellookComponent = this.val$builder.hotellookComponent;
        }

        public CommonPreferences get() {
            return (CommonPreferences) Preconditions.checkNotNull(this.hotellookComponent.getCommonPreferences(), "Cannot return null from a non-@Nullable component method");
        }
    }

    public static final class Builder {
        private HotellookComponent hotellookComponent;

        private Builder() {
        }

        public CurrenciesComponent build() {
            if (this.hotellookComponent != null) {
                return new DaggerCurrenciesComponent();
            }
            throw new IllegalStateException(HotellookComponent.class.getCanonicalName() + " must be set");
        }

        public Builder hotellookComponent(HotellookComponent hotellookComponent) {
            this.hotellookComponent = (HotellookComponent) Preconditions.checkNotNull(hotellookComponent);
            return this;
        }
    }

    static {
        $assertionsDisabled = !DaggerCurrenciesComponent.class.desiredAssertionStatus();
    }

    private DaggerCurrenciesComponent(Builder builder) {
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
        this.currencyRepositoryProvider = new C12201(builder);
        this.getCommonPreferencesProvider = new C12212(builder);
        this.currenciesPresenterProvider = CurrenciesPresenter_Factory.create(MembersInjectors.noOp(), this.currencyRepositoryProvider, this.getCommonPreferencesProvider);
    }

    public CurrenciesPresenter presenter() {
        return (CurrenciesPresenter) this.currenciesPresenterProvider.get();
    }
}
