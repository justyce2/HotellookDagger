package com.hotellook.ui.screen.currency;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hotellook.currency.CurrencyInfo;
import java.util.List;

public interface CurrenciesView extends MvpView {
    void showCurrencies(String str, List<CurrencyInfo> list);

    void showDialogNotActualResults();

    void showToastNotActualResults();
}
