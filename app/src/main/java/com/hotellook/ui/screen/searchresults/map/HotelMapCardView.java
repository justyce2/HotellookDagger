package com.hotellook.ui.screen.searchresults.map;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.badges.Badges;
import com.hotellook.badges.badgeviewgenerator.BadgeViewGenerator;
import com.hotellook.common.OfferUtils;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.db.SearchDestinationFavoritesCache;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.search.Discounts;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.Highlights;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.Search;
import com.hotellook.ui.screen.gallery.ViewSizeCalculator;
import com.hotellook.ui.screen.searchresults.SearchResultsItemView;
import com.hotellook.ui.screen.searchresults.map.clustering.HotelCluster;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.Size;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HotelMapCardView extends RelativeLayout {
    private Badges badges;
    private final int cardHeight;
    private final int cardWidth;
    @Nullable
    private View discountContainer;
    @Nullable
    private TextView discountTitle;
    private Size revealedSize;
    private Search search;
    private SearchResultsItemView searchResultsItemView;

    public HotelMapCardView(Context context) {
        this(context, null);
    }

    public HotelMapCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cardWidth = getResources().getDimensionPixelSize(C1178R.dimen.map_card_view_width);
        this.cardHeight = getResources().getDimensionPixelSize(C1178R.dimen.map_card_view_height);
        this.search = getComponent().searchKeeper().lastSearchOrThrowException();
        this.badges = this.search.badges();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.searchResultsItemView = (SearchResultsItemView) findViewById(C1178R.id.sr_item_content);
        this.discountTitle = (TextView) findViewById(C1178R.id.discount_title);
        this.discountContainer = findViewById(C1178R.id.discount_container);
    }

    public void setData(HotelCluster cluster, Map<Long, List<Offer>> results, String currency, Discounts discounts, Highlights highlights) {
        int nightsCount = this.search.searchParams().nightsCount();
        SearchDestinationFavoritesCache searchDestinationFavoritesCache = getComponent().getLocationFavoritesCache();
        HotelData baseClusterHotel = (HotelData) cluster.getHotels().get(0);
        List<Offer> clusterOffers = cluster.findClusterOffers(results);
        DiscountsByRooms discountsByRooms = discounts.get(results.keySet());
        HighlightsByRooms highlightsByRooms = highlights.get(results.keySet());
        Offer bestResult = getBestResult(clusterOffers, discountsByRooms, highlightsByRooms);
        this.searchResultsItemView.setData((List) results.get(Long.valueOf(baseClusterHotel.getId())), baseClusterHotel, bestResult, nightsCount, 0, new ViewSizeCalculator(getContext()).calculatePagerImageSize(), null, currency, discountsByRooms, highlightsByRooms);
        BadgeViewGenerator badgeGenerator = BadgeViewGenerator.withSmallView(getContext());
        if (searchDestinationFavoritesCache.contains(baseClusterHotel.getId())) {
            this.searchResultsItemView.setFavoriteBadge(badgeGenerator);
        }
        this.searchResultsItemView.setBadges(this.badges.getBadges(baseClusterHotel), badgeGenerator);
        if (OfferUtils.hasValidDiscount(bestResult, discountsByRooms, highlightsByRooms)) {
            if (this.discountContainer != null) {
                this.discountContainer.setVisibility(0);
            }
            if (this.discountTitle != null) {
                this.discountTitle.setText(((int) discountsByRooms.get(bestResult).getChangePercentage()) + "%");
            }
        } else if (this.discountContainer != null) {
            this.discountContainer.setVisibility(8);
        }
        calculateRevealedSize();
    }

    private void calculateRevealedSize() {
        measure(MeasureSpec.makeMeasureSpec(this.cardWidth, 0), MeasureSpec.makeMeasureSpec(this.cardHeight, 0));
        this.revealedSize = new Size(getMeasuredWidth(), getMeasuredHeight());
    }

    private HotellookComponent getComponent() {
        return HotellookApplication.getApp().getComponent();
    }

    private Offer getBestResult(List<Offer> resultsForCluster, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        if (resultsForCluster == null || resultsForCluster.size() == 0) {
            return null;
        }
        return (Offer) Collections.min(resultsForCluster, CompareUtils.getComparatorByRoomPrice(discounts, highlights));
    }

    public int getRevealedWidth() {
        return this.revealedSize.getWidth();
    }

    public int getRevealedHeight() {
        return this.revealedSize.getHeight();
    }

    public SearchResultsItemView getContent() {
        return this.searchResultsItemView;
    }
}
