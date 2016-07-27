package com.hotellook.utils;

import android.content.Context;
import com.hotellook.C1178R;
import com.hotellook.common.locale.Constants.Language;
import com.hotellook.search.SearchParams;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    public static Calendar getTodayCalendar() {
        Calendar today = Calendar.getInstance();
        today.set(11, 0);
        today.set(12, 0);
        today.set(13, 0);
        today.set(14, 0);
        return today;
    }

    public static String printMediumDate(Calendar date) {
        Date dateObj = date.getTime();
        if (!AndroidUtils.getLanguage().equalsIgnoreCase(Language.RUSSIAN)) {
            return DateFormat.getDateInstance(2).format(dateObj);
        }
        SimpleDateFormat day = new SimpleDateFormat("d");
        SimpleDateFormat month = new SimpleDateFormat("MMM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        String formattedMonth = month.format(dateObj);
        return day.format(dateObj) + " " + formattedMonth.substring(0, Math.min(formattedMonth.length(), 3)) + ", " + year.format(dateObj);
    }

    public static String printShortDate(Calendar date) {
        return DateFormat.getDateInstance(3).format(date.getTime());
    }

    public static DateFormat getSimpleDateFormatWithNominativeRuMonths(Context context, String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        DateFormatSymbols dfs = sdf.getDateFormatSymbols();
        dfs.setMonths(context.getResources().getStringArray(C1178R.array.months));
        sdf.setDateFormatSymbols(dfs);
        return sdf;
    }

    public static boolean isPreviousDayActualAnywhereOnThePlanet() {
        return getTodayInLastTimezone().get(5) != getTodayCalendar().get(5);
    }

    public static Calendar getTodayInLastTimezone() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT-12"));
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, days);
        return cal.getTime();
    }

    public static int daysBetween(Date day1, Date day2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(day1);
        cal2.setTime(day2);
        return daysBetween(cal1, cal2);
    }

    public static int daysBetween(Calendar day1, Calendar day2) {
        Calendar dayOne = (Calendar) day1.clone();
        Calendar dayTwo = (Calendar) day2.clone();
        if (dayOne.get(1) == dayTwo.get(1)) {
            return Math.abs(dayOne.get(6) - dayTwo.get(6));
        }
        if (dayTwo.get(1) > dayOne.get(1)) {
            Calendar temp = dayOne;
            dayOne = dayTwo;
            dayTwo = temp;
        }
        int extraDays = 0;
        while (dayOne.get(1) > dayTwo.get(1)) {
            dayOne.add(1, -1);
            extraDays += dayOne.getActualMaximum(6);
        }
        return (extraDays - dayTwo.get(6)) + dayOne.get(6);
    }

    public static Calendar parse(String dateStr, SimpleDateFormat formatter) {
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateStr);
        } catch (Exception e) {
        }
        if (parsedDate == null) {
            return null;
        }
        Calendar parsedCal = Calendar.getInstance();
        parsedCal.setTime(parsedDate);
        return parsedCal;
    }

    public static boolean areDatesEqualsByDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            return false;
        }
        if (cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6)) {
            return true;
        }
        return false;
    }

    public static boolean isTonight(SearchParams params) {
        return areDatesEqualsByDay(Calendar.getInstance(), params.checkIn());
    }

    public static int calculateDepth(Calendar date) {
        Calendar now = Calendar.getInstance();
        now.set(10, 0);
        now.set(12, 0);
        now.set(13, 0);
        now.set(14, 0);
        return daysBetween(date, now);
    }

    public static boolean isBetween(Date targetDate, Date fromDate, Date toDate) {
        return fromDate.before(targetDate) && toDate.after(targetDate);
    }
}
