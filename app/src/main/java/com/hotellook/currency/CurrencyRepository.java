package com.hotellook.currency;

import com.hotellook.C1178R;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CommonPreferences;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CurrencyRepository {
    private final List<CurrencyInfo> currencyInfoList;
    private final Map<String, String> currencySymbols;
    private final CommonPreferences prefs;

    public CurrencyRepository(CommonPreferences commonPreferences) {
        this.currencySymbols = new HashMap();
        this.currencyInfoList = new ArrayList();
        addCurrency(new CurrencyInfo("EUR", "\u20ac", C1178R.string.currency_eur));
        addCurrency(new CurrencyInfo("USD", "$", C1178R.string.currency_usd));
        addCurrency(new CurrencyInfo("AUD", "A$", C1178R.string.currency_aud));
        addCurrency(new CurrencyInfo("UAH", "\u20b4", C1178R.string.currency_uah));
        addCurrency(new CurrencyInfo("INR", "\u20b9", C1178R.string.currency_inr));
        addCurrency(new CurrencyInfo("HKD", "HK$", C1178R.string.currency_hkd));
        addCurrency(new CurrencyInfo("CAD", "C$", C1178R.string.currency_cad));
        addCurrency(new CurrencyInfo("CNY", "\u5143", C1178R.string.currency_cny));
        addCurrency(new CurrencyInfo("NZD", "NZ$", C1178R.string.currency_nzd));
        addCurrency(new CurrencyInfo("SGD", "S$", C1178R.string.currency_sgd));
        addCurrency(new CurrencyInfo("THB", "\u0e3f", C1178R.string.currency_thb));
        addCurrency(new CurrencyInfo("JPY", "\u00a5", C1178R.string.currency_jpy));
        addCurrency(new CurrencyInfo("GBP", "\u00a3", C1178R.string.currency_gbp));
        addCurrency(new CurrencyInfo("RUB", "\u20bd", C1178R.string.currency_rub));
        addCurrency(new CurrencyInfo("BRL", "R$", C1178R.string.currency_brl));
        addCurrency(new CurrencyInfo("KRW", "\u20a9", C1178R.string.currency_krw));
        addCurrency(new CurrencyInfo("TWD", "NT$", C1178R.string.currency_twd));
        addCurrency(new CurrencyInfo("AED", "\u062f.\u0625.\u200f", C1178R.string.currency_aed));
        addCurrency(new CurrencyInfo("CHF", "CHF", C1178R.string.currency_chf));
        addCurrency(new CurrencyInfo("MOP", "MOP$", C1178R.string.currency_mop));
        addCurrency(new CurrencyInfo("BYN", "Br", C1178R.string.currency_byn));
        this.prefs = commonPreferences;
    }

    public void addCurrency(CurrencyInfo info) {
        this.currencySymbols.put(info.code, info.symbol);
        this.currencyInfoList.add(info);
    }

    public void saveCurrency(String currency) {
        this.prefs.putCurrency(currency);
    }

    public String currencyCode() {
        return this.prefs.getCurrency(systemCurrency().getCurrencyCode());
    }

    public String toSymbol(String code) {
        return (String) this.currencySymbols.get(code.toUpperCase());
    }

    public boolean hasSymbolForCode(String currency) {
        return this.currencySymbols.containsKey(currency);
    }

    private Currency systemCurrency() {
        try {
            return Currency.getInstance(AndroidUtils.getLocale());
        } catch (IllegalArgumentException e) {
            return Currency.getInstance(Locale.US);
        }
    }

    public List<CurrencyInfo> getCurrencyList() {
        return this.currencyInfoList;
    }
}
