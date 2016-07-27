package com.hotellook.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GooglePlayPage {
    private final Context mContext;
    private final String mPackageName;

    public GooglePlayPage(Context context, String packageName) {
        this.mContext = context;
        this.mPackageName = packageName;
    }

    public void open() {
        try {
            this.mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.mPackageName)));
        } catch (ActivityNotFoundException e) {
            this.mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + this.mPackageName)));
        }
    }
}
