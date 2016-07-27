package com.hotellook.ui.screen.currency;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hotellook.HotellookApplication;
import com.hotellook.currency.CurrencyInfo;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.events.CurrencyChangedEvent;
import com.hotellook.utils.CommonPreferences;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class CurrenciesPresenter extends MvpBasePresenter<CurrenciesView> {
    private static final long TOAST_MIN_INTERVAL = 2000;
    private final CommonPreferences commonPreferences;
    private final CurrencyRepository currencyRepository;
    private long lastToastTimestamp;

    @Inject
    public CurrenciesPresenter(CurrencyRepository currencyRepository, CommonPreferences commonPreferences) {
        this.currencyRepository = currencyRepository;
        this.commonPreferences = commonPreferences;
    }

    public void currencySelected(CurrencyInfo field) {
        this.currencyRepository.saveCurrency(field.code);
        CurrenciesView view = (CurrenciesView) getView();
        if (view != null) {
            if (this.commonPreferences.isCurrencyDialogHasAlreadyShown()) {
                showToastAlert(view);
            } else {
                showDialogAlert(view);
            }
        }
        HotellookApplication.eventBus().post(new CurrencyChangedEvent(field.code));
    }

    private void showDialogAlert(CurrenciesView view) {
        this.commonPreferences.putCurrencyDialogShowed();
        view.showDialogNotActualResults();
    }

    private void showToastAlert(CurrenciesView view) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastToastTimestamp > TOAST_MIN_INTERVAL) {
            this.lastToastTimestamp = currentTime;
            view.showToastNotActualResults();
        }
    }

    public void loadCurrencies() {
        CurrenciesView view = (CurrenciesView) getView();
        if (view != null) {
            List<CurrencyInfo> currencyInfoList = this.currencyRepository.getCurrencyList();
            Collections.sort(currencyInfoList, new CurrencyFieldComparator());
            view.showCurrencies(this.currencyRepository.currencyCode(), currencyInfoList);
        }
    }
}
