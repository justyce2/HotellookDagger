package com.hotellook.core.api.pojo.hoteldetail;

import com.google.gson.annotations.Expose;
import java.util.List;

public class GoodToKnowData {
    private static final String BAD = "Bad";
    private static final String GOOD = "Good";
    private static final String NEUTRAL = "Neutral";
    public static final int RATIO_BAD = -1;
    public static final int RATIO_GOOD = 1;
    public static final int RATIO_NEUTRAL = 0;
    @Expose
    private String categoryName;
    @Expose
    private List<Highlight> highlights;
    @Expose
    private String ratio;
    @Expose
    private int score;
    @Expose
    private String text;

    public int getScore() {
        return this.score;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public String getRatio() {
        return this.ratio;
    }

    public String getText() {
        return this.text;
    }

    public List<Highlight> getHighlights() {
        return this.highlights;
    }

    public int getRatioType() {
        if (this.ratio.equals(BAD)) {
            return RATIO_BAD;
        }
        if (this.ratio.equals(NEUTRAL)) {
            return 0;
        }
        return RATIO_GOOD;
    }
}
