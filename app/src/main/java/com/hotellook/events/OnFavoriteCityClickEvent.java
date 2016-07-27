package com.hotellook.events;

import com.hotellook.db.data.FavoriteHotelDataEntity;
import java.util.List;

public class OnFavoriteCityClickEvent {
    public final List<FavoriteHotelDataEntity> favoriteHotels;

    public OnFavoriteCityClickEvent(List<FavoriteHotelDataEntity> favoriteHotels) {
        this.favoriteHotels = favoriteHotels;
    }
}
