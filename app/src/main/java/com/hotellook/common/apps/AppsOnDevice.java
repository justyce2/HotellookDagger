package com.hotellook.common.apps;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppsOnDevice {
    private final Context mContext;

    public AppsOnDevice(Context context) {
        this.mContext = context;
    }

    public boolean isInstalled(String appPackage) {
        try {
            this.mContext.getPackageManager().getPackageInfo(appPackage, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
