package com.hotellook.badges.selectors;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchData;
import com.hotellook.utils.ExponentialInterpolator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PriceSegmentCalculator {
    private final Map<Long, Offer> bestResults;
    private final ExponentialInterpolator exponentialInterpolator;
    private final double middleLeftPrice;
    private final double middleRightPrice;
    private final List<HotelData> sortedHotels;

    public PriceSegmentCalculator(SearchData searchData, List<HotelData> sortedHotels, Map<Long, Offer> bestResults) {
        this.sortedHotels = sortedHotels;
        this.bestResults = bestResults;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Offer offer : bestResults.values()) {
            double price = offer.getPrice();
            if (min > price) {
                min = price;
            }
            if (max < price) {
                max = price;
            }
        }
        this.exponentialInterpolator = new ExponentialInterpolator(min, max);
        this.middleLeftPrice = this.exponentialInterpolator.getInterpolation(((max - min) * 0.4d) + min);
        this.middleRightPrice = this.exponentialInterpolator.getInterpolation(((max - min) * 0.6d) + min);
    }

    public List<HotelData> getMiddleSegment() {
        List<HotelData> result = new ArrayList();
        for (HotelData data : this.sortedHotels) {
            if (this.bestResults.containsKey(Long.valueOf(data.getId()))) {
                double price = ((Offer) this.bestResults.get(Long.valueOf(data.getId()))).getPrice();
                if (price > this.middleLeftPrice && price <= this.middleRightPrice) {
                    result.add(data);
                }
            }
        }
        return result;
    }

    public List<HotelData> getLowSegment() {
        List<HotelData> result = new ArrayList();
        for (HotelData data : this.sortedHotels) {
            if (this.bestResults.containsKey(Long.valueOf(data.getId())) && ((Offer) this.bestResults.get(Long.valueOf(data.getId()))).getPrice() <= this.middleLeftPrice) {
                result.add(data);
            }
        }
        return result;
    }

    public List<HotelData> getHighAndMiddleSegment() {
        List<HotelData> result = new ArrayList();
        for (HotelData data : this.sortedHotels) {
            if (this.bestResults.containsKey(Long.valueOf(data.getId())) && ((Offer) this.bestResults.get(Long.valueOf(data.getId()))).getPrice() > this.middleLeftPrice) {
                result.add(data);
            }
        }
        return result;
    }
}
