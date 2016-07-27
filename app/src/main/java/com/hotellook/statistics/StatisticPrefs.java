package com.hotellook.statistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.hotellook.search.SearchParams;
import me.zhanghai.android.materialprogressbar.BuildConfig;
import timber.log.Timber;

public class StatisticPrefs {
    private static final String AS_PREFIX = "AS_";
    private static final String FLURRY_PREFIX = "FLURRY_";
    private static final long LAUNCH_TIME_PERIOD = 3600000;
    private final Gson gson;
    private final SharedPreferences prefs;

    public interface Key {
        public static final String ASMETRICS_SESSION_NUMBER = "ASM_SESSION_NUMBER";
        public static final String ASMETRICS_SESSION_TIMESTAMP = "ASM_SESSION_TIMESTAMP";
        public static final String FILTER_COUNT = "FILTER_COUNT";
        public static final String FIRST_LAUNCH = "FIRST_LAUNCH";
        public static final String FRAME_COUNT = "FRAME_COUNT";
        public static final String INSTALL_TRACKED = "INSTALL_TRACKED";
        public static final String LAST_SEARCH_PARAMS = "LAST_SEARCH_PARAMS";
        public static final String LAUNCH_TIMESTAMP = "LAUNCH_TIMESTAMP";
        public static final String MAP_TRACKED = "MAP_TRACKED";
        public static final String PREDICTED_DURATION = "PREDICTED_DURATIONS";
        public static final String SEARCH_COUNT = "SEARCH_COUNT";
        public static final String SHARE_COUNT = "SHARE_COUNT";
    }

    public StatisticPrefs(Context context) {
        this.gson = new Gson();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isFirstLaunch() {
        return this.prefs.getBoolean(Key.FIRST_LAUNCH, true);
    }

    public void setFirstLaunchTracked() {
        this.prefs.edit().putBoolean(Key.FIRST_LAUNCH, false).apply();
    }

    public boolean isMapOpenTracked() {
        return this.prefs.getBoolean(Key.MAP_TRACKED, false);
    }

    public void setMapTracked() {
        this.prefs.edit().putBoolean(Key.MAP_TRACKED, true).apply();
    }

    public boolean isTrackedASMetrics(String key) {
        return this.prefs.getBoolean(AS_PREFIX + key, false);
    }

    public void setTrackedASMetrics(String key) {
        this.prefs.edit().putBoolean(AS_PREFIX + key, true).apply();
    }

    public boolean isTrackedFlurry(String key) {
        return this.prefs.getBoolean(FLURRY_PREFIX + key, false);
    }

    public void setTrackedFlurry(String key) {
        this.prefs.edit().putBoolean(FLURRY_PREFIX + key, true).apply();
    }

    public int getASMetricsSessionNumber() {
        long lastSessionTimeStamp = getASMetricsSessionTimeStamp();
        long currentTime = System.currentTimeMillis();
        int sessionNumber = this.prefs.getInt(Key.ASMETRICS_SESSION_NUMBER, 0);
        if (currentTime - lastSessionTimeStamp <= 7200000) {
            return sessionNumber;
        }
        sessionNumber++;
        setASMetricsSessionNumber(sessionNumber);
        setCurrentASMetricsSessionTimestamp();
        return sessionNumber;
    }

    private void setASMetricsSessionNumber(int number) {
        this.prefs.edit().putInt(Key.ASMETRICS_SESSION_NUMBER, number).apply();
    }

    private long getASMetricsSessionTimeStamp() {
        return this.prefs.getLong(Key.ASMETRICS_SESSION_TIMESTAMP, 0);
    }

    private void setCurrentASMetricsSessionTimestamp() {
        this.prefs.edit().putLong(Key.ASMETRICS_SESSION_TIMESTAMP, System.currentTimeMillis()).apply();
    }

    @SuppressLint({"CommitPrefEdits"})
    public SearchParams putSearchParamsAndGetPrevious(SearchParams searchParams) {
        SearchParams prev = null;
        try {
            prev = (SearchParams) this.gson.fromJson(this.prefs.getString(Key.LAST_SEARCH_PARAMS, BuildConfig.FLAVOR), SearchParams.class);
        } catch (Exception e) {
            Timber.m752d(e, "Crashed while trying to get previous search params from prefs", new Object[0]);
        }
        this.prefs.edit().putString(Key.LAST_SEARCH_PARAMS, this.gson.toJson((Object) searchParams)).commit();
        return prev;
    }

    @SuppressLint({"CommitPrefEdits"})
    public void incSearchCount() {
        this.prefs.edit().putInt(Key.SEARCH_COUNT, getSearchCount() + 1).commit();
    }

    public int getSearchCount() {
        return this.prefs.getInt(Key.SEARCH_COUNT, 0);
    }

    @SuppressLint({"CommitPrefEdits"})
    public int incSharesAndGet() {
        int count = this.prefs.getInt(Key.SHARE_COUNT, 0) + 1;
        this.prefs.edit().putInt(Key.SHARE_COUNT, count).commit();
        return count;
    }

    @SuppressLint({"CommitPrefEdits"})
    public int incFramesAndGet() {
        int count = this.prefs.getInt(Key.FRAME_COUNT, 0) + 1;
        this.prefs.edit().putInt(Key.FRAME_COUNT, count).commit();
        return count;
    }

    @SuppressLint({"CommitPrefEdits"})
    public int incPredictedDurationsAndGet() {
        int count = this.prefs.getInt(Key.PREDICTED_DURATION, 0) + 1;
        this.prefs.edit().putInt(Key.PREDICTED_DURATION, count).commit();
        return count;
    }

    @SuppressLint({"CommitPrefEdits"})
    public void setLaunchTimestamp() {
        this.prefs.edit().putLong(Key.LAUNCH_TIMESTAMP, System.currentTimeMillis()).commit();
    }

    public boolean isNewLaunch() {
        return System.currentTimeMillis() - this.prefs.getLong(Key.LAUNCH_TIMESTAMP, 0) > LAUNCH_TIME_PERIOD;
    }

    public void incFilterCount() {
        this.prefs.edit().putInt(Key.FILTER_COUNT, getFilterCount() + 1).commit();
    }

    public int getFilterCount() {
        return this.prefs.getInt(Key.FILTER_COUNT, 0);
    }

    public boolean isInstallTracked() {
        return this.prefs.getBoolean(Key.INSTALL_TRACKED, false);
    }

    public void setInstallTracked() {
        this.prefs.edit().putBoolean(Key.INSTALL_TRACKED, true).apply();
    }
}
