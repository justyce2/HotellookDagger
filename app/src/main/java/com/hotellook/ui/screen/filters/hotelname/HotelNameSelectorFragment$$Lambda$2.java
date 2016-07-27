package com.hotellook.ui.screen.filters.hotelname;

import com.hotellook.filters.items.criterion.HotelNameCriterion;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelNameSelectorFragment$$Lambda$2 implements Runnable {
    private final HotelNameSelectorFragment arg$1;
    private final HotelNameCriterion arg$2;

    private HotelNameSelectorFragment$$Lambda$2(HotelNameSelectorFragment hotelNameSelectorFragment, HotelNameCriterion hotelNameCriterion) {
        this.arg$1 = hotelNameSelectorFragment;
        this.arg$2 = hotelNameCriterion;
    }

    public static Runnable lambdaFactory$(HotelNameSelectorFragment hotelNameSelectorFragment, HotelNameCriterion hotelNameCriterion) {
        return new HotelNameSelectorFragment$$Lambda$2(hotelNameSelectorFragment, hotelNameCriterion);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$foundMatches$1(this.arg$2);
    }
}
