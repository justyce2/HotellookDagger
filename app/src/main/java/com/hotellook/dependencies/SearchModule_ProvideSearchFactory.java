package com.hotellook.dependencies;

import com.hotellook.search.SearchKeeper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class SearchModule_ProvideSearchFactory implements Factory<SearchKeeper> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final SearchModule module;

    static {
        $assertionsDisabled = !SearchModule_ProvideSearchFactory.class.desiredAssertionStatus();
    }

    public SearchModule_ProvideSearchFactory(SearchModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public SearchKeeper get() {
        return (SearchKeeper) Preconditions.checkNotNull(this.module.provideSearch(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SearchKeeper> create(SearchModule module) {
        return new SearchModule_ProvideSearchFactory(module);
    }
}
