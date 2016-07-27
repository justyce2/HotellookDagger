package com.hotellook.ui.screen.currency;

import com.hotellook.dependencies.scope.MvpScope;
import dagger.Component;

@MvpScope
@Component
public interface CurrenciesComponent {
    CurrenciesPresenter presenter();
}
