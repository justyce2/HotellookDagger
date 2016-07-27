package com.hotellook.statistics.mixpanel;

import com.hotellook.statistics.Constants.MixPanelParams;
import java.util.HashMap;
import java.util.Map;

public class SearchCancelParams implements MixPanelParamsBuilder {
    private final boolean manual;
    private final long searchDuration;

    public SearchCancelParams(boolean manual, long searchDuration) {
        this.manual = manual;
        this.searchDuration = searchDuration;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.SEARCH_CANCEL_REFERRER, referrer());
        params.put(MixPanelParams.SEARCH_CANCEL_DURATION, Long.valueOf(this.searchDuration / 1000));
        return params;
    }

    private Object referrer() {
        return this.manual ? MixPanelParams.MANUAL : MixPanelParams.AUTO;
    }
}
