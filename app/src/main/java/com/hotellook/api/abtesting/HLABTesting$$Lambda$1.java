package com.hotellook.api.abtesting;

import com.mixpanel.android.mpmetrics.OnMixpanelUpdatesReceivedListener;
import java.lang.invoke.LambdaForm.Hidden;
import timber.log.Timber;

final /* synthetic */ class HLABTesting$$Lambda$1 implements OnMixpanelUpdatesReceivedListener {
    private static final HLABTesting$$Lambda$1 instance;

    static {
        instance = new HLABTesting$$Lambda$1();
    }

    private HLABTesting$$Lambda$1() {
    }

    public static OnMixpanelUpdatesReceivedListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onMixpanelUpdatesReceived() {
        Timber.m751d("AB data updated", new Object[0]);
    }
}
