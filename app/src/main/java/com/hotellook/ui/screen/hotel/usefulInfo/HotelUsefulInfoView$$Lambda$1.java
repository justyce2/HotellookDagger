package com.hotellook.ui.screen.hotel.usefulInfo;

import com.hotellook.core.api.pojo.hoteldetail.GoodToKnowData;
import com.hotellook.utils.CompareUtils;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.Comparator;

final /* synthetic */ class HotelUsefulInfoView$$Lambda$1 implements Comparator {
    private static final HotelUsefulInfoView$$Lambda$1 instance;

    static {
        instance = new HotelUsefulInfoView$$Lambda$1();
    }

    private HotelUsefulInfoView$$Lambda$1() {
    }

    public static Comparator lambdaFactory$() {
        return instance;
    }

    @Hidden
    public int compare(Object obj, Object obj2) {
        return CompareUtils.compare(((GoodToKnowData) obj2).getRatioType(), ((GoodToKnowData) obj).getRatioType());
    }
}
