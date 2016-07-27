package com.hotellook.ui.screen.searchresults.adapters;

import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.badges.Badges;
import com.hotellook.badges.badgeviewgenerator.BadgeViewGenerator;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.sorting.comparator.HotelsComparator;
import com.hotellook.filters.sorting.comparator.PriceComparator;
import com.hotellook.search.Discounts;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.Highlights;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.ui.screen.gallery.ViewSizeCalculator;
import com.hotellook.ui.screen.searchresults.SearchResultsItemView;
import com.hotellook.utils.CompareUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelsGalleryViewPagerAdapter extends PagerAdapter {
    private final Badges badges;
    private final String currency;
    @Nullable
    private final Discounts discounts;
    @Nullable
    private final Highlights highlights;
    private final List<HotelData> hotels;
    private final Map<Integer, View> itemsMap;
    private final int nights;
    private final Map<Long, List<Offer>> searchResults;

    public HotelsGalleryViewPagerAdapter(List<HotelData> hotels, Map<Long, List<Offer>> searchResults, int nights, Badges badges, String currency, Discounts discounts, Highlights highlights) {
        this.itemsMap = new HashMap();
        this.hotels = hotels;
        this.searchResults = searchResults;
        this.nights = nights;
        this.badges = badges;
        this.currency = currency;
        this.discounts = discounts;
        this.highlights = highlights;
        Collections.sort(this.hotels, new HotelsComparator(this.searchResults, true, new PriceComparator(this.searchResults, discounts, highlights)));
    }

    public int getCount() {
        return this.hotels.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View hotelCard = LayoutInflater.from(container.getContext()).inflate(C1178R.layout.hotels_gallery_card_view, container, false);
        SearchResultsItemView srView = (SearchResultsItemView) hotelCard.findViewById(C1178R.id.sr_item_content);
        HotelData hotel = (HotelData) this.hotels.get(position);
        srView.setBadges(this.badges.getBadges(hotel), BadgeViewGenerator.withSmallView(container.getContext()));
        List<Offer> resultsForHotel = (List) this.searchResults.get(Long.valueOf(hotel.getId()));
        Offer offerWithMinPrice = null;
        if (resultsForHotel != null && resultsForHotel.size() > 0) {
            offerWithMinPrice = (Offer) Collections.min(resultsForHotel, CompareUtils.getComparatorByRoomPrice(this.discounts.get(hotel.getId()), this.highlights.get(hotel.getId())));
        }
        srView.setData((List) this.searchResults.get(Long.valueOf(hotel.getId())), (HotelData) this.hotels.get(position), offerWithMinPrice, this.nights, 0, new ViewSizeCalculator(container.getContext()).calculatePagerImageSize(), null, this.currency, discounts(hotel.getId()), highlights(hotel.getId()));
        srView.disableViewpager();
        container.addView(hotelCard);
        this.itemsMap.put(Integer.valueOf(position), hotelCard);
        return hotelCard;
    }

    @Nullable
    private DiscountsByRooms discounts(long id) {
        return this.discounts == null ? null : this.discounts.get(id);
    }

    @Nullable
    private HighlightsByRooms highlights(long id) {
        return this.highlights == null ? null : this.highlights.get(id);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        this.itemsMap.remove(Integer.valueOf(position));
    }

    public View getItemView(int position) {
        return (View) this.itemsMap.get(Integer.valueOf(position));
    }

    public Collection<View> getItemViews() {
        return this.itemsMap.values();
    }
}
