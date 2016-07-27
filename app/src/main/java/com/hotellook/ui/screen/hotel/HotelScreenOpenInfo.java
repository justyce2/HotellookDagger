package com.hotellook.ui.screen.hotel;

public class HotelScreenOpenInfo {
    public final int position;
    public final Source source;

    public HotelScreenOpenInfo(Source source, int position) {
        this.source = source;
        this.position = position;
    }

    public HotelScreenOpenInfo(Source source) {
        this.source = source;
        this.position = 0;
    }
}
