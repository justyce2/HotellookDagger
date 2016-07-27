package com.hotellook.statistics.mixpanel;

import android.content.Context;
import android.support.annotation.NonNull;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.utils.AndroidUtils;
import java.util.HashMap;
import java.util.Map;

public class DeviceParams implements MixPanelParamsBuilder {
    private final Context context;

    public DeviceParams(Context context) {
        this.context = context;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.DEVICE_TYPE, deviceType());
        return params;
    }

    @NonNull
    private String deviceType() {
        return AndroidUtils.isPhone(this.context) ? MixPanelParams.PHONE : MixPanelParams.TABLET;
    }
}
