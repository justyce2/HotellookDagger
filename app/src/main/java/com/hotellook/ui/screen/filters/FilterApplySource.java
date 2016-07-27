package com.hotellook.ui.screen.filters;

import com.hotellook.statistics.Constants.MixPanelParams;

public enum FilterApplySource {
    QUICK(MixPanelParams.QUICK),
    GENERAL(MixPanelParams.GENERAL),
    RESULTS(MixPanelParams.RESULTS);
    
    public final String mixpanelLiteral;

    private FilterApplySource(String mixpanelLiteral) {
        this.mixpanelLiteral = mixpanelLiteral;
    }
}
