package com.hotellook.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.hotellook.statistics.Constants.MixPanelParams;
import pl.charmas.android.reactivelocation.C1822R;

public class Connectivity {
    public static String getNetwork(Context context) {
        switch (((TelephonyManager) context.getSystemService(MixPanelParams.PHONE)).getNetworkType()) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                return "Unknown";
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                return "GPRS";
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                return "EDGE";
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                return "UMTS";
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                return "CDMA";
            case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                return "EVDO rev. 0";
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
                return "EVDO rev. A";
            case C1822R.styleable.MapAttrs_uiCompass /*7*/:
                return "1xRTT";
            case C1822R.styleable.MapAttrs_uiRotateGestures /*8*/:
                return "HSDPA";
            case C1822R.styleable.MapAttrs_uiScrollGestures /*9*/:
                return "HSUPA";
            case C1822R.styleable.MapAttrs_uiTiltGestures /*10*/:
                return "HSPA";
            case C1822R.styleable.MapAttrs_uiZoomControls /*11*/:
                return "iDen";
            case C1822R.styleable.MapAttrs_uiZoomGestures /*12*/:
                return "EVDO rev. B";
            case C1822R.styleable.MapAttrs_useViewLifecycle /*13*/:
                return "LTE";
            case C1822R.styleable.MapAttrs_zOrderOnTop /*14*/:
                return "eHRPD";
            case C1822R.styleable.MapAttrs_uiMapToolbar /*15*/:
                return "HSPA+";
            default:
                return "New type of network";
        }
    }
}
