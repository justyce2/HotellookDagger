package com.hotellook.utils;

import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.common.Poi;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.Comparator;

final /* synthetic */ class CompareUtils$$Lambda$1 implements Comparator {
    private final Coordinates arg$1;

    private CompareUtils$$Lambda$1(Coordinates coordinates) {
        this.arg$1 = coordinates;
    }

    public static Comparator lambdaFactory$(Coordinates coordinates) {
        return new CompareUtils$$Lambda$1(coordinates);
    }

    @Hidden
    public int compare(Object obj, Object obj2) {
        return Float.compare(LocationUtils.distanceBetween(((Poi) obj).getLocation(), this.arg$1), LocationUtils.distanceBetween(((Poi) obj2).getLocation(), this.arg$1));
    }
}
