package com.hotellook.utils.map.clustering.algo;

import com.hotellook.utils.map.clustering.Cluster;
import com.hotellook.utils.map.clustering.ClusterItem;
import java.util.Collection;
import java.util.Set;

public interface Algorithm<T extends ClusterItem> {
    void addItem(T t);

    void addItems(Collection<T> collection);

    void clearItems();

    Set<? extends Cluster<T>> getClusters(double d);

    Collection<T> getItems();

    void removeItem(T t);
}
