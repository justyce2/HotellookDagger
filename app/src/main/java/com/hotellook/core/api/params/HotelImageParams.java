package com.hotellook.core.api.params;

public class HotelImageParams {
    public static final String FIT_CROP = "crop";
    public static final String FIT_LIMIT = "limit";
    private String fitType;
    private int height;
    private long hotelId;
    private int photoIndex;
    private int width;

    public int getPhotoIndex() {
        return this.photoIndex;
    }

    public void setPhotoIndex(int photoIndex) {
        this.photoIndex = photoIndex;
    }

    public String getFitType() {
        return this.fitType;
    }

    public void setFitType(String fitType) {
        this.fitType = fitType;
    }

    public long getHotelId() {
        return this.hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
