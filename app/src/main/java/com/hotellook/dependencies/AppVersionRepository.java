package com.hotellook.dependencies;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class AppVersionRepository {
    private final Context context;

    public AppVersionRepository(Context context) {
        this.context = context;
    }

    public String versionName() {
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return BuildConfig.FLAVOR;
        }
    }
}
