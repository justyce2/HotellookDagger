package com.hotellook.search;

import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.LocationUtils;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.Comparator;

final /* synthetic */ class Locations$$Lambda$1 implements Comparator {
    private final Coordinates arg$1;

    private Locations$$Lambda$1(Coordinates coordinates) {
        this.arg$1 = coordinates;
    }

    public static Comparator lambdaFactory$(Coordinates coordinates) {
        return new Locations$$Lambda$1(coordinates);
    }

    @Hidden
    public int compare(Object obj, Object obj2) {
        return CompareUtils.compare((int) LocationUtils.distanceBetween(((CityInfo) obj).getLocation(), this.arg$1), (int) LocationUtils.distanceBetween(((CityInfo) obj2).getLocation(), this.arg$1));
    }
}
