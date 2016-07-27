package com.hotellook.core.api.pojo.hoteldetail;

public class Subcategory {
    private String categoryId;
    private String categoryName;
    private float count;
    private String ratio;
    private float relevance;
    private int score;
    private String shortText;
    private String text;

    public String getCategoryName() {
        return this.categoryName;
    }

    public String getText() {
        return this.text;
    }

    public float getCount() {
        return this.count;
    }

    public String getRatio() {
        return this.ratio;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public String getShortText() {
        return this.shortText;
    }

    public int getScore() {
        return this.score;
    }

    public float getRelevance() {
        return this.relevance;
    }
}
