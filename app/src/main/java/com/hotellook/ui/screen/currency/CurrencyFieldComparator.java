package com.hotellook.ui.screen.currency;

import com.hotellook.currency.CurrencyInfo;
import com.hotellook.utils.CompareUtils;
import java.util.Comparator;

public class CurrencyFieldComparator implements Comparator<CurrencyInfo> {
    public int compare(CurrencyInfo lhs, CurrencyInfo rhs) {
        return CompareUtils.compare(lhs.code, rhs.code);
    }
}
