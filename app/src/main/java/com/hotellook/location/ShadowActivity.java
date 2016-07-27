package com.hotellook.location;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.common.api.Status;
import com.hotellook.HotellookApplication;
import com.hotellook.events.LocationPermissionRequestResultEvent;
import com.hotellook.events.LocationSettingsRequestResultEvent;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.EventBus;

@TargetApi(23)
public class ShadowActivity extends Activity {
    public static final String REQUEST_TYPE = "REQUEST_TYPE";
    public static final int REQUEST_TYPE_LOCATION_PERMISSION = 1;
    public static final int REQUEST_TYPE_LOCATION_SETTINGS = 2;
    public static final String STATUS = "STATUS";
    private EventBus eventBus;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.eventBus = HotellookApplication.from(this).getComponent().eventBus();
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Status status = (Status) intent.getParcelableExtra(STATUS);
        switch (intent.getIntExtra(REQUEST_TYPE, 0)) {
            case REQUEST_TYPE_LOCATION_PERMISSION /*1*/:
                String[] strArr = new String[REQUEST_TYPE_LOCATION_PERMISSION];
                strArr[0] = "android.permission.ACCESS_FINE_LOCATION";
                ActivityCompat.requestPermissions(this, strArr, REQUEST_TYPE_LOCATION_PERMISSION);
            case REQUEST_TYPE_LOCATION_SETTINGS /*2*/:
                try {
                    status.startResolutionForResult(this, REQUEST_TYPE_LOCATION_SETTINGS);
                } catch (SendIntentException e) {
                    this.eventBus.post(new LocationPermissionRequestResultEvent(false));
                    finish();
                }
            default:
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_TYPE_LOCATION_PERMISSION /*1*/:
                this.eventBus.post(new LocationPermissionRequestResultEvent(AndroidUtils.hasLocationPermission(this)));
                break;
        }
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TYPE_LOCATION_SETTINGS /*2*/:
                this.eventBus.post(new LocationSettingsRequestResultEvent(resultCode == -1));
                break;
        }
        finish();
    }
}
