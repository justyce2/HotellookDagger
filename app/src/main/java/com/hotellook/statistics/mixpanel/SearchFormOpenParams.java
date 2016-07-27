package com.hotellook.statistics.mixpanel;

import android.content.Context;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.utils.AndroidUtils;
import java.util.HashMap;
import java.util.Map;

public class SearchFormOpenParams implements MixPanelParamsBuilder {
    private final Context context;
    private final String source;

    public SearchFormOpenParams(String source, Context context) {
        this.source = source;
        this.context = context;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.FORM_REFERRER, this.source);
        params.put(MixPanelParams.ORIENTATION, AndroidUtils.isPortrait(this.context) ? MixPanelParams.PORTRAIT : MixPanelParams.LANDSCAPE);
        return params;
    }
}
