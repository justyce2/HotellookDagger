package com.hotellook.ui.screen.hotel.general;

import android.view.View;
import android.view.View.OnLongClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelGeneralFragment$$Lambda$7 implements OnLongClickListener {
    private final HotelGeneralFragment arg$1;
    private final String arg$2;

    private HotelGeneralFragment$$Lambda$7(HotelGeneralFragment hotelGeneralFragment, String str) {
        this.arg$1 = hotelGeneralFragment;
        this.arg$2 = str;
    }

    public static OnLongClickListener lambdaFactory$(HotelGeneralFragment hotelGeneralFragment, String str) {
        return new HotelGeneralFragment$$Lambda$7(hotelGeneralFragment, str);
    }

    @Hidden
    public boolean onLongClick(View view) {
        return this.arg$1.lambda$fillAddress$5(this.arg$2, view);
    }
}
