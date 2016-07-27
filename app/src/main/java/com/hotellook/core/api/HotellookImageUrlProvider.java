package com.hotellook.core.api;

import com.hotellook.core.api.params.CityImageParams;
import com.hotellook.core.api.params.HotelImageParams;

public class HotellookImageUrlProvider {
    private static final String URL_CITY_PHOTO = "https://photo.hotellook.com/static/cities/{w}x{h}/{cityId}.auto";
    private static final String URL_HOTEL_PHOTO = "https://photo.hotellook.com/image_v2/{fit}/h{id}_{i}/{w}/{h}.auto";

    public String createCityPhotoUrl(CityImageParams params) {
        return URL_CITY_PHOTO.replace("{w}", String.valueOf(params.getWidth())).replace("{h}", String.valueOf(params.getHeight())).replace("{cityId}", String.valueOf(params.getCityId()));
    }

    public String createHotelPhotoUrl(HotelImageParams params) {
        return URL_HOTEL_PHOTO.replace("{fit}", params.getFitType()).replace("{id}", String.valueOf(params.getHotelId())).replace("{i}", String.valueOf(params.getPhotoIndex())).replace("{w}", String.valueOf(params.getWidth())).replace("{h}", String.valueOf(params.getHeight()));
    }
}
