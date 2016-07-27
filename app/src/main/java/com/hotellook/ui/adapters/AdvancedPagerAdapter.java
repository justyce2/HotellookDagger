package com.hotellook.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public abstract class AdvancedPagerAdapter extends FragmentPagerAdapter {
    private long pagerId;

    public AdvancedPagerAdapter(FragmentManager fm, long pagerId) {
        super(fm);
        this.pagerId = pagerId;
    }

    protected Fragment findFragmentInFMByIndex(FragmentManager fm, int index) {
        return fm.findFragmentByTag("android:switcher:" + this.pagerId + ":" + index);
    }
}
