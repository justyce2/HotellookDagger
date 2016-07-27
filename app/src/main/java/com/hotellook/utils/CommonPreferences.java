package com.hotellook.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import com.hotellook.HotellookApplication;
import com.hotellook.common.locale.Constants.Language;
import com.hotellook.events.EnGatesAllowedPrefUpdatedEvent;

public class CommonPreferences {
    private final SharedPreferences prefs;

    public interface Key {
        public static final String ALLOW_EN_AGENCIES = "ALLOW_EN_AGENCIES";
        public static final String APP_GUIDE_SHOWN = "APP_GUIDE_SHOWN";
        public static final String CURRENCY = "currency";
        public static final String CURRENCY_DIALOG_SHOWED = "currency_dialog_showed";
        public static final String LAUNCH_FLAGS_TIMESTAMP = "LAUNCH_FLAGS_TIMESTAMP";
        public static final String LAUNCH_FLAG_HLS = "LAUNCH_FLAG_HLS";
        public static final String LAUNCH_FLAG_MARKER = "LAUNCH_FLAG_MARKER";
        public static final String LAUNCH_FLAG_UTM_DETAILS = "LAUNCH_FLAG_UTM_DETAILS";
        public static final String PERMISSION_DENIED = "PERMISSION_DENIED";
        public static final String RATE_CONDITION_HOTEL_VIEWS = "RATE_CONDITION_HOTEL_VIEWS";
        public static final String RATE_CONDITION_RATING = "RATE_CONDITION_RATING";
        public static final String RATE_CONDITION_SESSIONS = "RATE_CONDITION_SESSIONS";
        public static final String RATE_CONDITION_SUCCESSFUL_SEARCHES_COUNT = "RATE_CONDITION_SUCCESSFUL_SEARCHES_COUNT";
        public static final String SHORT_PREDICTED_TIME = "SHORT_PREDICTED_TIME";
        public static final String SHOW_EN_AGENCIES_QUESTION_IN_RESULT = "SHOW_EN_AGENCIES_QUESTION_IN_RESULT";
        public static final String URL_SOURCE = "URL_SOURCE";
    }

