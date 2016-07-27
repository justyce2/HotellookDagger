package com.hotellook.ui.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import com.hotellook.HotellookApplication;
import com.hotellook.events.LocationPermissionDeniedEvent;
import com.hotellook.events.LocationPermissionGrantedEvent;
import com.hotellook.events.ShowCustomLocationRequestDialogEvent;
import com.hotellook.utils.CommonPreferences;
import me.zhanghai.android.materialprogressbar.C1759R;

public class PermissionResultProcessor {
    private final Activity activity;
    private final CommonPreferences commonPreferences;

    public PermissionResultProcessor(CommonPreferences commonPreferences, Activity activity) {
        this.commonPreferences = commonPreferences;
        this.activity = activity;
    }

    public void onResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case C1759R.styleable.AppCompatTheme_autoCompleteTextViewStyle /*101*/:
                String permission = permissions[0];
                if (isPermissionGranted(grantResults)) {
                    HotellookApplication.eventBus().post(new LocationPermissionGrantedEvent());
                } else {
                    onPermissionDenied(permission);
                }
            default:
        }
    }

    private boolean isPermissionGranted(@NonNull int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == 0;
    }

    private void onPermissionDenied(String permission) {
        if (!hasUserSetDontShowAgainPermissionDialog(permission)) {
            HotellookApplication.eventBus().post(new LocationPermissionDeniedEvent());
        } else if (shouldShowCustomDialog(permission)) {
            HotellookApplication.eventBus().post(new ShowCustomLocationRequestDialogEvent());
        } else {
            setShowCustomDialogOnNextTime(permission);
        }
    }

    private void setShowCustomDialogOnNextTime(String permission) {
        this.commonPreferences.setShouldShowCustomPermissionDialog(permission);
    }

    private boolean shouldShowCustomDialog(String permission) {
        return this.commonPreferences.shouldShowCustomPermissionDialog(permission);
    }

    private boolean hasUserSetDontShowAgainPermissionDialog(String permission) {
        return !ActivityCompat.shouldShowRequestPermissionRationale(this.activity, permission);
    }
}
