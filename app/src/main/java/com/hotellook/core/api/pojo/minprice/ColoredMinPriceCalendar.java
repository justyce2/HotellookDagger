package com.hotellook.core.api.pojo.minprice;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class ColoredMinPriceCalendar {
    public static final String CHEAP_RATE = "cheap";
    public static final String EXPENSIVE_RATE = "expensive";
    public static final String REGULAR_RATE = "regular";
    private Map<String, MinPriceData> dates;
    @SerializedName("points")
    private Map<String, Double> groupPricesRates;

    public Map<String, MinPriceData> getDates() {
        return this.dates;
    }

    public Map<String, Double> getGroupPricesRates() {
        return this.groupPricesRates;
    }

    public Double getCheapRate() {
        return (Double) this.groupPricesRates.get(CHEAP_RATE);
    }

    public Double getRegularRate() {
        return (Double) this.groupPricesRates.get(REGULAR_RATE);
    }

    public Double getExpensiveRate() {
        return (Double) this.groupPricesRates.get(EXPENSIVE_RATE);
    }
}
