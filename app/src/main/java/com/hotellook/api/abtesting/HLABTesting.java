package com.hotellook.api.abtesting;

import android.graphics.Color;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import timber.log.Timber;

public class HLABTesting implements ABTesting {
    public static final String DEFAULT_CPA_BTN_COLOR = "#71C34A";

    public HLABTesting(MixpanelAPI api) {
        api.getPeople().joinExperimentIfAvailable();
        api.getPeople().addOnMixpanelUpdatesReceivedListener(HLABTesting$$Lambda$1.lambdaFactory$());
    }

    public int getCTAButtonColor() {
        Timber.m751d("AB CTA color %s", (String) MixpanelAPI.stringTweak(Constants.CTA_BUTTON_COLOR, DEFAULT_CPA_BTN_COLOR).get());
        try {
            return Color.parseColor((String) MixpanelAPI.stringTweak(Constants.CTA_BUTTON_COLOR, DEFAULT_CPA_BTN_COLOR).get());
        } catch (Exception e) {
            return Color.parseColor(DEFAULT_CPA_BTN_COLOR);
        }
    }
}
