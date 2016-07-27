package com.hotellook.filters.persistant;

import android.content.SharedPreferences;

public class PersistentBoolFilterItem {
    private final boolean defaultValue;
    private final String filterPrefKey;
    private final SharedPreferences prefs;

    public PersistentBoolFilterItem(SharedPreferences prefs, String filterPrefKey, boolean defaultValue) {
        this.prefs = prefs;
        this.filterPrefKey = filterPrefKey;
        this.defaultValue = defaultValue;
    }

    public boolean enabled() {
        return this.prefs.getBoolean(this.filterPrefKey, this.defaultValue);
    }

    public void setEnabled(boolean enabled) {
        this.prefs.edit().putBoolean(this.filterPrefKey, enabled).apply();
    }
}
