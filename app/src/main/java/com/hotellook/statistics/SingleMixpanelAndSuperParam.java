package com.hotellook.statistics;

import com.hotellook.statistics.mixpanel.MixPanelSuperParamsBuilder;
import java.util.Map;

public class SingleMixpanelAndSuperParam extends SingleParams implements MixPanelSuperParamsBuilder {
    public SingleMixpanelAndSuperParam(String key, Object value) {
        super(key, value);
    }

    public Map<String, Object> buildSuperParams() {
        return buildParams();
    }
}
