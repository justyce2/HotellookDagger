package com.hotellook.core.api.pojo.hoteldetail;

import com.google.gson.annotations.Expose;
import java.util.List;

public class HotelSummaryData {
    @Expose
    private List<Highlight> highlights;
    @Expose
    private String ratio;
    @Expose
    private int score;
    @Expose
    private String scoreDescription;
    @Expose
    private String text;

    public int getScore() {
        return this.score;
    }

    public String getRatio() {
        return this.ratio;
    }

    public String getText() {
        return this.text;
    }

    public String getScoreDescription() {
        return this.scoreDescription;
    }

    public List<Highlight> getHighlights() {
        return this.highlights;
    }
}
