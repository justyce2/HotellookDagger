package com.hotellook.statistics.mixpanel;

import com.hotellook.BuildConfig;
import com.hotellook.statistics.Constants.MixPanelParams;
import java.util.HashMap;
import java.util.Map;

public class AppBuildParams implements MixPanelParamsBuilder {
    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.BUILD_TYPE, BuildConfig.FLAVOR);
        return params;
    }
}
