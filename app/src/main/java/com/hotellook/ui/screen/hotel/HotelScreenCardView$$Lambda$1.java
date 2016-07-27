package com.hotellook.ui.screen.hotel;

import com.hotellook.HotellookApplication;
import com.hotellook.events.OnPhotoClickEvent;
import com.hotellook.ui.screen.searchresults.adapters.ImagePagerAdapter.OnItemClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelScreenCardView$$Lambda$1 implements OnItemClickListener {
    private static final HotelScreenCardView$$Lambda$1 instance;

    static {
        instance = new HotelScreenCardView$$Lambda$1();
    }

    private HotelScreenCardView$$Lambda$1() {
    }

    public static OnItemClickListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onItemClicked(int i) {
        HotellookApplication.eventBus().post(new OnPhotoClickEvent(i));
    }
}
