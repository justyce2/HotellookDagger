package com.hotellook.ui.screen.information;

import com.hotellook.filters.items.criterion.Criterion;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PersistentFiltersGroupPresenter$NullBoolFilterItem$$Lambda$1 implements Criterion {
    private static final PersistentFiltersGroupPresenter$NullBoolFilterItem$$Lambda$1 instance;

    static {
        instance = new PersistentFiltersGroupPresenter$NullBoolFilterItem$$Lambda$1();
    }

    private PersistentFiltersGroupPresenter$NullBoolFilterItem$$Lambda$1() {
    }

    public static Criterion lambdaFactory$() {
        return instance;
    }

    @Hidden
    public boolean passes(Object obj) {
        return false;
    }
}
