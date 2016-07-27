package com.hotellook.ui.screen.gallery;

import com.hotellook.HotellookApplication;
import com.hotellook.core.api.HotellookImageUrlProvider;
import com.hotellook.core.api.params.HotelImageParams;
import com.hotellook.utils.Size;

public class ImageUrlCreator {
    private final long mHotelId;
    private final HotellookImageUrlProvider mImageService;

    public ImageUrlCreator(HotellookImageUrlProvider imageService, long hotelId) {
        this.mImageService = imageService;
        this.mHotelId = hotelId;
    }

    public ImageUrlCreator(long hotelId) {
        this(HotellookApplication.getApp().getComponent().getHotelImageUrlProvider(), hotelId);
    }

    public String getImageUrl(int index, Size size) {
        HotelImageParams params = new HotelImageParams();
        params.setFitType(HotelImageParams.FIT_CROP);
        params.setHeight(size.getHeight());
        params.setWidth(size.getWidth());
        params.setHotelId(this.mHotelId);
        params.setPhotoIndex(index);
        return this.mImageService.createHotelPhotoUrl(params);
    }
}
