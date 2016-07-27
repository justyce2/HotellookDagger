package com.hotellook.utils.map.clustering.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hotellook.utils.map.clustering.Cluster;
import com.hotellook.utils.map.clustering.ClusterItem;
import com.hotellook.utils.map.clustering.ClusterManager.OnClusterClickListener;
import com.hotellook.utils.map.clustering.ClusterManager.OnClusterInfoWindowClickListener;
import com.hotellook.utils.map.clustering.ClusterManager.OnClusterItemClickListener;
import com.hotellook.utils.map.clustering.ClusterManager.OnClusterItemInfoWindowClickListener;
import com.hotellook.utils.map.clustering.view.ClusterRenderer.OnRenderingFinishedListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BaseRenderer<T extends ClusterItem> implements OnCameraChangeListener, ClusterRenderer<T> {
    private final GoogleMap map;
    private Map<Cluster<T>, Marker> markersOnTheMap;
    private OnClusterClickListener<T> onClusterClickListener;
    private OnRenderingFinishedListener onRenderingFinishedListener;
    private boolean rendering;
    private Set<Cluster<T>> visibleClusters;

    /* renamed from: com.hotellook.utils.map.clustering.view.BaseRenderer.1 */
    class C14621 implements OnMarkerClickListener {
        C14621() {
        }

        public boolean onMarkerClick(Marker marker) {
            for (Cluster<T> cluster : BaseRenderer.this.markersOnTheMap.keySet()) {
                if (cluster.getPosition().equals(marker.getPosition())) {
                    BaseRenderer.this.onClusterClickListener.onClusterClick(cluster);
                    return true;
                }
            }
            return false;
        }
    }

    /* renamed from: com.hotellook.utils.map.clustering.view.BaseRenderer.2 */
    class C14632 implements AnimatorUpdateListener {
        final /* synthetic */ Map val$markers;

        C14632(Map map) {
            this.val$markers = map;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            float animatedFraction = ((Float) animation.getAnimatedValue()).floatValue();
            for (Marker marker : this.val$markers.values()) {
                marker.setAlpha(animatedFraction);
            }
        }
    }

    public abstract boolean doClustersDrawDifferently(Cluster<T> cluster, Cluster<T> cluster2);

    public abstract Bitmap getMarkerBmp(Cluster<T> cluster);

    public BaseRenderer(GoogleMap map) {
        this.markersOnTheMap = new HashMap();
        this.rendering = false;
        this.map = map;
        this.map.setOnMarkerClickListener(new C14621());
    }

    public void onClustersChanged(Set<? extends Cluster<T>> clusters) {
        this.rendering = true;
        LatLngBounds visibleBounds = enlargeVisibleBounds(this.map.getProjection().getVisibleRegion().latLngBounds, 0.5f);
        Set<Cluster<T>> lastVisibleClusters = this.visibleClusters;
        this.visibleClusters = new HashSet();
        for (Cluster<T> cluster : clusters) {
            if (visibleBounds.contains(cluster.getPosition())) {
                this.visibleClusters.add(cluster);
            }
        }
        Set<Cluster<T>> clustersToDeleteFromMap = new HashSet();
        if (lastVisibleClusters != null) {
            clustersToDeleteFromMap.addAll(lastVisibleClusters);
            clustersToDeleteFromMap.removeAll(this.visibleClusters);
        }
        Set<Cluster<T>> clustersToAddOnMap = new HashSet(this.visibleClusters);
        if (lastVisibleClusters != null) {
            clustersToAddOnMap.removeAll(lastVisibleClusters);
        }
        Map<Cluster<T>, Cluster<T>> clustersSwapMap = getClusterSwapMap(clustersToDeleteFromMap, clustersToAddOnMap);
        for (Cluster<T> oldCluster : clustersSwapMap.keySet()) {
            Marker swapMarker = (Marker) this.markersOnTheMap.remove(oldCluster);
            Cluster<T> newCluster = (Cluster) clustersSwapMap.get(oldCluster);
            this.markersOnTheMap.put(newCluster, swapMarker);
            if (doClustersDrawDifferently(oldCluster, newCluster)) {
                swapMarker.setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBmp(newCluster)));
            }
        }
        for (Cluster<T> cluster2 : clustersToDeleteFromMap) {
            ((Marker) this.markersOnTheMap.remove(cluster2)).remove();
        }
        Map<Cluster<T>, Marker> newMarkers = new HashMap();
        for (Cluster<T> cluster22 : clustersToAddOnMap) {
            newMarkers.put(cluster22, this.map.addMarker(getMarkerOptions(cluster22)));
        }
        this.markersOnTheMap.putAll(newMarkers);
        animateMarkersAppearing(newMarkers);
        this.rendering = false;
        this.onRenderingFinishedListener.onRenderingFinished();
    }

    @NonNull
    private Map<Cluster<T>, Cluster<T>> getClusterSwapMap(Set<Cluster<T>> clustersToDeleteFromMap, Set<Cluster<T>> clustersToAddOnMap) {
        Map<Cluster<T>, Cluster<T>> clustersSwapMap = new HashMap();
        if (clustersToDeleteFromMap.size() > 0 && clustersToAddOnMap.size() > 0) {
            for (Cluster<T> candidateToDelete : clustersToDeleteFromMap) {
                for (Cluster<T> candidateToAdd : clustersToAddOnMap) {
                    if (clustersCanBeReused(candidateToDelete, candidateToAdd)) {
                        clustersSwapMap.put(candidateToDelete, candidateToAdd);
                        break;
                    }
                }
            }
        }
        clustersToDeleteFromMap.removeAll(clustersSwapMap.keySet());
        clustersToAddOnMap.removeAll(clustersSwapMap.values());
        return clustersSwapMap;
    }

    private boolean clustersCanBeReused(Cluster<T> candidateToDelete, Cluster<T> candidateToAdd) {
        return candidateToAdd.getPosition().equals(candidateToDelete.getPosition()) && clustersOfOneType(candidateToDelete, candidateToAdd);
    }

    private boolean clustersOfOneType(Cluster<T> candidateToDelete, Cluster<T> candidateToAdd) {
        if (candidateToDelete.getSize() == 1 && candidateToAdd.getSize() == 1) {
            return true;
        }
        return candidateToDelete.getSize() > 1 && candidateToAdd.getSize() > 1;
    }

    private void animateMarkersAppearing(Map<Cluster<T>, Marker> markers) {
        ValueAnimator markerAppearAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        markerAppearAnimator.addUpdateListener(new C14632(markers));
        markerAppearAnimator.start();
    }

    private MarkerOptions getMarkerOptions(Cluster<T> cluster) {
        return new MarkerOptions().position(cluster.getPosition()).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBmp(cluster))).alpha(0.0f);
    }

    private LatLngBounds enlargeVisibleBounds(LatLngBounds visibleBounds, float fraction) {
        double westLon = visibleBounds.southwest.longitude;
        double northLat = visibleBounds.northeast.latitude;
        double eastLon = visibleBounds.northeast.longitude;
        double southLat = visibleBounds.southwest.latitude;
        double lonSpan = (eastLon - westLon) * ((double) fraction);
        double latSpan = (northLat - southLat) * ((double) fraction);
        return new LatLngBounds(new LatLng(southLat - (latSpan / 2.0d), westLon - (lonSpan / 2.0d)), new LatLng((latSpan / 2.0d) + northLat, (lonSpan / 2.0d) + eastLon));
    }

    public void setOnClusterClickListener(OnClusterClickListener<T> onClusterClickListener) {
        this.onClusterClickListener = onClusterClickListener;
    }

    public void setOnClusterInfoWindowClickListener(OnClusterInfoWindowClickListener<T> onClusterInfoWindowClickListener) {
    }

    public void setOnClusterItemClickListener(OnClusterItemClickListener<T> onClusterItemClickListener) {
    }

    public void setOnClusterItemInfoWindowClickListener(OnClusterItemInfoWindowClickListener<T> onClusterItemInfoWindowClickListener) {
    }

    public boolean isReady() {
        return !this.rendering;
    }

    public void onCameraChange(CameraPosition cameraPosition) {
    }

    public void setOnRenderingFinishedListener(OnRenderingFinishedListener onRenderingFinishedListener) {
        this.onRenderingFinishedListener = onRenderingFinishedListener;
    }

    public Marker getMarker(Cluster<T> cluster) {
        return (Marker) this.markersOnTheMap.get(cluster);
    }
}
