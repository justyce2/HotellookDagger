package com.hotellook.core.api.pojo.hoteldetail;

import java.util.List;

public class SummarySentence {
    private List<String> categoryIdList;
    private String sentiment;
    private String text;

    public List<String> getCategoryIdList() {
        return this.categoryIdList;
    }

    public String getSentiment() {
        return this.sentiment;
    }

    public String getText() {
        return this.text;
    }
}
