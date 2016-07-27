package com.hotellook.ui.screen.filters;

import com.hotellook.statistics.Constants.MixPanelParams;

public enum FilterOpenSource {
    LIST(MixPanelParams.LIST, 1),
    MAP(MixPanelParams.MAP, 2);
    
    public final long googlePlayId;
    public final String mixpanelLiteral;

    private FilterOpenSource(String mixpanelLiteral, int googlePlayId) {
        this.mixpanelLiteral = mixpanelLiteral;
        this.googlePlayId = (long) googlePlayId;
    }
}
