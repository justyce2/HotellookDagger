package com.hotellook.currency;

public class CurrencyInfo {
    public final String code;
    public final int resNameId;
    public final String symbol;

    public CurrencyInfo(String code, String symbol, int resNameId) {
        this.code = code;
        this.symbol = symbol;
        this.resNameId = resNameId;
    }
}
