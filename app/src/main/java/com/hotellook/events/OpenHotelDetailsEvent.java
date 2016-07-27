package com.hotellook.events;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.utils.Size;
import java.util.List;

public class OpenHotelDetailsEvent {
    public final FavoriteHotelDataEntity favoriteHotelData;
    public final HotelData hotelData;
    public final Size imageSize;
    public final int pageIndex;
    public final int positionInList;
    public final List<Offer> searchResult;

    public OpenHotelDetailsEvent(@NonNull HotelData hotelData, @NonNull List<Offer> searchResult, Size imageSize, int pageIndex, int positionInList) {
        this.positionInList = positionInList;
        this.favoriteHotelData = null;
        this.hotelData = hotelData;
        this.searchResult = searchResult;
        this.pageIndex = pageIndex;
        this.imageSize = imageSize;
    }

    public OpenHotelDetailsEvent(FavoriteHotelDataEntity favoriteHotelData, List<Offer> results, Size imageSize, int photoIdx, int positionInList) {
        this.positionInList = positionInList;
        this.hotelData = null;
        this.favoriteHotelData = favoriteHotelData;
        this.searchResult = results;
        this.pageIndex = photoIdx;
        this.imageSize = imageSize;
    }
}
