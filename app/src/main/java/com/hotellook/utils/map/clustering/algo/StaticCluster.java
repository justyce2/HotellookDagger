package com.hotellook.utils.map.clustering.algo;

import com.google.android.gms.maps.model.LatLng;
import com.hotellook.utils.map.clustering.Cluster;
import com.hotellook.utils.map.clustering.ClusterItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StaticCluster<T extends ClusterItem> implements Cluster<T> {
    private final LatLng mCenter;
    private final List<T> mItems;

    public StaticCluster(LatLng center) {
        this.mItems = new ArrayList();
        this.mCenter = center;
    }

    public boolean add(T t) {
        return this.mItems.add(t);
    }

    public LatLng getPosition() {
        return this.mCenter;
    }

    public boolean remove(T t) {
        return this.mItems.remove(t);
    }

    public Collection<T> getItems() {
        return this.mItems;
    }

    public int getSize() {
        return this.mItems.size();
    }

    public String toString() {
        return "StaticCluster{mCenter=" + this.mCenter + ", mItems.size=" + this.mItems.size() + '}';
    }
}
