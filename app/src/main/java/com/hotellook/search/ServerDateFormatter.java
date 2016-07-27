package com.hotellook.search;

import com.hotellook.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ServerDateFormatter {
    private static final String SERVER_DATE_FORMAT = "yyyy-MM-dd";
    private final SimpleDateFormat dateFormat;

    public ServerDateFormatter() {
        this.dateFormat = new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US);
    }

    public String format(Calendar date) {
        return format(date.getTime());
    }

    public String format(Date date) {
        return this.dateFormat.format(date);
    }

    public Calendar parse(String date) {
        if (date == null) {
            return Calendar.getInstance();
        }
        return DateUtils.parse(date, this.dateFormat);
    }
}
