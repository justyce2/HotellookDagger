package com.hotellook.core.api.pojo.hoteldetail;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

public class TrustYouData {
    @Expose
    private ArrayList<GenderTalkData> genderTalks;
    @Expose
    private ArrayList<GoodToKnowData> goodToKnow;
    @Expose
    private HotelSummaryData hotelSummary;
    @Expose
    private ArrayList<HotelTypeData> hotelType;
    private List<LanguageData> languageSplit;
    @Expose
    private int popularity;
    @Expose
    private int rating;
    @Expose
    private int reviewsCount;
    @Expose
    private ArrayList<ScoreData> sentimentScoreList;
    @Expose
    private List<TripType> tripTypeSplit;

    public ArrayList<ScoreData> getSentimentScoreList() {
        return this.sentimentScoreList;
    }

    public int getRating() {
        return this.rating;
    }

    public ArrayList<HotelTypeData> getHotelType() {
        return this.hotelType;
    }

    public ArrayList<GenderTalkData> getGenderTalks() {
        return this.genderTalks;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public ArrayList<GoodToKnowData> getGoodToKnow() {
        return this.goodToKnow;
    }

    public HotelSummaryData getHotelSummary() {
        return this.hotelSummary;
    }

    public int getReviewsCount() {
        return this.reviewsCount;
    }

    public List<TripType> getTripTypeSplit() {
        return this.tripTypeSplit;
    }

    public List<LanguageData> getLanguageSplit() {
        return this.languageSplit;
    }
}
