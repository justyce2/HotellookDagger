package com.hotellook.currency;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class CurrencyFormatter {
    private final CurrencyRepository currencyRepository;

    public CurrencyFormatter(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public String formatPrice(double price, String currency) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(0);
        String symbol = BuildConfig.FLAVOR;
        if (this.currencyRepository.hasSymbolForCode(currency)) {
            symbol = this.currencyRepository.toSymbol(currency);
        }
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol(symbol);
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
        return nf.format(price);
    }
}
