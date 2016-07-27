package com.hotellook.statistics.mixpanel;

import com.hotellook.HotellookApplication;
import com.hotellook.api.trackers.RequestFlagsHelperTracker;
import com.hotellook.statistics.Constants.MixPanelParams;
import java.util.HashMap;
import java.util.Map;

public class SuperParams implements MixPanelParamsBuilder, MixPanelSuperParamsBuilder {
    private final String token;

    public SuperParams(String token) {
        this.token = token;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        RequestFlagsHelperTracker requestFlagsHelperTracker = HotellookApplication.getApp().getComponent().getRequestFlagsHelperTracker();
        putNotNullParam(params, MixPanelParams.FIRST_LAUNCH_DATE, MixpanelCurrentDayFactory.create());
        putNotNullParam(params, MixPanelParams.MARKER, requestFlagsHelperTracker.getMarker());
        putNotNullParam(params, MixPanelParams.HLS, requestFlagsHelperTracker.getHls());
        putNotNullParam(params, MixPanelParams.UTM, requestFlagsHelperTracker.getUtmDetail());
        return params;
    }

    private void putNotNullParam(Map<String, Object> params, String key, Object value) {
        if (value != null) {
            params.put(key, value);
        }
    }

    public Map<String, Object> buildSuperParams() {
        Map<String, Object> params = buildParams();
        params.put(MixPanelParams.LOCATION_REQUESTED, Boolean.valueOf(false));
        params.put(MixPanelParams.TOKEN, this.token);
        return params;
    }
}
