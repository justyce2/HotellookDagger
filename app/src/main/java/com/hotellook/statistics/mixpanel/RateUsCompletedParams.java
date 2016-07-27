package com.hotellook.statistics.mixpanel;

import com.hotellook.statistics.Constants.MixPanelParams;
import java.util.HashMap;
import java.util.Map;

public class RateUsCompletedParams implements MixPanelParamsBuilder, MixPanelSuperParamsBuilder {
    private final String source;
    private final int stars;

    public RateUsCompletedParams(int stars, String source) {
        this.stars = stars;
        this.source = source;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.RATED_STARS, Integer.valueOf(this.stars));
        params.put(MixPanelParams.RATE_US_REFERRER, this.source);
        return params;
    }

    public Map<String, Object> buildSuperParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.RATED_STARS, Integer.valueOf(this.stars));
        return params;
    }
}
