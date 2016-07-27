package com.hotellook.utils;

import android.content.Context;
import com.hotellook.C1178R;
import com.hotellook.search.SearchParams;
import java.text.SimpleDateFormat;

public class ParamsToStringFormatter {
    public static final String DATE_FORMAT = "dd MMM";
    public static final String SHORT_DATE_FORMAT = "dd.MM";
    private final Context context;

    public ParamsToStringFormatter(Context context) {
        this.context = context;
    }

    public String convert(SearchParams searchParams) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, AndroidUtils.getLocale());
        String formattedDate = sdf.format(searchParams.checkIn().getTime());
        int cutSize = Math.min(formattedDate.length(), 6);
        sb.append(formattedDate.substring(0, cutSize) + " \u2014 " + sdf.format(searchParams.checkOut().getTime()).substring(0, cutSize));
        sb.append(", ").append(getGuestsText(searchParams.adults() + searchParams.kidsCount()));
        return sb.toString();
    }

    public String convertToResultsToolbarFormat(SearchParams searchParams) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT, AndroidUtils.getLocale());
        String dates = sdf.format(searchParams.checkIn().getTime()) + " \u2014 " + sdf.format(searchParams.checkOut().getTime());
        return dates + ", " + getGuestsText(searchParams.adults() + searchParams.kidsCount());
    }

    private String getGuestsText(int count) {
        return this.context.getResources().getQuantityString(C1178R.plurals.sr_guests, count, new Object[]{Integer.valueOf(count)});
    }
}
