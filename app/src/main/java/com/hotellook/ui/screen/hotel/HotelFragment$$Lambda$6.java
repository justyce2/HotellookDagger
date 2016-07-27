package com.hotellook.ui.screen.hotel;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelFragment$$Lambda$6 implements OnDismissListener {
    private final HotelFragment arg$1;

    private HotelFragment$$Lambda$6(HotelFragment hotelFragment) {
        this.arg$1 = hotelFragment;
    }

    public static OnDismissListener lambdaFactory$(HotelFragment hotelFragment) {
        return new HotelFragment$$Lambda$6(hotelFragment);
    }

    @Hidden
    public void onDismiss(DialogInterface dialogInterface) {
        this.arg$1.lambda$showSharingLoadingDialog$4(dialogInterface);
    }
}
