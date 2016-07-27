package com.hotellook.core.api.pojo.hoteldetail;

import java.util.List;

public class ScoreData {
    private String categoryId;
    private List<Highlight> highlights;
    private String name;
    private String ratio;
    private int reviewCount;
    private int score;
    private String shortText;
    private List<Subcategory> subcategories;
    private List<SummarySentence> summarySentences;

    public int getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }

    public String getRatio() {
        return this.ratio;
    }

    public int getReviewCount() {
        return this.reviewCount;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public String getShortText() {
        return this.shortText;
    }

    public List<Subcategory> getSubcategories() {
        return this.subcategories;
    }

    public List<Highlight> getHighlights() {
        return this.highlights;
    }

    public List<SummarySentence> getSummarySentences() {
        return this.summarySentences;
    }
}
