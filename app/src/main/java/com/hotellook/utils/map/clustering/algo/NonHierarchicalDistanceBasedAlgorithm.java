package com.hotellook.utils.map.clustering.algo;

import com.google.android.gms.maps.model.LatLng;
import com.hotellook.utils.MathUtils;
import com.hotellook.utils.map.clustering.Cluster;
import com.hotellook.utils.map.clustering.ClusterItem;
import com.hotellook.utils.map.clustering.projection.SphericalMercatorProjection;
import com.hotellook.utils.map.geometry.Bounds;
import com.hotellook.utils.map.geometry.Point;
import com.hotellook.utils.map.quadtree.PointQuadTree;
import com.hotellook.utils.map.quadtree.PointQuadTree.Item;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NonHierarchicalDistanceBasedAlgorithm<T extends ClusterItem> implements Algorithm<T> {
    protected static final SphericalMercatorProjection PROJECTION;
    private final Collection<QuadItem<T>> mItems;
    private double mLastDiscreteZoom;
    public final int mMaxDistanceAtZoom;
    private final PointQuadTree<QuadItem<T>> mQuadTree;
    private Set<Cluster<T>> mResults;
    private final int mZoomRoundPlaces;

    private static class QuadItem<T extends ClusterItem> implements Cluster<T>, Item {
        private final T mClusterItem;
        private final Point mPoint;
        private final LatLng mPosition;
        private Set<T> singletonSet;

        private QuadItem(T item) {
            this.mClusterItem = item;
            this.mPosition = item.getPosition();
            this.mPoint = NonHierarchicalDistanceBasedAlgorithm.PROJECTION.toPoint(this.mPosition);
            this.singletonSet = Collections.singleton(this.mClusterItem);
        }

        public Point getPoint() {
            return this.mPoint;
        }

        public LatLng getPosition() {
            return this.mPosition;
        }

        public Set<T> getItems() {
            return this.singletonSet;
        }

        public int getSize() {
            return 1;
        }
    }

    static {
        PROJECTION = new SphericalMercatorProjection(1.0d);
    }

    public NonHierarchicalDistanceBasedAlgorithm(int maxDistAtZoom, int zoomRoundPlaces) {
        this.mItems = new ArrayList();
        this.mQuadTree = new PointQuadTree(0.0d, 1.0d, 0.0d, 1.0d);
        this.mMaxDistanceAtZoom = maxDistAtZoom;
        this.mZoomRoundPlaces = zoomRoundPlaces;
    }

    public void addItem(T item) {
        QuadItem<T> quadItem = new QuadItem(null);
        synchronized (this.mQuadTree) {
            this.mItems.add(quadItem);
            this.mQuadTree.add(quadItem);
        }
    }

    public void addItems(Collection<T> items) {
        for (T item : items) {
            addItem(item);
        }
    }

    public void clearItems() {
        synchronized (this.mQuadTree) {
            this.mItems.clear();
            this.mQuadTree.clear();
        }
        this.mLastDiscreteZoom = -1.0d;
    }

    public void removeItem(T t) {
        throw new UnsupportedOperationException("NonHierarchicalDistanceBasedAlgorithm.remove not implemented");
    }

    public Set<? extends Cluster<T>> getClusters(double zoom) {
        double discreteZoom = MathUtils.round(zoom, this.mZoomRoundPlaces);
        if (this.mLastDiscreteZoom == discreteZoom) {
            return this.mResults;
        }
        this.mLastDiscreteZoom = discreteZoom;
        this.mResults = new HashSet();
        Set<QuadItem<T>> visitedCandidates = new HashSet();
        Map<QuadItem<T>, Double> distanceToCluster = new HashMap();
        Map<QuadItem<T>, StaticCluster<T>> itemToCluster = new HashMap();
        double zoomSpecificSpan = getZoomSpecificSpan(discreteZoom);
        synchronized (this.mQuadTree) {
            for (QuadItem<T> candidate : this.mItems) {
                if (!visitedCandidates.contains(candidate)) {
                    Bounds searchBounds = createBoundsFromSpan(candidate.getPoint(), zoomSpecificSpan);
                    Collection<QuadItem<T>> clusterItems = this.mQuadTree.search(searchBounds);
                    if (clusterItems.size() == 1) {
                        this.mResults.add(candidate);
                        visitedCandidates.add(candidate);
                        distanceToCluster.put(candidate, Double.valueOf(0.0d));
                    } else {
                        StaticCluster<T> cluster = new StaticCluster(candidate.mClusterItem.getPosition());
                        this.mResults.add(cluster);
                        for (QuadItem<T> clusterItem : clusterItems) {
                            Double existingDistance = (Double) distanceToCluster.get(clusterItem);
                            double distance = distanceSquared(clusterItem.getPoint(), candidate.getPoint());
                            if (existingDistance != null) {
                                if (existingDistance.doubleValue() >= distance) {
                                    ((StaticCluster) itemToCluster.get(clusterItem)).remove(clusterItem.mClusterItem);
                                }
                            }
                            distanceToCluster.put(clusterItem, Double.valueOf(distance));
                            cluster.add(clusterItem.mClusterItem);
                            itemToCluster.put(clusterItem, cluster);
                        }
                        visitedCandidates.addAll(clusterItems);
                    }
                }
            }
        }
        return this.mResults;
    }

    protected double getZoomSpecificSpan(double discreteZoom) {
        return (((double) this.mMaxDistanceAtZoom) / Math.pow(2.0d, discreteZoom)) / 256.0d;
    }

    public Collection<T> getItems() {
        List<T> items = new ArrayList();
        synchronized (this.mQuadTree) {
            for (QuadItem<T> quadItem : this.mItems) {
                items.add(quadItem.mClusterItem);
            }
        }
        return items;
    }

    protected double distanceSquared(Point a, Point b) {
        return ((a.f743x - b.f743x) * (a.f743x - b.f743x)) + ((a.f744y - b.f744y) * (a.f744y - b.f744y));
    }

    protected Bounds createBoundsFromSpan(Point p, double span) {
        double halfSpan = span / 2.0d;
        return new Bounds(p.f743x - halfSpan, p.f743x + halfSpan, p.f744y - halfSpan, p.f744y + halfSpan);
    }
}
