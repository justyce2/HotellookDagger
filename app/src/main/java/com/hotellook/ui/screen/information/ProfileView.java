package com.hotellook.ui.screen.information;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface ProfileView extends MvpView {
    void addPersistentFilterViews(PersistentFiltersGroupPresenter persistentFiltersGroupPresenter);

    void hideEnGateViews();

    void hideRateView();

    void setEnGatesChecked(boolean z);

    void showAppVersionName(String str);

    void showCurrency(String str);
}
