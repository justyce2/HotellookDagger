package com.hotellook.core.api;

import android.content.Context;
import android.content.SharedPreferences;

class Settings {
    private static final String KEY_TOKEN = "APP_TOKEN";
    private static final String PREFS_NAME = "hotellook-core-settings";
    private final SharedPreferences preferences;

    private Settings(Context context) {
        this.preferences = context.getSharedPreferences(PREFS_NAME, 0);
    }

    static Settings from(Context context) {
        return new Settings(context);
    }

    String getToken() {
        return this.preferences.getString(KEY_TOKEN, null);
    }

    void saveToken(String token) {
        this.preferences.edit().putString(KEY_TOKEN, token).apply();
    }
}
