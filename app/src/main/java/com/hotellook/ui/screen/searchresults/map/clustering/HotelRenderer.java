package com.hotellook.ui.screen.searchresults.map.clustering;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import com.google.android.gms.maps.GoogleMap;
import com.hotellook.C1178R;
import com.hotellook.badges.Badge;
import com.hotellook.badges.Badges;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.ui.screen.searchresults.map.ClusterView;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.map.clustering.Cluster;
import com.hotellook.utils.map.clustering.view.BaseRenderer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HotelRenderer extends BaseRenderer<HotelClusterItem> {
    private final Badges badges;
    private final Discounts discounts;
    private final Highlights highlights;
    private final ClusterView markerView;
    private Map<Long, List<Offer>> offers;

    public HotelRenderer(Context context, GoogleMap map, Map<Long, List<Offer>> results, String currency, Badges badges, Discounts discounts, Highlights highlights) {
        super(map);
        this.offers = results;
        this.markerView = (ClusterView) LayoutInflater.from(context).inflate(C1178R.layout.results_map_marker, null);
        this.discounts = discounts;
        this.highlights = highlights;
        this.markerView.setCurrency(currency);
        this.badges = badges;
    }

    public void setSearchResults(Map<Long, List<Offer>> searchResults) {
        this.offers = searchResults;
    }

    public Bitmap getMarkerBmp(Cluster<HotelClusterItem> cluster) {
        HotelCluster hotelCluster = new HotelCluster(cluster, this.discounts, this.highlights);
        if (cluster.getSize() == 1) {
            this.markerView.setHotel((HotelData) hotelCluster.getHotels().get(0), hotelCluster.findClusterOffers(this.offers));
        } else {
            this.markerView.setGroup(hotelCluster.getHotels(), this.offers);
        }
        return this.markerView.getBitmap();
    }

    private Double getMinPriceForCluster(Cluster<HotelClusterItem> cluster, Discounts discounts, Highlights highlights) {
        List<Offer> clusterOffers = new HotelCluster(cluster, discounts, highlights).findClusterOffers(this.offers);
        Iterable hotelIds = getHotelIds(cluster);
        return clusterOffers.size() == 0 ? null : Double.valueOf(((Offer) Collections.min(clusterOffers, CompareUtils.getComparatorByRoomPrice(discounts.get(hotelIds), highlights.get(hotelIds)))).getPrice());
    }

    private Iterable<Long> getHotelIds(Cluster<HotelClusterItem> cluster) {
        List<Long> ids = new ArrayList();
        for (HotelClusterItem item : cluster.getItems()) {
            ids.add(Long.valueOf(item.getHotelData().getId()));
        }
        return ids;
    }

    public boolean doClustersDrawDifferently(Cluster<HotelClusterItem> oldCluster, Cluster<HotelClusterItem> newCluster) {
        return differInBadges(oldCluster, newCluster) || differInClusterTypes(oldCluster, newCluster) || differInLabels(oldCluster, newCluster);
    }

    private boolean differInLabels(Cluster<HotelClusterItem> oldCluster, Cluster<HotelClusterItem> newCluster) {
        if (singleClusters(oldCluster, newCluster)) {
            return differInMinPrice(oldCluster, newCluster);
        }
        if (groupClusters(oldCluster, newCluster)) {
            return differInSize(oldCluster, newCluster);
        }
        throw new RuntimeException("Something wrong with cluster rendering logic");
    }

    private boolean differInSize(Cluster<HotelClusterItem> oldCluster, Cluster<HotelClusterItem> newCluster) {
        return oldCluster.getSize() != newCluster.getSize();
    }

    private boolean differInMinPrice(Cluster<HotelClusterItem> oldCluster, Cluster<HotelClusterItem> newCluster) {
        Double minPriceForOldCluster = getMinPriceForCluster(oldCluster, this.discounts, this.highlights);
        Double minPriceForNewCluster = getMinPriceForCluster(newCluster, this.discounts, this.highlights);
        if (minPriceForNewCluster == null && minPriceForOldCluster == null) {
            return false;
        }
        if (minPriceForNewCluster == null || minPriceForOldCluster == null || !minPriceForNewCluster.equals(minPriceForOldCluster)) {
            return true;
        }
        return false;
    }

    private boolean differInClusterTypes(Cluster<HotelClusterItem> oldCluster, Cluster<HotelClusterItem> newCluster) {
        return singleClusters(oldCluster, newCluster) || groupClusters(oldCluster, newCluster);
    }

    private boolean groupClusters(Cluster<HotelClusterItem> oldCluster, Cluster<HotelClusterItem> newCluster) {
        return oldCluster.getSize() > 1 && newCluster.getSize() > 1;
    }

    private boolean singleClusters(Cluster<HotelClusterItem> oldCluster, Cluster<HotelClusterItem> newCluster) {
        return oldCluster.getSize() == 1 && newCluster.getSize() == 1;
    }

    private boolean differInBadges(Cluster<HotelClusterItem> oldCluster, Cluster<HotelClusterItem> newCluster) {
        return !fromCluster(this.badges, oldCluster).equals(fromCluster(this.badges, newCluster));
    }

    private Set<Badge> fromCluster(Badges badges, Cluster<HotelClusterItem> cluster) {
        Collection<HotelClusterItem> items = cluster.getItems();
        Collection hotels = new ArrayList();
        for (HotelClusterItem item : items) {
            hotels.add(item.getHotelData());
        }
        return new HashSet(badges.getBadges(hotels));
    }
}
