package com.hotellook.utils.map.clustering.view;

import com.google.android.gms.maps.model.Marker;
import com.hotellook.utils.map.clustering.Cluster;
import com.hotellook.utils.map.clustering.ClusterItem;
import com.hotellook.utils.map.clustering.ClusterManager.OnClusterClickListener;
import com.hotellook.utils.map.clustering.ClusterManager.OnClusterInfoWindowClickListener;
import com.hotellook.utils.map.clustering.ClusterManager.OnClusterItemClickListener;
import com.hotellook.utils.map.clustering.ClusterManager.OnClusterItemInfoWindowClickListener;
import java.util.Set;

public interface ClusterRenderer<T extends ClusterItem> {

    public interface OnRenderingFinishedListener {
        void onRenderingFinished();
    }

    Marker getMarker(Cluster<T> cluster);

    boolean isReady();

    void onClustersChanged(Set<? extends Cluster<T>> set);

    void setOnClusterClickListener(OnClusterClickListener<T> onClusterClickListener);

    void setOnClusterInfoWindowClickListener(OnClusterInfoWindowClickListener<T> onClusterInfoWindowClickListener);

    void setOnClusterItemClickListener(OnClusterItemClickListener<T> onClusterItemClickListener);

    void setOnClusterItemInfoWindowClickListener(OnClusterItemInfoWindowClickListener<T> onClusterItemInfoWindowClickListener);

    void setOnRenderingFinishedListener(OnRenderingFinishedListener onRenderingFinishedListener);
}
