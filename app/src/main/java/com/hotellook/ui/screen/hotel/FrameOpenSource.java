package com.hotellook.ui.screen.hotel;

import com.hotellook.statistics.Constants.MixPanelParams;

public enum FrameOpenSource {
    ACTIONBTN(MixPanelParams.ACTION_BUTTON),
    RATES(MixPanelParams.RATES);
    
    public final String mixpanelLiteral;

    private FrameOpenSource(String mixpanelLiteral) {
        this.mixpanelLiteral = mixpanelLiteral;
    }
}
