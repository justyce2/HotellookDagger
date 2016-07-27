package com.hotellook.statistics;

import com.hotellook.statistics.mixpanel.MixPanelParamsBuilder;
import java.util.HashMap;
import java.util.Map;

public class SingleParams implements MixPanelParamsBuilder {
    private final String mKey;
    private final Object mValue;

    public SingleParams(String key, Object value) {
        this.mKey = key;
        this.mValue = value;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(this.mKey, this.mValue);
        return params;
    }
}
