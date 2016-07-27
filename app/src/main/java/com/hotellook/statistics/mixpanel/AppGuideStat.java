package com.hotellook.statistics.mixpanel;

import com.hotellook.statistics.Constants.MixPanelParams;
import java.util.HashMap;
import java.util.Map;

public class AppGuideStat extends OnScreenTimeDuration implements MixPanelParamsBuilder {
    private int pagesShown;

    public AppGuideStat() {
        this.pagesShown = 1;
    }

    public void pageShown() {
        this.pagesShown++;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.TUTORIAL_SCREENS, Integer.valueOf(this.pagesShown));
        params.put(MixPanelParams.TUTORIAL_DURATION, Long.valueOf(getDurationInSeconds()));
        return params;
    }
}
