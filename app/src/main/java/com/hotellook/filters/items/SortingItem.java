package com.hotellook.filters.items;

import android.location.Location;
import android.support.annotation.NonNull;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.OfferUtils;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.filters.distancetarget.LocationDistanceTarget;
import com.hotellook.filters.sorting.comparator.BadgesComparator;
import com.hotellook.filters.sorting.comparator.DiscountComparator;
import com.hotellook.filters.sorting.comparator.DistanceComparator;
import com.hotellook.filters.sorting.comparator.HotelsComparator;
import com.hotellook.filters.sorting.comparator.PopularityComparator;
import com.hotellook.filters.sorting.comparator.PriceComparator;
import com.hotellook.filters.sorting.comparator.RatingComparator;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.search.SearchData;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SortingItem implements BaseLogicItem, HotelComparatorProducer {
    private static final String SAVE_TAG;
    private int algoId;
    private BadgesComparator badgesComparator;
    private Discounts discounts;
    private DistanceTarget distanceTarget;
    private boolean hasDiscounts;
    private Highlights highlights;

    @Retention(RetentionPolicy.SOURCE)
    @interface SortingAlgorithm {
    }

    public SortingItem() {
        this.algoId = C1178R.id.sorting_popularity;
    }

    static {
        SAVE_TAG = SortingItem.class.getSimpleName();
    }

    public void reset() {
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        Map<Long, List<Offer>> offersForHotels = searchData.offers().all();
        Location searchCenter = searchData.searchParams().location();
        this.discounts = searchData.discounts();
        this.highlights = searchData.highlights();
        setUpDefaultTarget(searchCenter);
        this.algoId = C1178R.id.sorting_popularity;
        this.hasDiscounts = checkHasDiscounts(offersForHotels, this.discounts, this.highlights);
        this.badgesComparator = createBadgesComparator(searchData);
    }

    private BadgesComparator createBadgesComparator(@NonNull SearchData searchData) {
        return new BadgesComparator(HotellookApplication.getApp().getComponent().searchKeeper().lastSearchOrThrowException().badges(), searchData.seasons(), searchData.searchParams());
    }

    private boolean checkHasDiscounts(Map<Long, List<Offer>> offersForHotels, Discounts discounts, Highlights highlights) {
        for (Entry<Long, List<Offer>> roomsInHotel : offersForHotels.entrySet()) {
            Long hotelId = (Long) roomsInHotel.getKey();
            for (Offer room : (List) roomsInHotel.getValue()) {
                if (OfferUtils.hasValidDiscount(room, discounts.get(hotelId.longValue()), highlights.get(hotelId.longValue()))) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setUpDefaultTarget(Location searchCenter) {
        this.distanceTarget = new LocationDistanceTarget(searchCenter, HotellookApplication.getApp().getString(C1178R.string.city_center), DestinationType.CENTER);
    }

    public boolean inDefaultState() {
        return true;
    }

    public int getAlgoId() {
        return this.algoId;
    }

    public void setAlgoId(int algoId) {
        this.algoId = algoId;
    }

    public int _hashCode() {
        return (this.algoId * 31) + this.distanceTarget.hashCode();
    }

    public void saveState(FiltersSnapshot snapshot) {
        snapshot.addData(SAVE_TAG, Integer.valueOf(this.algoId));
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.algoId = ((Integer) snapshot.getData(SAVE_TAG)).intValue();
    }

    public void release() {
    }

    public int hashCode() {
        return _hashCode();
    }

    public Comparator<HotelData> newComparator(Map<Long, List<Offer>> offersForHotels) {
        switch (this.algoId) {
            case C1178R.id.sorting_popularity /*2131689811*/:
                return new HotelsComparator(offersForHotels, false, new PopularityComparator(this.badgesComparator));
            case C1178R.id.sorting_ratings /*2131689812*/:
                return new HotelsComparator(offersForHotels, false, new RatingComparator());
            case C1178R.id.sorting_price /*2131689813*/:
                return new HotelsComparator(offersForHotels, true, new PriceComparator(offersForHotels, this.discounts, this.highlights));
            case C1178R.id.sorting_distance /*2131689814*/:
                return new HotelsComparator(offersForHotels, true, new DistanceComparator(this.distanceTarget));
            case C1178R.id.sorting_discount /*2131689815*/:
                return new HotelsComparator(offersForHotels, true, new DiscountComparator(offersForHotels, new PopularityComparator(this.badgesComparator), this.discounts, this.highlights));
            default:
                return new HotelsComparator(offersForHotels, false, new PopularityComparator(this.badgesComparator));
        }
    }

    public boolean isPriceSorting() {
        return this.algoId == C1178R.id.sorting_price;
    }

    public DistanceTarget getDistanceTarget() {
        return this.distanceTarget;
    }

    public void setDistanceTarget(DistanceTarget distanceTarget) {
        this.distanceTarget = distanceTarget;
    }

    public boolean hasDiscounts() {
        return this.hasDiscounts;
    }
}