    public CommonPreferences(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void putCurrency(String currency) {
        this.prefs.edit().putString(Key.CURRENCY, currency).apply();
    }

    public String getCurrency(String defValue) {
        return this.prefs.getString(Key.CURRENCY, defValue);
    }

    public void putCurrencyDialogShowed() {
        this.prefs.edit().putBoolean(Key.CURRENCY_DIALOG_SHOWED, true).apply();
    }

    public boolean isCurrencyDialogHasAlreadyShown() {
        return this.prefs.getBoolean(Key.CURRENCY_DIALOG_SHOWED, false);
    }

    @SuppressLint({"CommitPrefEdits"})
    public void resetRateConditions() {
        this.prefs.edit().remove(Key.RATE_CONDITION_SUCCESSFUL_SEARCHES_COUNT).remove(Key.RATE_CONDITION_SESSIONS).remove(Key.RATE_CONDITION_HOTEL_VIEWS).commit();
    }

    public void putRateConditionSuccessfulSearches(int count) {
        this.prefs.edit().putInt(Key.RATE_CONDITION_SUCCESSFUL_SEARCHES_COUNT, count).apply();
    }

    public void putRateConditionSessions(int count) {
        this.prefs.edit().putInt(Key.RATE_CONDITION_SESSIONS, count).apply();
    }

    public void putRateConditionHotelViews(int count) {
        this.prefs.edit().putInt(Key.RATE_CONDITION_HOTEL_VIEWS, count).apply();
    }

    public void putAppRated(int rating) {
        this.prefs.edit().putInt(Key.RATE_CONDITION_RATING, rating).apply();
    }

    public int getRateConditionSuccessfulSearchesCount() {
        return this.prefs.getInt(Key.RATE_CONDITION_SUCCESSFUL_SEARCHES_COUNT, 0);
    }

    public int getRateConditionSessionCount() {
        return this.prefs.getInt(Key.RATE_CONDITION_SESSIONS, 0);
    }

    public int getRateConditionHotelViewCount() {
        return this.prefs.getInt(Key.RATE_CONDITION_HOTEL_VIEWS, 0);
    }

    public int getAppRated() {
        return this.prefs.getInt(Key.RATE_CONDITION_RATING, 0);
    }

    public boolean wasGuideShown() {
        return this.prefs.getBoolean(Key.APP_GUIDE_SHOWN, false);
    }

    public void putGuideWasShown() {
        this.prefs.edit().putBoolean(Key.APP_GUIDE_SHOWN, true).apply();
    }

    public void putUrlSource(String source) {
        this.prefs.edit().putString(Key.URL_SOURCE, source).apply();
    }

    public String getUrlSource() {
        return this.prefs.getString(Key.URL_SOURCE, null);
    }

    public void clearUrlSource() {
        putUrlSource(null);
    }

    public void clearOneSessionData() {
        clearUrlSource();
    }

    public void clearLaunchRequestFlags() {
        this.prefs.edit().remove(Key.LAUNCH_FLAG_HLS).remove(Key.LAUNCH_FLAG_MARKER).remove(Key.LAUNCH_FLAG_UTM_DETAILS).remove(Key.LAUNCH_FLAGS_TIMESTAMP).apply();
    }

    public void putLaunchFlagsTimestamp(long timestamp) {
        this.prefs.edit().putLong(Key.LAUNCH_FLAGS_TIMESTAMP, timestamp).apply();
    }

    public void putLaunchFlagHls(String hls) {
        this.prefs.edit().putString(Key.LAUNCH_FLAG_HLS, hls).apply();
    }

    public void putLaunchFlagMarker(String marker) {
        this.prefs.edit().putString(Key.LAUNCH_FLAG_MARKER, marker).apply();
    }

    public void putLaunchFlagUtmDetails(String utmDetails) {
        this.prefs.edit().putString(Key.LAUNCH_FLAG_UTM_DETAILS, utmDetails).apply();
    }

    public long getLaunchFlagsTimestamp() {
        return this.prefs.getLong(Key.LAUNCH_FLAGS_TIMESTAMP, 0);
    }

    public String getLaunchFlagHls() {
        return this.prefs.getString(Key.LAUNCH_FLAG_HLS, null);
    }

    public String getLaunchFlagMarker() {
        return this.prefs.getString(Key.LAUNCH_FLAG_MARKER, null);
    }

    public String getLaunchFlagUtmDetails() {
        return this.prefs.getString(Key.LAUNCH_FLAG_UTM_DETAILS, null);
    }

    public void setShouldShowCustomPermissionDialog(String permission) {
        this.prefs.edit().putBoolean(getPermissionKey(permission), true).apply();
    }

    public boolean shouldShowCustomPermissionDialog(String permission) {
        return this.prefs.getBoolean(getPermissionKey(permission), false);
    }

    @NonNull
    private String getPermissionKey(String permission) {
        return "PERMISSION_DENIED_" + permission;
    }

    public boolean areEnGatesAllowed() {
        if (!this.prefs.contains(Key.ALLOW_EN_AGENCIES)) {
            setUpDefaultAllowEnAgenciesPref();
        }
        return this.prefs.getBoolean(Key.ALLOW_EN_AGENCIES, false);
    }

    public boolean showEnGatesQuestionInResults() {
        return this.prefs.getBoolean(Key.SHOW_EN_AGENCIES_QUESTION_IN_RESULT, true);
    }

    public void setEnGatesQuestionNeverShowInResults() {
        this.prefs.edit().putBoolean(Key.SHOW_EN_AGENCIES_QUESTION_IN_RESULT, false).apply();
    }

    public void setEnGatesAllowed(boolean allowed) {
        this.prefs.edit().putBoolean(Key.ALLOW_EN_AGENCIES, allowed).apply();
        HotellookApplication.eventBus().post(new EnGatesAllowedPrefUpdatedEvent(allowed));
    }

    private void setUpDefaultAllowEnAgenciesPref() {
        if (AndroidUtils.getLanguage().equalsIgnoreCase(Language.RUSSIAN)) {
            setEnGatesAllowed(false);
        } else {
            setEnGatesAllowed(true);
        }
    }

    public boolean isShortBrowserPredictedTimeouts() {
        return this.prefs.getBoolean(Key.SHORT_PREDICTED_TIME, false);
    }

    public void setShortBrowserPredictedTimeouts(boolean shortTimeout) {
        this.prefs.edit().putBoolean(Key.SHORT_PREDICTED_TIME, shortTimeout).apply();
    }
}
