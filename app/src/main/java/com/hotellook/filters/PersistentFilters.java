package com.hotellook.filters;

import android.content.Context;
import android.preference.PreferenceManager;
import com.hotellook.filters.persistant.PersistentBoolFilterItem;

public class PersistentFilters {
    private static final String KEY_FILTER_HOSTELS = "filter_hostels";
    private final PersistentBoolFilterItem hostelFilterIteml;

    public PersistentFilters(Context context) {
        this.hostelFilterIteml = new PersistentBoolFilterItem(PreferenceManager.getDefaultSharedPreferences(context), KEY_FILTER_HOSTELS, false);
    }

    public PersistentBoolFilterItem hostelFilterIteml() {
        return this.hostelFilterIteml;
    }
}
