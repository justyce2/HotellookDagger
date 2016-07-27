package com.hotellook.ui.screen.destinationpicker;

import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.utils.CompareUtils;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.Comparator;

final /* synthetic */ class DestinationsPickerAdapter$$Lambda$1 implements Comparator {
    private static final DestinationsPickerAdapter$$Lambda$1 instance;

    static {
        instance = new DestinationsPickerAdapter$$Lambda$1();
    }

    private DestinationsPickerAdapter$$Lambda$1() {
    }

    public static Comparator lambdaFactory$() {
        return instance;
    }

    @Hidden
    public int compare(Object obj, Object obj2) {
        return CompareUtils.compare(((GeoLocationData) obj2).getHotelsCount(), ((GeoLocationData) obj).getHotelsCount());
    }
}
