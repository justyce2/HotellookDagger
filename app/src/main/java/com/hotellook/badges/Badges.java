package com.hotellook.badges;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import com.hotellook.C1178R;
import com.hotellook.badges.selectors.BeachSelector;
import com.hotellook.badges.selectors.BusinessSelector;
import com.hotellook.badges.selectors.CenterSelector;
import com.hotellook.badges.selectors.LowCostSelector;
import com.hotellook.badges.selectors.LuxSelector;
import com.hotellook.badges.selectors.OptimalSelector;
import com.hotellook.badges.selectors.PopularSelector;
import com.hotellook.badges.selectors.RomanceSelector;
import com.hotellook.badges.selectors.WithChildrenSelector;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.sorting.comparator.HotelsComparator;
import com.hotellook.filters.sorting.comparator.RatingComparator;
import com.hotellook.search.SearchData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Badges {
    public static final String NAME_BEACH = "beach";
    public static final String NAME_BUSINESS = "business";
    public static final String NAME_CENTER = "center";
    public static final String NAME_CHEAP = "cheap";
    public static final String NAME_CHILDREN = "children";
    public static final String NAME_LUX = "lux";
    public static final String NAME_OPTIMAL = "optimal";
    public static final String NAME_POPULAR = "popular";
    public static final String NAME_ROMANTIC = "romantic";
    private final List<Badge> mBadges;
    private final Map<Long, List<Badge>> mBadgesMap;
    private final Set<HotelData> mHotels;

    public Badges(Context context) {
        this.mBadgesMap = new Hashtable();
        this.mHotels = new HashSet();
        this.mBadges = new ArrayList();
        Resources res = context.getResources();
        this.mBadges.add(new Badge(res.getString(C1178R.string.badge_popular), res.getColor(C1178R.color.badge_popular), new PopularSelector(), NAME_POPULAR));
        this.mBadges.add(new Badge(res.getString(C1178R.string.badge_optimal), res.getColor(C1178R.color.badge_optimal), new OptimalSelector(), NAME_OPTIMAL));
        this.mBadges.add(new Badge(res.getString(C1178R.string.badge_low_cost), res.getColor(C1178R.color.badge_low_cost), new LowCostSelector(), NAME_CHEAP));
        this.mBadges.add(new Badge(res.getString(C1178R.string.badge_lux), res.getColor(C1178R.color.badge_lux), new LuxSelector(), NAME_LUX));
        this.mBadges.add(new Badge(res.getString(C1178R.string.badge_in_center), res.getColor(C1178R.color.badge_center), new CenterSelector(), NAME_CENTER));
        this.mBadges.add(new Badge(res.getString(C1178R.string.badge_business), res.getColor(C1178R.color.badge_business), new BusinessSelector(), NAME_BUSINESS));
        this.mBadges.add(new Badge(res.getString(C1178R.string.badge_kids), res.getColor(C1178R.color.badge_kids), new WithChildrenSelector(), NAME_CHILDREN));
        this.mBadges.add(new Badge(res.getString(C1178R.string.badge_beach), res.getColor(C1178R.color.badge_beach), new BeachSelector(), NAME_BEACH));
        this.mBadges.add(new Badge(res.getString(C1178R.string.badge_romantic), res.getColor(C1178R.color.badge_romantic), new RomanceSelector(), NAME_ROMANTIC));
    }

    public void init(SearchData searchData) {
        Map<Long, List<Offer>> offers = searchData.offers().all();
        List<HotelData> sortedHotels = getSortedAndWithPrice(searchData.hotels().all(), offers);
        this.mBadgesMap.clear();
        this.mHotels.clear();
        Map<Long, Offer> bestResults = calculateBestOfferForEveryHotel(sortedHotels, offers);
        for (Badge badge : this.mBadges) {
            HotelData hotel = badge.badgeSelector.select(searchData, sortedHotels, bestResults);
            if (hotel != null) {
                this.mHotels.add(hotel);
                addBadgeToMap(hotel.getId(), badge);
            }
        }
    }

    private Map<Long, Offer> calculateBestOfferForEveryHotel(List<HotelData> hotels, Map<Long, List<Offer>> offers) {
        Map<Long, Offer> bestOffer = new HashMap();
        for (HotelData hotel : hotels) {
            bestOffer.put(Long.valueOf(hotel.getId()), getMinimumPrice((List) offers.get(Long.valueOf(hotel.getId()))));
        }
        return bestOffer;
    }

    @Nullable
    private Offer getMinimumPrice(@Nullable List<Offer> offers) {
        if (offers == null || offers.size() == 0) {
            return null;
        }
        Offer best = (Offer) offers.get(0);
        for (int i = 1; i < offers.size(); i++) {
            Offer offer = (Offer) offers.get(i);
            if (best == null || offer.getPrice() < best.getPrice()) {
                best = offer;
            }
        }
        return best;
    }

    private List<HotelData> getSortedAndWithPrice(List<HotelData> hotels, Map<Long, List<Offer>> offers) {
        List<HotelData> sortedAndPriceFiltered = new ArrayList();
        for (HotelData hotel : hotels) {
            if (offers.containsKey(Long.valueOf(hotel.getId()))) {
                sortedAndPriceFiltered.add(hotel);
            }
        }
        Collections.sort(sortedAndPriceFiltered, new HotelsComparator(offers, false, new RatingComparator()));
        return sortedAndPriceFiltered;
    }

    private void addBadgeToMap(long id, Badge badge) {
        if (this.mBadgesMap.containsKey(Long.valueOf(id))) {
            ((List) this.mBadgesMap.get(Long.valueOf(id))).add(badge);
            return;
        }
        List<Badge> badges = new ArrayList();
        badges.add(badge);
        this.mBadgesMap.put(Long.valueOf(id), badges);
    }

    public boolean hasHotelWithBadgesMoreThan(int size) {
        for (List<Badge> badges : this.mBadgesMap.values()) {
            if (badges != null && badges.size() > size) {
                return true;
            }
        }
        return false;
    }

    public List<Badge> getFoundBadges() {
        List<Badge> result = new ArrayList();
        for (List<Badge> badgeList : this.mBadgesMap.values()) {
            result.addAll(badgeList);
        }
        return result;
    }

    public List<HotelData> getHotels() {
        return new ArrayList(this.mHotels);
    }

    public List<Badge> getBadges(HotelData hotel) {
        return getBadgesById(hotel.getId());
    }

    public List<Badge> getBadges(long id) {
        return getBadgesById(id);
    }

    private List<Badge> getBadgesById(long id) {
        List<Badge> result = (List) this.mBadgesMap.get(Long.valueOf(id));
        return result != null ? result : Collections.EMPTY_LIST;
    }

    public List<Badge> getAllSupportedBadges() {
        return this.mBadges;
    }

    public List<Badge> getBadges(Collection<HotelData> hotels) {
        List<Badge> result = new ArrayList();
        for (HotelData hotel : hotels) {
            result.addAll(getBadges(hotel));
        }
        return result;
    }

    public boolean hasBadges() {
        return !this.mHotels.isEmpty();
    }

    public boolean hasBadges(long id) {
        return !getBadgesById(id).isEmpty();
    }
}
