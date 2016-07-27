package com.hotellook.core.api.pojo.hoteldetail;

import com.google.gson.annotations.Expose;

public class GenderTalkData {
    @Expose
    private String categoryName;
    @Expose
    private String gender;
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
}
