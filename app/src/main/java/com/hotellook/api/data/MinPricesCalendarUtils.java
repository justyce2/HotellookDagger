package com.hotellook.api.data;

import com.hotellook.core.api.pojo.minprice.ColoredMinPriceCalendar;
import com.hotellook.core.api.pojo.minprice.MinPriceData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MinPricesCalendarUtils {
    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd";

    public enum PriceGroup {
        LOW,
        NORMAL,
        HIGH,
        NONE
    }

    public static PriceGroup getPriceGroup(ColoredMinPriceCalendar minPriceCalendar, Date date) {
        MinPriceData minPriceData = (MinPriceData) minPriceCalendar.getDates().get(new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US).format(date));
        if (minPriceData == null) {
            return PriceGroup.NONE;
        }
        if (minPriceData.getRate() == 0) {
            return PriceGroup.LOW;
        }
        if (minPriceData.getRate() == 1) {
            return PriceGroup.NORMAL;
        }
        if (minPriceData.getRate() == 2) {
            return PriceGroup.HIGH;
        }
        return PriceGroup.NONE;
    }
}
