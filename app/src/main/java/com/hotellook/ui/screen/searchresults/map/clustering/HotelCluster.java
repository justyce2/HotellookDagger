package com.hotellook.ui.screen.searchresults.map.clustering;

import android.support.annotation.NonNull;
import com.google.android.gms.maps.model.LatLng;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.Discounts;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.Highlights;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.map.clustering.Cluster;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class HotelCluster {
    private final Cluster<HotelClusterItem> cluster;
    private final Discounts discounts;
    private final Highlights highlights;

    /* renamed from: com.hotellook.ui.screen.searchresults.map.clustering.HotelCluster.1 */
    class C13941 implements Comparator<HotelClusterItem> {
        final /* synthetic */ Map val$results;

        C13941(Map map) {
            this.val$results = map;
        }

        public int compare(HotelClusterItem lhs, HotelClusterItem rhs) {
            List<Offer> lhsResults = getResultsForClusterItem(lhs);
            List<Offer> rhsResults = getResultsForClusterItem(rhs);
            if (lhsResults == null && rhsResults == null) {
                return 0;
            }
            if (lhsResults == null) {
                return 1;
            }
            if (rhsResults == null) {
                return -1;
            }
            long lHotelId = lhs.getHotelData().getId();
            long rHotelId = rhs.getHotelData().getId();
            return Double.compare(getMinResult(lhsResults, HotelCluster.this.discounts.get(lHotelId), HotelCluster.this.highlights.get(lHotelId)).getPrice(), getMinResult(rhsResults, HotelCluster.this.discounts.get(rHotelId), HotelCluster.this.highlights.get(rHotelId)).getPrice());
        }

        private List<Offer> getResultsForClusterItem(HotelClusterItem lhs) {
            return (List) this.val$results.get(Long.valueOf(lhs.getHotelData().getId()));
        }

        private Offer getMinResult(List<Offer> results, DiscountsByRooms discounts, HighlightsByRooms highlights) {
            return (Offer) Collections.min(results, CompareUtils.getComparatorByRoomPrice(discounts, highlights));
        }
    }

    public HotelCluster(@NonNull Cluster<HotelClusterItem> cluster, Discounts discounts, Highlights highlights) {
        this.cluster = cluster;
        this.discounts = discounts;
        this.highlights = highlights;
    }

    public List<HotelData> getHotels() {
        List<HotelData> hotels = new ArrayList();
        for (HotelClusterItem item : this.cluster.getItems()) {
            hotels.add(item.getHotelData());
        }
        return hotels;
    }

    public int getSize() {
        return this.cluster.getSize();
    }

    public LatLng getPosition() {
        return this.cluster.getPosition();
    }

    public List<Offer> findClusterOffers(Map<Long, List<Offer>> allOffers) {
        List<Offer> clusterOffers = new ArrayList();
        for (HotelClusterItem clusterItem : this.cluster.getItems()) {
            List<Offer> clusterItemOffers = (List) allOffers.get(Long.valueOf(clusterItem.getHotelData().getId()));
            if (CollectionUtils.isNotEmpty(clusterItemOffers)) {
                clusterOffers.addAll(clusterItemOffers);
            }
        }
        return clusterOffers;
    }

    public Collection<HotelClusterItem> getItems() {
        return this.cluster.getItems();
    }

    public HotelClusterItem getItemWithMinPrice(Map<Long, List<Offer>> results) {
        return (HotelClusterItem) Collections.min(getItems(), new C13941(results));
    }
}
