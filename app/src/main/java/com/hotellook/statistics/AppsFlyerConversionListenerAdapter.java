package com.hotellook.statistics;

import com.appsflyer.AppsFlyerConversionListener;
import java.util.Map;

public class AppsFlyerConversionListenerAdapter implements AppsFlyerConversionListener {
    public void onInstallConversionDataLoaded(Map<String, String> map) {
    }

    public void onInstallConversionFailure(String s) {
    }

    public void onAppOpenAttribution(Map<String, String> map) {
    }

    public void onAttributionFailure(String s) {
    }
}
