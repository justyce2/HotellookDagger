package com.hotellook.ui.screen.favorite.router;

import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.hotel.HotelFragment;
import com.hotellook.ui.screen.hotel.HotelScreenOpenInfo;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import javax.inject.Inject;

public class FavoritesRouter {
    private final MainActivity activity;

    @Inject
    public FavoritesRouter(MainActivity activity) {
        this.activity = activity;
    }

    public void showHotelScreen(HotelDataSource hotelDataSource, HotelScreenOpenInfo hotelScreenOpenInfo, int pageIndex) {
        this.activity.showFragment(HotelFragment.create(hotelDataSource, hotelScreenOpenInfo, pageIndex));
    }
}
