package com.hotellook.ui.screen.information;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hotellook.BuildConfig;
import com.hotellook.common.locale.Constants.Language;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.dependencies.AppVersionRepository;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.Search;
import com.hotellook.search.SearchKeeper;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CommonPreferences;
import javax.inject.Inject;

public class ProfilePresenter extends MvpBasePresenter<ProfileView> {
    private final AppVersionRepository appVersionRepository;
    private final CommonPreferences commonPreferences;
    private final CurrencyRepository currencyRepository;
    private final PersistentFiltersGroupPresenter persistentFiltersGroupPresenter;

    @Inject
    public ProfilePresenter(CommonPreferences commonPreferences, SearchKeeper searchKeeper, PersistentFilters persistentFilters, CurrencyRepository currencyRepository, AppVersionRepository appVersionRepository) {
        this.commonPreferences = commonPreferences;
        this.currencyRepository = currencyRepository;
        this.appVersionRepository = appVersionRepository;
        Search search = searchKeeper.lastSearch();
        Filters filters = null;
        if (search != null) {
            filters = search.filters();
        }
        this.persistentFiltersGroupPresenter = new PersistentFiltersGroupPresenter(filters, persistentFilters);
    }

    public void attachView(ProfileView view) {
        super.attachView(view);
        view.addPersistentFilterViews(this.persistentFiltersGroupPresenter);
        view.setEnGatesChecked(this.commonPreferences.areEnGatesAllowed());
        view.showCurrency(this.currencyRepository.currencyCode());
        if (isEnLocale()) {
            view.hideEnGateViews();
        }
        if (!BuildConfig.GOOGLE_PLAY_BUILD.booleanValue()) {
            view.hideRateView();
        }
        view.showAppVersionName(this.appVersionRepository.versionName());
    }

    public void onEnGatesAllowCheckedChanged(boolean checked) {
        this.commonPreferences.setEnGatesAllowed(checked);
    }

    private boolean isEnLocale() {
        return AndroidUtils.getLanguage().equals(Language.ENGLISH);
    }
}
