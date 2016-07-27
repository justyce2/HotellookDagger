package com.hotellook.statistics.mixpanel;

import com.hotellook.statistics.Constants.MixPanelParams;
import java.util.HashMap;
import java.util.Map;

public class MixpanelErrorParams implements MixPanelParamsBuilder {
    private final Exception f726e;
    private final String event;

    public MixpanelErrorParams(String event, Exception e) {
        this.event = event;
        this.f726e = e;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.EVENT, this.event);
        params.put(MixPanelParams.MESSAGE, this.f726e.getMessage());
        return params;
    }
}
