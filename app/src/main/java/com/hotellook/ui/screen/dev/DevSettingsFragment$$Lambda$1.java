package com.hotellook.ui.screen.dev;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class DevSettingsFragment$$Lambda$1 implements OnCheckedChangeListener {
    private final DevSettingsFragment arg$1;

    private DevSettingsFragment$$Lambda$1(DevSettingsFragment devSettingsFragment) {
        this.arg$1 = devSettingsFragment;
    }

    public static OnCheckedChangeListener lambdaFactory$(DevSettingsFragment devSettingsFragment) {
        return new DevSettingsFragment$$Lambda$1(devSettingsFragment);
    }

    @Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$onCreateView$0(compoundButton, z);
    }
}
