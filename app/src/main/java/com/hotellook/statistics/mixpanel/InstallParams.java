package com.hotellook.statistics.mixpanel;

import android.text.TextUtils;
import com.hotellook.statistics.Constants.MixPanelParams;
import java.util.HashMap;
import java.util.Map;

public class InstallParams implements MixPanelSuperParamsBuilder {
    private final Map<String, String> installParams;

    public InstallParams(Map<String, String> installParams) {
        this.installParams = installParams;
    }

    public Map<String, Object> buildSuperParams() {
        return buildParams();
    }

    private Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        String agency = (String) this.installParams.get(MixPanelParams.AGENCY);
        params.put(MixPanelParams.INSTALL_AGENCY, agency);
        params.put(MixPanelParams.INSTALL_TYPE, this.installParams.get(MixPanelParams.AF_STATUS));
        params.put(MixPanelParams.INSTALL_MARKER, this.installParams.get(MixPanelParams.AF_SUB1));
        String mediaSource = (String) this.installParams.get(MixPanelParams.MEDIA_SOURCE);
        String str = MixPanelParams.INSTALL_MEDIA_SOURCE;
        if (!TextUtils.isEmpty(mediaSource)) {
            agency = mediaSource;
        }
        params.put(str, agency);
        params.put(MixPanelParams.INSTALL_COMPAIGN, this.installParams.get(MixPanelParams.COMPAIGN));
        params.put(MixPanelParams.INSTALL_TIME, this.installParams.get(MixPanelParams.AF_INSTALL_TIME));
        return params;
    }
}
