package com.hotellook.core.api.pojo.hoteldetail;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class FoursquareData {
    @Expose
    private ArrayList<TipData> tips;

    public ArrayList<TipData> getTips() {
        return this.tips;
    }
}
