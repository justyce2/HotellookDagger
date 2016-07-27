package com.hotellook.ui.screen.information;

import com.hotellook.dependencies.scope.MvpScope;
import dagger.Component;

@MvpScope
@Component
public interface ProfileComponent {
    ProfilePresenter presenter();
}
