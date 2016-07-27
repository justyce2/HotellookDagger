package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.badges.Badges;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SearchModule_ProvideBadgesFactory implements Factory<Badges> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final SearchModule module;

    static {
        $assertionsDisabled = !SearchModule_ProvideBadgesFactory.class.desiredAssertionStatus();
    }

    public SearchModule_ProvideBadgesFactory(SearchModule module, Provider<Application> appProvider) {
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

    public Badges get() {
        return (Badges) Preconditions.checkNotNull(this.module.provideBadges((Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Badges> create(SearchModule module, Provider<Application> appProvider) {
        return new SearchModule_ProvideBadgesFactory(module, appProvider);
    }
}
